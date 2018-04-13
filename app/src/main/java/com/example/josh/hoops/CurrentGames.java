package com.example.josh.hoops;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrentGames extends AppCompatActivity implements View.OnClickListener
{
    private ReadJSONGamesInfo jsonCurrentGames;
    private List<GameData> gameList = new ArrayList<>();
    private BroadcastReceiver mMessageReceiver;
    private Intent scheduledVolleyIntent;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView rView;
    private ScoreboardAdapter scoreboardAdapter;
    private String savedJson = "";
    private Button prevButton;
    private Button nextButton;
    private Button todayButton;
    private Date todayDate;
    private SimpleDateFormat dateBttnFormatter = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat urlFormatter = new SimpleDateFormat("yyyyMMdd");
    private String urlDateString;
    private String urlString =  "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/currentGames.php?dateStr=";
    private String todayString;
    private TextView noGames;
    private LocalDB localDB;
    private long lastClickTime = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);
        setTitle("Live Games");
        todayDate = Calendar.getInstance().getTime();
        initWidgets();
        initLayouts();
        formatDates();
        localDB = LocalDB.getInstance();
        localDB.query(CurrentGames.this);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("JSONString");

                jsonCurrentGames = new ReadJSONGamesInfo(message);
                try
                {
                    jsonCurrentGames.readJSON();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                gameList = jsonCurrentGames.getGameList();
                if(todayButton.getText().equals("Today"))
                {
                    scoreboardAdapter.setItems(gameList);
                    scoreboardAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("THE STATE IS", "PAUSE");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        stopService(scheduledVolleyIntent);

    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("savedJSON", savedJson);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("JSON Info Update"));
        scheduledVolleyIntent = new Intent(this, ScheduledService.class);
        scheduledVolleyIntent.putExtra("URL", urlString + urlDateString);
        startService(scheduledVolleyIntent);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("THE STATE IS", "DESTROY");
    }

    protected void formatDates()
    {
        urlDateString = urlFormatter.format(todayDate);
        todayString = dateBttnFormatter.format(todayDate);
    }

    protected void setDateButton()
    {
        Date date = new Date();
        String todayDate = dateBttnFormatter.format(date);
        if(todayString.equals(todayDate))
        {
            todayButton.setText("Today");
        }
        else
        {
            todayButton.setText(todayString);
        }
    }

    protected void initWidgets()
    {
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        prevButton = (Button) findViewById(R.id.prevBttn);
        nextButton = (Button) findViewById(R.id.nextBttn);
        todayButton = (Button) findViewById(R.id.todayBttn);
        noGames = (TextView) findViewById(R.id.noGames);
        noGames.setVisibility(View.INVISIBLE);
    }

    protected void initLayouts()
    {

        gridLayoutManager = new GridLayoutManager(CurrentGames.this, 1);
        scoreboardAdapter = new ScoreboardAdapter(CurrentGames.this, gameList);
        rView.setLayoutManager(gridLayoutManager);
        rView.setAdapter(scoreboardAdapter);

        rView.setNestedScrollingEnabled(false);
        rView.setHasFixedSize(true);
        rView.setItemViewCacheSize(20);
        rView.setDrawingCacheEnabled(true);
        rView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rView.setNestedScrollingEnabled(true);
        scoreboardAdapter.setItems(gameList);
    }

    @Override
    public void onClick(View view)
    {
        Calendar cal;
        switch (view.getId())
        {
            case R.id.prevBttn:
                if (SystemClock.elapsedRealtime() - lastClickTime < 10)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                cal = Calendar.getInstance();
                try
                {
                    cal.setTime(dateBttnFormatter.parse(todayString));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                cal.add(Calendar.DATE, -1);
                todayDate = cal.getTime();
                urlDateString = urlFormatter.format(todayDate);
                formatDates();
                setDateButton();

                if(!todayButton.getText().equals("Today"))
                {
                    stopService(scheduledVolleyIntent);
                    List<GameData> nonLiveGames = localDB.getQueryList(urlDateString);
                    if(nonLiveGames.isEmpty())
                    {
                        noGames.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        noGames.setVisibility(View.INVISIBLE);
                    }
                    scoreboardAdapter.setItems(nonLiveGames);
                    scoreboardAdapter.notifyDataSetChanged();

                }
                else
                {
                    scoreboardAdapter.setItems(gameList);
                    scoreboardAdapter.notifyDataSetChanged();
                    startService(scheduledVolleyIntent);
                }
            break;

            case R.id.nextBttn:
                if (SystemClock.elapsedRealtime() - lastClickTime < 10)
                {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                cal = Calendar.getInstance();
                try
                {
                    cal.setTime(dateBttnFormatter.parse(todayString));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                cal.add(Calendar.DATE, 1);
                todayDate = cal.getTime();
                urlDateString = urlFormatter.format(todayDate);
                formatDates();
                setDateButton();

                if(!todayButton.getText().equals("Today"))
                {
                    stopService(scheduledVolleyIntent);
                    List<GameData> nonLiveGames = localDB.getQueryList(urlDateString);
                    if(nonLiveGames.isEmpty())
                    {
                        noGames.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        noGames.setVisibility(View.INVISIBLE);
                    }
                    scoreboardAdapter.setItems(nonLiveGames);
                    scoreboardAdapter.notifyDataSetChanged();

                }
                else
                {
                    scoreboardAdapter.setItems(gameList);
                    scoreboardAdapter.notifyDataSetChanged();
                    startService(scheduledVolleyIntent);
                }
            break;
        }
    }
}

