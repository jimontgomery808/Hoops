package com.example.josh.hoops;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.MyViewHolder>
{
    private Context mContext;
    private List<GameData> gameList;
    private List<String> vLogoList;
    private List<String> hLogoList;
    private String url1 = "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/";
    private String url2 = ".png";

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

    public ScoreboardAdapter(Context mContext, List<GameData> list)
    {
        this.mContext = mContext;
        this.gameList = list;
        vLogoList = new ArrayList<>();
        hLogoList = new ArrayList<>();

        initLogoList();
    }

    public void initLogoList()
    {
        String vTeamResource = "";
        String hTeamResource = "";

        vLogoList.clear();
        hLogoList.clear();

        for(int i = 0; i < gameList.size(); i ++)
        {
            vTeamResource = getLogo(gameList.get(i).getvTeamAbrv());
            hTeamResource = getLogo(gameList.get(i).gethTeamAbrv());

            vLogoList.add(url1 + vTeamResource + url2);
            hLogoList.add(url1 + hTeamResource + url2);
        }
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
        else if(gameData.getQuarter() > 4)
        {
            switch(gameData.getQuarter())
            {
                case(5): clock = "1OT";
                break;
                case(6): clock = "2OT";
                break;
                case(7): clock = "3OT";
                    break;
                case(8): clock = "4OT";
                    break;
                case(9): clock = "5OT";
                    break;
                case(10): clock = "6OT";
                    break;
            }
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

        Picasso.with(mContext).load(vLogoList.get(position)).into(holder.vTeamLogo);
        Picasso.with(mContext).load(hLogoList.get(position)).into(holder.hTeamLogo);
//        holder.vTeamLogo.setImageResource(logoList.get(position * 2));
//        holder.hTeamLogo.setImageResource(logoList.get((position *2) + 1));
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
        vLogoList = new ArrayList<>();
        hLogoList = new ArrayList<>();

        initLogoList();
    }
    @Override
    public int getItemCount() {
        return gameList.size();
    }
    public String getLogo(String abrv)
    {
        switch(abrv)
        {case("ATL"): return "hawks";

            case("BKN"): return "nets";

            case("BOS"):return "celtics";

            case("CHA"):return "hornets";

            case("CHI"):return "bulls";

            case("CLE"):return "cavs";

            case("DAL"):return "mavericks";

            case("DEN"):return "nuggets";

            case("DET"):return "pistons";

            case("GSW"):return "warriors";

            case("HOU"):return "rockets";

            case("IND"):return "pacers";

            case("LAC"):return "clippers";

            case("LAL"):return "lakers";

            case("MEM"):return "grizzlies";

            case("MIA"):return "heat";

            case("MIL"):return "bucks";

            case("MIN"):return "twolves";

            case("NOP"):return "pels";

            case("NYK"):return "knicks";

            case("OKC"):return "thunder";

            case("ORL"):return "magic";

            case("PHI"):return "sixers";

            case("PHX"):return "suns";

            case("POR"):return "blazers";

            case("SAC"):return "kings";

            case("SAS"):return "spurs";

            case("TOR"):return "raptors";

            case("UTA"):return "jazz";

            case("WAS"):return "wizards";

            default: return "";
        }
    }
}