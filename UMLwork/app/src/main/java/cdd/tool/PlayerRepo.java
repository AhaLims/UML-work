package cdd.tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                        cursor.getColumnIndex("score")));
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
                " score" +
                " FROM " + "Player"
                + " WHERE " +
                "name" + "=?";

        int iCount = 0;
        Player player = new Player(name);
        Cursor cursor = db.rawQuery(selectQuery, new String[] {name});
        if (cursor.moveToFirst()) {
            do {
                player.setPlayerName(cursor.getString(cursor.getColumnIndex("name")));
                Log.e("", "getPlayerByName: " + name );
                player.setScore((int)(cursor.getDouble(cursor.getColumnIndex("score"))));//这里做了类型转换
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        int accout=1;
//调用函数获取
        List<HashMap<String,String>> list =getPlayerScore();
        for(HashMap<String,String>item : list) {
            Log.e("", "getPlayerByName: list:score" + item.get("score") );
            Log.e(""," list:name" + item.get("name"));
            if(Integer.valueOf(item.get("score"))> player.getScore())
            accout++;
        }



        String ary[][]=new String[200][2];
        for(int i=0;i<=9;i++)
        {
            ary[i][0]="***";
            ary[i][1]="0";
        }
        int num=0;
        for(HashMap<String,String>item : list) {
            ary[num][0]=item.get("name");
            ary[num][1]=item.get("score");
            num++;
        }
        for(int i=0;i<=num-2;i++)
        {
            int temp=i;
            for(int j=i+1;j<=num-1;j++)
            {
                if(Integer.parseInt(ary[temp][1])<Integer.parseInt(ary[j][1]))
                {
                    temp=j;
                }
            }
            if(temp!=i)
            {
                String temp_s1=ary[i][0];
                String temp_s2=ary[i][1];
                ary[i][0]=ary[temp][0];
                ary[i][1]=ary[temp][1];
                ary[temp][0]=temp_s1;
                ary[temp][1]=temp_s2;
            }
        }

        dbCallBack.dispalyRank(player.getPlayerName(),(int)(player.getScore()),accout,ary);//TODO 排名记得改

        return player;
    }

    public Boolean QueryByName(String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                " name " + " FROM " +
                "Player" + " WHERE " +
                " name " + " =?";
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
