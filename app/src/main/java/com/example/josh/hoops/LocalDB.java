package com.example.josh.hoops;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josh on 2/10/2018.
 */

public class LocalDB implements RequestHandler
{
    private SQLiteDatabaseHandler dbHandler;
    private RequestQueue requestQueue;
    private RequestHandler requestHandler;
    private ReadJSONGamesInfo jsonGamesInfo;
    private List<GameData> gameList;
    private String urlString ="http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/games_history.php";
    private Context mContext;
    private static LocalDB instance;
    private LocalDB(){}  //private constructor.

    public static LocalDB getInstance()
    {
        if (instance == null){ //if there is no instance available... create new one
            instance = new LocalDB();
        }

        return instance;
    }
    public void close()
    {
        dbHandler.close();
    }
    public void query(Context context)
    {
        mContext = context;
        requestQueue = Volley.newRequestQueue(context);
        requestHandler = this;
        VolleyStringRequest volley = new VolleyStringRequest(requestHandler, urlString);
        requestQueue.add(volley.startRequest());
    }
    @Override
    public void onResponse(String resp)
    {

        gameList = new ArrayList<>();
        jsonGamesInfo = new ReadJSONGamesInfo(resp);
        gameList.clear();
        try
        {
            jsonGamesInfo.readJSON();
            gameList = jsonGamesInfo.getGameList();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        dbHandler = new SQLiteDatabaseHandler(mContext, gameList);
    }
    public List<GameData> getQueryList(String date)
    {
        return(dbHandler.queryData(date));
    }

}
