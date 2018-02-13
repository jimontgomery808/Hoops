package com.example.josh.hoops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HoopsDB";
    private static final String TABLE_NAME = "all_scoreboards";
    private static final String GAME_ID = "gameId";
    private static final String GAME_ACTIVATED =  "isGameActivated";
    private static final String START_TIME =  "startTime";
    private static final String START_DATE =  "startDate";
    private static final String CLOCK =  "clock";
    private static final String QUARTER =  "quarter";
    private static final String HALFTIME =  "isHalfTime";
    private static final String END_QUARTER =  "isEndOfQuarter";
    private static final String V_TEAM_ABRV =  "vTeamAbrv";
    private static final String V_WIN_RECORD =  "vTeamWinRecord";
    private static final String V_LOSS_RECORD =  "vTeamLossRecord";
    private static final String V_SCORE =  "vTeamScore";
    private static final String H_TEAM_ABRV =  "hTeamAbrv";
    private static final String H_WIN_RECORD =  "hTeamWinRecord";
    private static final String H_LOSS_RECORD =  "hTeamLossRecord";
    private static final String H_SCORE =  "hTeamScore";
    private static final String V_TEAM_WATCH_SHORT = "vTeamWatchShort";
    private static final String V_TEAM_WATCH_LONG = "vTeamWatchLong";
    private static final String H_TEAM_WATCH_SHORT = "hTeamWatchShort";
    private static final String H_TEAM_WATCH_LONG = "hTeamWatchLong";

    private static final String[] COLUMNS = { GAME_ID, GAME_ACTIVATED, START_TIME,
            START_DATE,CLOCK,QUARTER,HALFTIME,END_QUARTER,V_TEAM_ABRV,V_WIN_RECORD,
            V_LOSS_RECORD,V_SCORE,H_TEAM_ABRV,H_WIN_RECORD,H_LOSS_RECORD,H_SCORE,
            V_TEAM_WATCH_SHORT,V_TEAM_WATCH_LONG,H_TEAM_WATCH_SHORT,H_TEAM_WATCH_LONG};

    private List<GameData> gameDataList;

    public SQLiteDatabaseHandler(Context context, List<GameData> list)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gameDataList = list;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATION_TABLE = "CREATE TABLE all_scoreboards ( "+ "gameId INTEGER PRIMARY KEY, "
                + "isGameActivated INTEGER, " +  "startTime TEXT, " + "startDate TEXT, " + "clock TEXT, "
                + "quarter INTEGER, " + "isHalfTime TEXT, " + "isEndOfQuarter TEXT, " + "vTeamAbrv TEXT, "
                + "vTeamWinRecord TEXT, " + "vTeamLossRecord TEXT, " + "vTeamScore TEXT, " + "hTeamAbrv TEXT, "
                + "hTeamWinRecord TEXT, " + "hTeamLossRecord TEXT, " + "hTeamScore TEXT, " + "vTeamWatchShort TEXT, "
                + "vTeamWatchLong TEXT, " +"hTeamWatchShort TEXT, " + "hTeamWatchLong TEXT)";

        db.execSQL(CREATION_TABLE);
        String sql = "INSERT INTO all_scoreboards VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();
        for(int i = 0; i < gameDataList.size(); i ++)
        {
            GameData data = gameDataList.get(i);
            statement.clearBindings();
            statement.bindString(1, data.getGameId());
            statement.bindString(2, String.valueOf(data.isGameActivated()));
            statement.bindString(3, data.getStartTime());
            statement.bindString(4, data.getStartDate());
            statement.bindString(5, data.getClock());
            statement.bindLong(6, data.getQuarter());
            statement.bindString(7, String.valueOf(data.isHalfTime()));
            statement.bindString(8, String.valueOf(data.isEndOfQuarter()));
            statement.bindString(9, data.getvTeamAbrv());
            statement.bindString(10, data.getvTeamWinRecord());
            statement.bindString(11, data.getvTeamLossRecord());
            statement.bindString(12, data.getvTeamScore());
            statement.bindString(13, data.gethTeamAbrv());
            statement.bindString(14, data.gethTeamWinRecord());
            statement.bindString(15, data.gethTeamLossRecord());
            statement.bindString(16, data.gethTeamScore());
            statement.bindString(17, data.getvTeamWatchShort());
            statement.bindString(18, data.getvTeamWatchLong());
            statement.bindString(19, data.gethTeamWatchShort());
            statement.bindString(20, data.gethTeamWatchLong());
            statement.execute();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(GameData data)
    {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(data.getGameId()) });
        db.close();
    }

    public void addGameData(GameData data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();
        values.put(GAME_ID, data.getGameId());
        values.put(GAME_ACTIVATED, data.isGameActivated());
        values.put(START_TIME, data.getStartTime());
        values.put(START_DATE, data.getStartDate());
        values.put(CLOCK, data.getClock());
        values.put(QUARTER, data.getQuarter());
        values.put(HALFTIME, data.isHalfTime());
        values.put(END_QUARTER, data.isEndOfQuarter());
        values.put(V_TEAM_ABRV, data.getvTeamAbrv());
        values.put(V_WIN_RECORD, data.getvTeamWinRecord());
        values.put(V_LOSS_RECORD, data.getvTeamLossRecord());
        values.put(V_SCORE, data.getvTeamScore());
        values.put(H_TEAM_ABRV, data.gethTeamAbrv());
        values.put(H_WIN_RECORD, data.gethTeamWinRecord());
        values.put(H_LOSS_RECORD, data.gethTeamLossRecord());
        values.put(H_SCORE, data.gethTeamScore());
        values.put(V_TEAM_WATCH_SHORT, data.getvTeamWatchShort());
        values.put(V_TEAM_WATCH_LONG, data.getvTeamWatchLong());
        values.put(H_TEAM_WATCH_SHORT, data.gethTeamWatchShort());
        values.put(H_TEAM_WATCH_LONG, data.gethTeamWatchLong());
        // insert
        db.insertWithOnConflict(TABLE_NAME,null, values, 5);
        db.endTransaction();
        db.close();
    }

    public GameData getData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        GameData data = new GameData(cursor.getString(1),Boolean.valueOf(cursor.getString(2)),cursor.getString(3),cursor.getString(4),
                cursor.getString(5),Integer.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7)),Boolean.valueOf(cursor.getString(8)),
                cursor.getString(9), cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),
                cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18),cursor.getString(19), cursor.getString(20));

        return data;
    }

    public List<GameData> queryData(String date)
    {
        List<GameData> gameData = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME +" WHERE startDate = " + date;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GameData data = null;

        if (cursor.moveToFirst())
        {
            do
            {
                data =  new GameData(cursor.getString(0),Boolean.valueOf(cursor.getString(1)),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),Integer.valueOf(cursor.getString(5)),Boolean.valueOf(cursor.getString(6)),Boolean.valueOf(cursor.getString(7)),
                        cursor.getString(8), cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),
                        cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18), cursor.getString(19));

                gameData.add(data);
            } while (cursor.moveToNext());
        }
        return gameData;
    }
}