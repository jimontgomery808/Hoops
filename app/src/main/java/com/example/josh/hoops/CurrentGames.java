package com.example.josh.hoops;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentGames extends AppCompatActivity
{
    private GameData gameData;
    private String url = "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/currentGames.php";
    private ReadJSONCurrentGames jsonCurrentGames;
    private List<GameData> gameList = new ArrayList<>();
    private TextView tv;
    String str;
    JSONObject obj = new JSONObject();
    StringRequest stringRequest;
    RequestHandler rh;
    RequestQueue rq;
    VolleyStringRequest volleyStringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);

        Intent i= new Intent(this, ScheduledService.class);
        this.startService(i);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("JSON Info Update"));

        tv = (TextView) findViewById(R.id.tv);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("JSONString");
            jsonCurrentGames = new ReadJSONCurrentGames(message);
            try
            {
                jsonCurrentGames.readJSON();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            gameList = jsonCurrentGames.getGameList();
            tv.setText("Clock1 " + String.valueOf(gameList.get(0).getClock()) + "\n\nClock2 " + String.valueOf(gameList.get(1).getClock()));
        }
    };
}

