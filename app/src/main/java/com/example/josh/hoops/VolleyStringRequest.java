package com.example.josh.hoops;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Josh on 1/25/2018.
 */

public class VolleyStringRequest
{
    private StringRequest stringRequest;
    private List<GameData> gameList = new ArrayList<>();
    private GameData gameData;
    private RequestHandler requestHandler;
    private String value;
    private Boolean isToday;
    private String url;

//    public VolleyStringRequest(RequestHandler rh, String url, String date)
//    {
//        requestHandler = rh;
//        this.url = url + date;
//    }
    public VolleyStringRequest(RequestHandler rh, String url)
    {
        requestHandler = rh;
        this. url = url;
    }

    public StringRequest startRequest()
    {
        //creating a string request to send request to the url
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONArray array = new JSONArray(response);
                            value = array.toString();
                            requestHandler.onResponse(array.toString());
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

        return stringRequest;
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
