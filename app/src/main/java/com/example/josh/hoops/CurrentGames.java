package com.example.josh.hoops;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CurrentGames extends AppCompatActivity implements RequestHandler
{
    private SharedPreferences prefs = null;
    private GameData gameData;
    private String url = "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/currentGames.php";
    private ReadJSONCurrentGames jsonCurrentGames;
    private List<GameData> gameList = new ArrayList<>();
    private TextView tv;
    private BroadcastReceiver mMessageReceiver;
    private Intent scheduledVolleyIntent;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView rView;
    private ScoreboardAdapter scoreboardAdapter;
    private Toolbar mToolbar;
    private String savedJson = "";
    private Button prevButton;
    private Button nextButton;
    private Button todayButton;
    private Date todayDate;
    private SimpleDateFormat dateBttnFormatter = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat urlFormatter = new SimpleDateFormat("yyyyMMdd");
    private String urlString;
    private String todayString;
    private RequestHandler requestHandler;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);
        setTitle("Live Games");
        requestHandler = this;
        todayDate = Calendar.getInstance().getTime();
        initWidgets();

        formatDates();

        prevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
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
                formatDates();

                String checkStr1 = dateBttnFormatter.format(todayDate);
                String checkStr2 = dateBttnFormatter.format(Calendar.getInstance().getTime());


                if(!checkStr1.equals(checkStr2))
                {
                    stopService(scheduledVolleyIntent);
                }
                else
                {
                    startService(scheduledVolleyIntent);
                }


                formatDates();
                requestQueue = Volley.newRequestQueue(CurrentGames.this);

                Intent intent = new Intent(CurrentGames.this, ScheduledService.class);
                intent.putExtra("data", todayString);

                VolleyStringRequest volley = new VolleyStringRequest(requestHandler, urlString);
                requestQueue.add(volley.startRequest());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar cal = Calendar.getInstance();
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
                formatDates();

                String checkStr1 = dateBttnFormatter.format(todayDate);
                String checkStr2 = dateBttnFormatter.format(Calendar.getInstance().getTime());

                if(!checkStr1.equals(checkStr2))
                {
                    stopService(scheduledVolleyIntent);
                }
                else
                {
                    startService(scheduledVolleyIntent);
                }

                requestQueue = Volley.newRequestQueue(CurrentGames.this);

                Intent intent = new Intent(CurrentGames.this, ScheduledService.class);
                intent.putExtra("data", todayString);

                VolleyStringRequest volley = new VolleyStringRequest(requestHandler, urlString);
                requestQueue.add(volley.startRequest());
            }
        });

        initLayouts();
        mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("JSONString");
                savedJson = message;
                jsonCurrentGames = new ReadJSONCurrentGames(message);
                try
                {
                    scoreboardAdapter.setItems(gameList);
                    scoreboardAdapter.notifyDataSetChanged();
                    jsonCurrentGames.readJSON();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                gameList = jsonCurrentGames.getGameList();
                scoreboardAdapter.setItems(gameList);
                scoreboardAdapter.notifyDataSetChanged();
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
        scheduledVolleyIntent.putExtra("URL", urlString);
        startService(scheduledVolleyIntent);


    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("THE STATE IS", "DESTROY");
    }

    @Override
    public void onResponse(String resp)
    {
        Log.d("here", "marcel");
        jsonCurrentGames = new ReadJSONCurrentGames(resp);
        try
        {
            scoreboardAdapter.setItems(gameList);
            scoreboardAdapter.notifyDataSetChanged();
            jsonCurrentGames.readJSON();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        gameList = jsonCurrentGames.getGameList();
        scoreboardAdapter.setItems(gameList);
        scoreboardAdapter.notifyDataSetChanged();
    }

    protected void formatDates()
    {
        urlString = urlFormatter.format(todayDate);
        todayString = dateBttnFormatter.format(todayDate);
        todayButton.setText(todayString);

    }

    protected void initWidgets()
    {
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        prevButton = (Button) findViewById(R.id.prevBttn);
        nextButton = (Button) findViewById(R.id.nextBttn);
        todayButton = (Button) findViewById(R.id.todayBttn);
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
}

