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
import android.widget.TextView;

import org.json.JSONException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);

        rView = (RecyclerView)findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(CurrentGames.this,1);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayoutManager);



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
                scoreboardAdapter = new ScoreboardAdapter(CurrentGames.this, gameList);
                rView.setAdapter(scoreboardAdapter);
               // fillTextView();
            }
        };


    }

//    public void fillTextView()
//    {
//        String temp = "";
//        for(int i = 0; i < gameList.size(); i ++)
//        {
//            String clock = "";
//            if(gameList.get(i).isHalfTime())
//            {
//                clock = "Halftime";
//            }
//            else if(!gameList.get(i).isGameActivated() && gameList.get(i).getQuarter() == 0)
//            {
//                clock = "Tip off at " + gameList.get(i).getStartTime();
//            }
//            else if(gameList.get(i).getQuarter() == 4 && !gameList.get(i).isGameActivated())
//            {
//                clock = "Final";
//            }
//            else
//            {
//               clock = "Q" + String.valueOf(gameList.get(i).getQuarter()) + "     " + gameList.get(i).getClock();
//            }
//            temp += gameList.get(i).gethTeamAbrv() + ": " + gameList.get(i).gethTeamScore() +"\n" +
//                    gameList.get(i).getvTeamAbrv() + ": " + gameList.get(i).getvTeamScore() +"\n" +
//                    clock +"\n\n";
//        }
//        tv.setText(temp);
//    }

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

//        IntentFilter filter = new IntentFilter();
//        registerReceiver(mMessageReceiver, new IntentFilter("JSON Info Update"));
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

