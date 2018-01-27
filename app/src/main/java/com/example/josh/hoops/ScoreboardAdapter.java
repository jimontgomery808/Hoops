package com.example.josh.hoops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.MyViewHolder>
{
    private Context mContext;
    private List<GameData> gameList;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView vTeamName;
        TextView hTeamName;
        TextView vRecord;
        TextView hRecord;
        TextView vScore;
        TextView hScore;
        TextView clock;


        public MyViewHolder(View view)
        {
            super(view);
            vTeamName = (TextView) view.findViewById(R.id.vTeamAbrv);
            hTeamName = (TextView) view.findViewById(R.id.hTeamAbrv);
            vRecord = (TextView) view.findViewById(R.id.vTeamRecord);
            hRecord = (TextView) view.findViewById(R.id.hTeamRecord);
            vScore = (TextView) view.findViewById(R.id.vTeamScore);
            hScore = (TextView) view.findViewById(R.id.hTeamScore);
            clock = (TextView) view.findViewById(R.id.clock);
        }
    }


    public ScoreboardAdapter(Context mContext, List<GameData> list)
    {
        this.mContext = mContext;
        this.gameList = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_scoreboard, null);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        GameData gameData = gameList.get(position);
        String vRecord = ("(" + gameData.getvTeamWinRecord() + "-" + gameData.getvTeamLossRecord() + ")");
        String hRecord = ("(" + gameData.gethTeamWinRecord() + "-" + gameData.gethTeamLossRecord() + ")");

        holder.vTeamName.setText(gameData.getvTeamAbrv());
        holder.hTeamName.setText(gameData.gethTeamAbrv());
        holder.vRecord.setText(vRecord);
        holder.hRecord.setText(hRecord);
        holder.vScore.setText(gameData.getvTeamScore());
        holder.hScore.setText(gameData.gethTeamScore());
        holder.clock.setText(gameData.getClock());

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}