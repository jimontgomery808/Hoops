package com.example.josh.hoops;

import android.content.Context;

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

/**
 * Created by Josh on 1/21/2018.
 */

public class ReadJSONCurrentGames
{
    private String url;
    private GameData gameData;
    private ArrayList<GameData> gameList;
    private StringRequest stringRequest;

    public ReadJSONCurrentGames(String jsonUrl)
    {
        url = jsonUrl;
        gameList = new ArrayList<>();
    }

    public int getListSize()
    {
        return gameList.size();
    }
    public ArrayList<GameData> getGameList(Context context)
    {
        return gameList;
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
            String vWinRecord = obj.getString("vWinRecord");;                       // # wins
            String vLossRecord = obj.getString("vLossRecord");                       // # losses
            String vTeamScore = obj.getString("vTeamScore");                           // # current score

            String hTeamAbrv = obj.getString("hTeamAbrv");                   // team name
            String hWinRecord = obj.getString("hWinRecord");;                       // # wins
            String hLossRecord = obj.getString("hLossRecord");                       // # losses
            String hTeamScore = obj.getString("hTeamScore");                           // # current score


            String vTeamWatchShort = obj.getString("vTeamWatchShort");
            String vTeamWatchLong = obj.getString("vTeamWatchLong");
            String hTeamWatchShort = obj.getString("hTeamWatchShort");
            String hTeamWatchLong = obj.getString("hTeamWatchLong");

            gameData = new GameData(gameIdNumber, getBoolean(isGameActivated), startTime, startDate, clock, Integer.valueOf(quarter), getBoolean(isHalfTime), getBoolean(isEndOfQuarter),
                    vTeamAbrv, vWinRecord, vLossRecord, vTeamScore, hTeamAbrv, hWinRecord,
                    hLossRecord, hTeamScore, vTeamWatchShort, vTeamWatchLong, hTeamWatchShort, hTeamWatchLong);

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
