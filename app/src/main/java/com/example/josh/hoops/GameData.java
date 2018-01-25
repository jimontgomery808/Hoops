package com.example.josh.hoops;

public class GameData
{
    private String gameId;
    private boolean isGameActivated;
    private String startTime;
    private String startDate;
    private String clock;
    private int quarter;
    private boolean isHalfTime;
    private boolean isEndOfQuarter;
    private String vTeamAbrv;
    private String vTeamWinRecord;
    private String vTeamLossRecord;
    private String vTeamScore;
    private String hTeamAbrv;
    private String hTeamWinRecord;
    private String hTeamLossRecord;
    private String hTeamScore;
    private String vTeamWatchShort;
    private String vTeamWatchLong;
    private String hTeamWatchShort;
    private String hTeamWatchLong;


    public GameData(String gameId, boolean isGameActivated, String startTime, String startDate, String clock, int quarter,
                    boolean isHalfTime, boolean isEndOfQuarter, String vTeamAbrv, String vTeamWinRecord, String vTeamLossRecord,
                    String vTeamScore, String hTeamAbrv, String hTeamWinRecord, String hTeamLossRecord, String hTeamScore,
                    String vTeamWatchShort, String vTeamWatchLong, String hTeamWatchShort, String hTeamWatchLong)
    {
        this.gameId = gameId;
        this.isGameActivated = isGameActivated;
        this.startTime = startTime;
        this.startDate = startDate;
        this.clock = clock;
        this.quarter = quarter;
        this.isHalfTime = isHalfTime;
        this.isEndOfQuarter = isEndOfQuarter;
        this.vTeamAbrv = vTeamAbrv;
        this.vTeamWinRecord = vTeamWinRecord;
        this.vTeamLossRecord = vTeamLossRecord;
        this.vTeamScore = vTeamScore;
        this.hTeamAbrv = hTeamAbrv;
        this.hTeamWinRecord = hTeamWinRecord;
        this.hTeamLossRecord = hTeamLossRecord;
        this.hTeamScore = hTeamScore;
        this.vTeamWatchShort = vTeamWatchShort;
        this.vTeamWatchLong = vTeamWatchLong;
        this.hTeamWatchShort = hTeamWatchShort;
        this.hTeamWatchLong = hTeamWatchLong;
    }


    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public boolean isGameActivated()
    {
        return isGameActivated;
    }
    public void setGameActivated(boolean isGameActivated)
    {
        this.isGameActivated = isGameActivated;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    public String getStartDate()
    {
        return startDate;
    }
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    public String getClock()
    {
        return clock;
    }
    public void setClock(String clock)
    {
        this.clock = clock;
    }
    public int getQuarter()
    {
        return quarter;
    }
    public void setQuarter(int quarter)
    {
        this.quarter = quarter;
    }
    public boolean isHalfTime()
    {
        return isHalfTime;
    }
    public void setHalfTime(boolean isHalfTime)
    {
        this.isHalfTime = isHalfTime;
    }
    public boolean isEndOfQuarter()
    {
        return isEndOfQuarter;
    }
    public void setEndOfQuarter(boolean isEndOfQaurter)
    {
        this.isEndOfQuarter = isEndOfQaurter;
    }
    public String getvTeamAbrv()
    {
        return vTeamAbrv;
    }
    public void setvTeamAbrv(String vTeamAbrv)
    {
        this.vTeamAbrv = vTeamAbrv;
    }
    public String getvTeamWinRecord()
    {
        return vTeamWinRecord;
    }
    public void setvTeamWinRecord(String vTeamWinRecord)
    {
        this.vTeamWinRecord = vTeamWinRecord;
    }
    public String getvTeamLossRecord()
    {
        return vTeamLossRecord;
    }
    public void setvTeamLossRecord(String vTeamLossRecord)
    {
        this.vTeamLossRecord = vTeamLossRecord;
    }
    public String getvTeamScore()
    {
        return vTeamScore;
    }
    public void setvTeamScore(String vTeamScore)
    {
        this.vTeamScore = vTeamScore;
    }
    public String gethTeamAbrv()
    {
        return hTeamAbrv;
    }
    public void sethTeamAbrv(String hTeamAbrv)
    {
        this.hTeamAbrv = hTeamAbrv;
    }
    public String gethTeamWinRecord()
    {
        return hTeamWinRecord;
    }
    public void sethTeamWinRecord(String hTeamWinRecord)
    {
        this.hTeamWinRecord = hTeamWinRecord;
    }
    public String gethTeamLossRecord()
    {
        return hTeamLossRecord;
    }
    public void sethTeamLossRecord(String hTeamLossRecord)
    {
        this.hTeamLossRecord = hTeamLossRecord;
    }
    public String gethTeamScore()
    {
        return hTeamScore;
    }
    public void sethTeamScore(String hTeamScore)
    {
        this.hTeamScore = hTeamScore;
    }
    public String getvTeamWatchShort()
    {
        return vTeamWatchShort;
    }
    public void setvTeamWatchShort(String vTeamWatchShort)
    {
        this.vTeamWatchShort = vTeamWatchShort;
    }
    public String getvTeamWatchLong()
    {
        return vTeamWatchLong;
    }
    public void setvTeamWatchLong(String vTeamWatchLong)
    {
        this.vTeamWatchLong = vTeamWatchLong;
    }
    public String gethTeamWatchShort()
    {
        return hTeamWatchShort;
    }
    public void sethTeamWatchShort(String hTeamWatchShort)
    {
        this.hTeamWatchShort = hTeamWatchShort;
    }
    public String gethTeamWatchLong()
    {
        return hTeamWatchLong;
    }
    public void sethTeamWatchLong(String hTeamWatchLong)
    {
        this.hTeamWatchLong = hTeamWatchLong;
    }
    @Override
    public String toString()
    {
        return "GameData: gameId=" + gameId + ", isGameActivated=" + isGameActivated + ", startTime=" + startTime + ", startDate=" + startDate
                + ", clock=" + clock + ", quarter=" + quarter + ", isHalfTime=" + isHalfTime + ", isEndOfQuarter="
                + isEndOfQuarter + ", vTeamAbrv=" + vTeamAbrv + ", vTeamWinRecord=" + vTeamWinRecord
                + ", vTeamLossRecord=" + vTeamLossRecord + ", vTeamScore=" + vTeamScore + ", hTeamAbrv=" + hTeamAbrv
                + ", hTeamWinRecord=" + hTeamWinRecord + ", hTeamLossRecord=" + hTeamLossRecord + ", hTeamScore="
                + hTeamScore + ", vTeamWatchShort=" + vTeamWatchShort + ", vTeamWatchLong=" + vTeamWatchLong
                + ", hTeamWatchShort=" + hTeamWatchShort + ", hTeamWatchLong=" + hTeamWatchLong;
    }


}
