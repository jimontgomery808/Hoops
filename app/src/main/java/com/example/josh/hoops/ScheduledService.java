package com.example.josh.hoops;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Josh on 1/25/2018.
 */

public class ScheduledService extends Service implements RequestHandler
{
    StringRequest stringRequest;
    RequestHandler rh;
    RequestQueue rq;
    VolleyStringRequest volleyStringRequest;

    Context context;

    private Timer timer = new Timer();


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        rh = this;
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                rq = Volley.newRequestQueue(context);
                volleyStringRequest = new VolleyStringRequest(rh);
                rq.add(volleyStringRequest.startRequest());
            }
        }, 0, 50000);//5 Minutes
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onResponse(String resp)
    {
        Log.d("here", resp);
        Intent intent = new Intent("JSON Info Update");
        intent.putExtra("JSONString", resp);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}