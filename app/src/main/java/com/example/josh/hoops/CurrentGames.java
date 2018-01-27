package com.example.josh.hoops;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrentGames extends AppCompatActivity
{
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
    private TextView today;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);

        setTitle("Live Games");
        today = (TextView) findViewById(R.id.today);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String dateString = sdf.format(date);
        today.setText(dateString);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setNestedScrollingEnabled(false);
        rView.setHasFixedSize(true);
        rView.setItemViewCacheSize(20);
        rView.setDrawingCacheEnabled(true);
        rView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        gridLayoutManager = new GridLayoutManager(CurrentGames.this,1);
        scoreboardAdapter = new ScoreboardAdapter(CurrentGames.this, gameList);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);
        rView.setAdapter(scoreboardAdapter);


        Log.d("THE STATE IS", "CREATE");
        mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("JSONString");
                jsonCurrentGames = new ReadJSONCurrentGames(message);
                try
                {
                    Log.d("here", "gathered broadcast receiver");
                    jsonCurrentGames.readJSON();

                }
                catch (JSONException e)
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
        //unregisterReceiver(mMessageReceiver);
        stopService(scheduledVolleyIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("JSON Info Update"));
        scheduledVolleyIntent = new Intent(this, ScheduledService.class);
        startService(scheduledVolleyIntent);

        Log.d("THE STATE IS", "RESUME");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("THE STATE IS", "DESTROY");
    }
}

