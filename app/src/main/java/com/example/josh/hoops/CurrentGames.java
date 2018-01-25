package com.example.josh.hoops;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentGames extends AppCompatActivity
{
    private GameData gameData;
    private String url = "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/games.php";
    private ReadJSONCurrentGames jsonCurrentGames;
    private List<GameData> gameList = new ArrayList<>();
    private TextView tv;
    String str;
    JSONObject obj = new JSONObject();
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_games);


        tv = (TextView) findViewById(R.id.tv);
        startRequest(this);

        Log.d("List Size", gameList.toString());
    }
    private void readJSON(String response) throws JSONException
    {
        //getting the whole json object from the response
        JSONArray array = new JSONArray(response);
        for(int i = 0; i < array.length(); i ++)
        {
            JSONObject obj = array.getJSONObject(i);

            String gameIdNumber = obj.getString("gameId");
            int isGameActivated = obj.getInt("isGameActivated"); // game started
            String startTime = obj.getString("startTime");       // start time
            String startDate = obj.getString("startDate");       // start date
            String clock = obj.getString("clock");                       // game clock
            String quarter = obj.getString("quarter");               // quarter #
            int isHalfTime = obj.getInt("isHalfTime");           // half time
            int isEndOfQuarter = obj.getInt("isEndOfQuarter");  // end of quarter
            String vTeamAbrv = obj.getString("vTeamAbrv");                   // team name
            String vTeamWinRecord = obj.getString("vWinRecord");;                       // # wins
            String vTeamLossRecord = obj.getString("vLossRecord");                       // # losses
            String vTeamScore = obj.getString("vTeamScore");                           // # current score

            String hTeamAbrv = obj.getString("hTeamAbrv");                   // team name
            String hTeamWinRecord = obj.getString("hWinRecord");;                       // # wins
            String hTeamLossRecord = obj.getString("hLossRecord");                       // # losses
            String hTeamScore = obj.getString("hTeamScore");                           // # current score


            String vTeamWatchShort = obj.getString("vTeamWatchShort");
            String vTeamWatchLong = obj.getString("vTeamWatchLong");
            String hTeamWatchShort = obj.getString("hTeamWatchShort");
            String hTeamWatchLong = obj.getString("hTeamWatchLong");

            gameData = new GameData(gameIdNumber, getBoolean(isGameActivated), startTime, startDate, clock, Integer.valueOf(quarter), getBoolean(isHalfTime), getBoolean(isEndOfQuarter),
                    vTeamAbrv, vTeamWinRecord, vTeamLossRecord, vTeamScore, hTeamAbrv, hTeamWinRecord,
                    hTeamLossRecord, hTeamScore, vTeamWatchShort, vTeamWatchLong, hTeamWatchShort, hTeamWatchLong);

            //tv.setText(vTeamAbrv + ": " + vTeamScore + "          " + hTeamAbrv + ": " + hTeamScore + "                  Q" + quarter + "        " + clock);
            gameList.add(gameData);
        }

    }

    public void startRequest(Context context)
    {
        //creating a string request to send request to the url
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //hiding the progressbar after completion
                        try
                        {
                            readJSON(response);

                            tv.setText(gameList.toString());
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private boolean getBoolean(int a)
    {
        if(a == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

