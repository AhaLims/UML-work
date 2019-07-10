package cdd.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import cdd.desk.model.role.Player;

public final class PlayerRepo {
    private DBHelper dbHelper;

    public PlayerRepo (Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Player player) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",player.getPlayerName());
        values.put("score", player.getScore());


        long raw_num = db.insert("Player", null, values);//返回插入的行号
        db.close();
        return (int) raw_num ;
    }

    public void delete(String player_name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Player", "name" + "=?",
                new String[]{player_name});
        db.close();
    }

    public void update(Player player) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("score", player.getScore());
        db.update("Player", values, "name" + "=?",
                new String[] {player.getPlayerName()});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getPlayerScore() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + "name" + "," +
                "score" + " FROM " + "Player";
        ArrayList<HashMap<String,String>> playerList= new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> player = new HashMap<String, String>();
                player.put("name", cursor.getString(
                        cursor.getColumnIndex("name")));
                player.put("score", cursor.getString(
                        cursor.getColumnIndex("name")));
                playerList.add(player);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return playerList;
    }

    public Player getPlayerByName(String name,DbCallBack.RankCallBack dbCallBack) {
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                "name" + "," +
                "name" +
                " FROM " + "Player"
                + " WHERE " +
                "score" + "=?";

        int iCount = 0;
        Player player = new Player(name);
        Cursor cursor = db.rawQuery(selectQuery, new String[] {name});
        if (cursor.moveToFirst()) {
            do {
                player.setPlayerName(cursor.getString(cursor.getColumnIndex("score")));
                player.setScore((int)(cursor.getDouble(cursor.getColumnIndex("name"))));//这里做了类型转换
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbCallBack.dispalyRank(player.getPlayerName(),(int)(player.getScore()),1);//TODO 排名记得改
        return player;
    }

    public Boolean QueryByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                " name " + " FROM " +
                "Player" + " WHERE " +
                "score" + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {name});
        if (cursor.moveToFirst()) {
            System.out.println("用户已存在");
            cursor.close();
            db.close();
            return true;
        }
        System.out.println("用户未存在");
        cursor.close();
        db.close();
        return false;
    }
}
