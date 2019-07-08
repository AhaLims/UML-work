package src.role;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerRepo {
    private DBHelper dbHelper;

    public PlayRepo (Context context) {
        dbHelper = new DBHelper(context);
    }

    public String insert(Player player) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Player.KEY_SCORE, player.score);


        long player_name = db.insert(Player.TABLE, null, values);
        db.close();
        return String.valueOf(player_name);
    }

    public void delete(String player_name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Player.TABLE, Player.KEY_NAME+"=?",
                new String[]{player_name});
        db.close();
    }

    public void update(Player player) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Player.KEY_SCORE, player.score);
        db.update(Player.TABLE, values, Player.KEY_NAME+"=?",
                new String[] {player_name});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getPlayerScore() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + Player.KEY_NAME + "," +
                Player.KEY_SCORE + " FROM " + Player.TABLE;
        ArrayList<HashMap<String,String>> playerList= new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<Sting, String> player = new HashMap();
                player.put("name", cursor.getString(
                        cursor.getColumnIndex(Player.KEY_NAME)));
                player.put("score", cursor.getString(
                        cursor.getColumnIndex(Player.KEY_SCORE)));
                playerList.add(player);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return playerList;
    }

    public Player getPlayerByName(String name) {
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Player.KEY_NAME + "," +
                Player.KEY_SCORE + "," +
                " FROM " + Player.TABLE
                + " WHERE " +
                Player.KEY_NAME + "=?";

        int iCount = 0;
        Player player = new player();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {name});
        if (cursor.moveToFirst()) {
            do {
                player.player_name = cursor.getString(cursor.getColumnIndex(Player.KEY_NAME));
                player.score = cursor.getDouble(cursor.getColumnIndex(Player.KEY_NAME));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return player;
    }
}
