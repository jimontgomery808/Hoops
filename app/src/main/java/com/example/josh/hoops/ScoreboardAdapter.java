package com.example.josh.hoops;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.MyViewHolder>
{
    private Context mContext;
    private List<GameData> gameList;
    private List<String> prevVScore;
    private List<String> prevHScore;
    private List<String> vLogoList;
    private List<String> hLogoList;
    private List<String> prevVTeam;
    private List<String> prevHTeam;
    private String url1 = "http://ec2-52-14-204-231.us-east-2.compute.amazonaws.com/";
    private String url2 = ".png";
    private boolean isLive;

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

        prevVScore = new ArrayList<>();
        prevHScore = new ArrayList<>();
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

            prevVScore.add(gameList.get(i).getvTeamScore());
            prevHScore.add(gameList.get(i).gethTeamScore());
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_scoreboard, null, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        GameData gameData = gameList.get(position);
        String vRecord = ("(" + gameData.getvTeamWinRecord() + "-" + gameData.getvTeamLossRecord() + ")");
        String hRecord = ("(" + gameData.gethTeamWinRecord() + "-" + gameData.gethTeamLossRecord() + ")");
        final String vScore;
        final String hScore;

        if(gameData.getQuarter() == 0)
        {
            vScore = "";
            hScore = "";
        }
        else
        {
            vScore = gameData.getvTeamScore();
            hScore = gameData.gethTeamScore();
        }


        String broadcast = getBroadcast(gameData);
        String clock = getClock(gameData);

        Picasso.with(mContext).load(hLogoList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.hTeamLogo);
        Picasso.with(mContext).load(vLogoList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.vTeamLogo);

        Picasso.with(mContext).load(vLogoList.get(position)).into(holder.vTeamLogo);
        Picasso.with(mContext).load(hLogoList.get(position)).into(holder.hTeamLogo);

        holder.vTeamName.setText(gameData.getvTeamAbrv());
        holder.hTeamName.setText(gameData.gethTeamAbrv());
        holder.vRecord.setText(vRecord);
        holder.hRecord.setText(hRecord);

        holder.vScore.setText(vScore);
        if(position == 1)
            Toast.makeText(mContext, prevHScore.get(position), Toast.LENGTH_LONG).show();

        if(!vScore.equals(prevVScore.get(position)) && !prevVScore.get(position).isEmpty())
        {
            holder.vScore.setTextColor(Color.RED);
            holder.vScore.animate().setDuration(2000).withEndAction(new Runnable()
            {

                @Override
                public void run()
                {
                    // set color back to normal
                    holder.vScore.setTextColor(Color.parseColor("#BDBDBD"));
                }
            }).start();
        }
        prevVScore.set(position,vScore);


        holder.hScore.setText(hScore);

        if(!hScore.equals(prevHScore.get(position)) && !prevHScore.get(position).isEmpty())
        {
            holder.hScore.setTextColor(Color.RED);
            holder.hScore.animate().setDuration(2000).withEndAction(new Runnable()
            {

                @Override
                public void run()
                {
                    // set color back to normal
                    holder.hScore.setTextColor(Color.parseColor("#BDBDBD"));
                }
            }).start();
        }

        prevHScore.set(position, gameData.gethTeamScore());


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

    public String getBroadcast(GameData gameData)
    {
        String broadcast = "";
        if(gameData.getvTeamWatchShort().equals(gameData.gethTeamWatchShort()))
        {
            broadcast = gameData.getvTeamWatchShort();
        }
        else
        {
            broadcast = gameData.getvTeamWatchShort() + "\n" + gameData.gethTeamWatchShort();
        }

        return broadcast;
    }

    public String getClock(GameData gameData)
    {
        String clock = "";
        String overTime = "";

        if(gameData.getQuarter() == 0)
        {
            clock = gameData.getStartTime();
        }
        else if(gameData.isHalfTime())
        {
            clock = "Halftime";
        }
        else if(gameData.getQuarter() == 4 && !gameData.isGameActivated())
        {
            clock = "Final";
        }
        else if(gameData.getQuarter() > 4)
        {
            switch(gameData.getQuarter())
            {
                case(5): overTime = "1OT";
                    break;
                case(6): overTime = "2OT";
                    break;
                case(7): overTime = "3OT";
                    break;
                case(8): overTime = "4OT";
                    break;
                case(9): overTime = "5OT";
                    break;
                case(10): overTime = "6OT";
                    break;
            }

            if(gameData.isGameActivated())
            {
                clock = overTime;
            }
            else
            {
                clock = "Final " + overTime;
            }
        }

        else if(gameData.getClock().equals("0.0"))
        {
            clock = "End of Q" + gameData.getQuarter();
        }

        else
        {
            clock = "Q" + String.valueOf(gameData.getQuarter()) + "  " +gameData.getClock();
        }

        return clock;
    }

}