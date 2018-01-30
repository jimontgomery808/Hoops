package com.example.josh.hoops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.MyViewHolder>
{
    private Context mContext;
    private List<GameData> gameList;
    private List<Integer> logoList;


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView vTeamLogo;
        ImageView hTeamLogo;
        TextView vTeamName;
        TextView hTeamName;
        TextView vRecord;
        TextView hRecord;
        TextView vScore;
        TextView hScore;
        TextView clock;
        TextView broadcast;


        public MyViewHolder(View view)
        {
            super(view);
            vTeamLogo = (ImageView) view.findViewById(R.id.vTeamLogo);
            hTeamLogo = (ImageView) view.findViewById(R.id.hTeamLogo);
            vTeamName = (TextView) view.findViewById(R.id.vTeamAbrv);
            hTeamName = (TextView) view.findViewById(R.id.hTeamAbrv);
            vRecord = (TextView) view.findViewById(R.id.vTeamRecord);
            hRecord = (TextView) view.findViewById(R.id.hTeamRecord);
            vScore = (TextView) view.findViewById(R.id.vTeamScore);
            hScore = (TextView) view.findViewById(R.id.hTeamScore);
            clock = (TextView) view.findViewById(R.id.clock);
            broadcast = (TextView) view.findViewById(R.id.broadcast);
        }
    }

    public void initLogoList()
    {
        int vTeamResource = 0;
        int hTeamResource = 0;

        logoList.clear();
        for(int i = 0; i < gameList.size(); i ++)
        {
            vTeamResource = setLogo(gameList.get(i).getvTeamAbrv());
            hTeamResource = setLogo(gameList.get(i).gethTeamAbrv());

            logoList.add(vTeamResource);
            logoList.add(hTeamResource);
        }
    }
    public ScoreboardAdapter(Context mContext, List<GameData> list)
    {
        this.mContext = mContext;
        this.gameList = list;
        logoList = new ArrayList<>();
        initLogoList();
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
        String vScore = gameData.getvTeamScore();
        String hScore = gameData.gethTeamScore();
        String clock = "";
        String broadcast = "";
        if(gameData.isHalfTime())
        {
            clock = "Halftime";
        }
        else if(gameData.getQuarter() == 0)
        {
            clock = gameData.getStartTime();
            vScore = "";
            hScore = "";
        }
        else if(gameData.getQuarter() == 4 && !gameData.isGameActivated())
        {
            clock = "Final";
        }
        else if(gameData.getClock().equals("0.0"))
        {
            clock = "End of Q" + gameData.getQuarter();
        }
        else
        {
            clock = "Q" + String.valueOf(gameData.getQuarter()) + "  " +gameData.getClock();
        }
        if(gameData.getvTeamWatchShort().equals(gameData.gethTeamWatchShort()))
        {
            broadcast = gameData.getvTeamWatchShort();
        }
        else
        {
            broadcast = gameData.getvTeamWatchShort() + "\n" + gameData.gethTeamWatchShort();
        }

//        holder.vTeamLogo.setImageResource(setLogo(gameData.getvTeamAbrv()));
//        holder.hTeamLogo.setImageResource(setLogo(gameData.gethTeamAbrv()));

        holder.vTeamLogo.setImageResource(logoList.get(position * 2));
        holder.hTeamLogo.setImageResource(logoList.get((position *2) + 1));
        holder.vTeamName.setText(gameData.getvTeamAbrv());
        holder.hTeamName.setText(gameData.gethTeamAbrv());
        holder.vRecord.setText(vRecord);
        holder.hRecord.setText(hRecord);
        holder.vScore.setText(vScore);
        holder.hScore.setText(hScore);
        holder.clock.setText(clock);
        holder.broadcast.setText(broadcast);

    }
    public void setItems(List<GameData> persons)
    {
        this.gameList = persons;
        logoList = new ArrayList<>();
        initLogoList();
    }
    @Override
    public int getItemCount() {
        return gameList.size();
    }
    public int setLogo(String abrv)
    {
        int logo = 0;
        switch(abrv)
        {
            case("ATL"): logo = R.drawable.hawks;
                break;
            case("BKN"):logo =(R.drawable.nets);
                break;
            case("BOS"):logo =(R.drawable.celtics);
                break;
            case("CHA"):logo =(R.drawable.hornets);
                break;
            case("CHI"):logo =(R.drawable.bulls);
                break;
            case("CLE"):logo =(R.drawable.cavs);
                break;
            case("DAL"):logo =(R.drawable.mavericks);
                break;
            case("DEN"):logo =(R.drawable.nuggets);
                break;
            case("DET"):logo =(R.drawable.pistons);
                break;
            case("GSW"):logo =(R.drawable.warriors);
                break;
            case("HOU"):logo =(R.drawable.rockets);
                break;
            case("IND"):logo =(R.drawable.pacers);
                break;
            case("LAC"):logo =(R.drawable.clippers);
                break;
            case("LAL"):logo =(R.drawable.lakers);
                break;
            case("MEM"):logo =(R.drawable.grizzlies);
                break;
            case("MIA"):logo =(R.drawable.heat);
                break;
            case("MIL"):logo =(R.drawable.bucks);
                break;
            case("MIN"):logo =(R.drawable.twolves);
                break;
            case("NOP"):logo =(R.drawable.pels);
                break;
            case("NYK"):logo =(R.drawable.knicks);
                break;
            case("OKC"):logo =(R.drawable.thunder);
                break;
            case("ORL"):logo =(R.drawable.magic);
                break;
            case("PHI"):logo =(R.drawable.sixers);
                break;
            case("PHX"):logo =(R.drawable.suns);
                break;
            case("POR"):logo =(R.drawable.blazers);
                break;
            case("SAC"):logo =(R.drawable.kings);
                break;
            case("SAS"):logo =(R.drawable.spurs);
                break;
            case("TOR"):logo =(R.drawable.raptors);
                break;
            case("UTA"):logo =(R.drawable.jazz);
                break;
            case("WAS"):logo =(R.drawable.wizards);
                break;
        }

        return logo;
    }
}