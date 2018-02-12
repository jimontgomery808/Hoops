package com.example.josh.hoops;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Josh on 1/21/2018.
 */

public class ReadJSONGamesInfo
{
    private String url;
    private GameData gameData;
    private ArrayList<GameData> gameList;
    private StringRequest stringRequest;

    public ReadJSONGamesInfo(String jsonUrl)
    {
        url = jsonUrl;
        gameList = new ArrayList<>();
    }

    public int getListSize()
    {
        return gameList.size();
    }
    public ArrayList<GameData> getGameList()
    {
        return gameList;
    }

    public void readJSON() throws JSONException
    {
        //getting the whole json object from the response
        JSONArray array = new JSONArray(url);
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
