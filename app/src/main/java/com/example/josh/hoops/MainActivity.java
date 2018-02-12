package com.example.josh.hoops;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    //the URL having the json data
    private Button currentGamesBtn;
    //listview object


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDB local = LocalDB.getInstance();
        local.query(MainActivity.this);
        currentGamesBtn = (Button) findViewById(R.id.currentGamesBtn);
        currentGamesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, CurrentGames.class);
                startActivity(intent);
            }
        });
    }

}