package src.role;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
//version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    //每次你对数据表进行编辑，添加时候，你都需要对数据库的版本进行提升

    //数据库版本
    private static final int DATABASE_VERSION=1;

    //date base's name
    private static final String DATABASE_NAME="player.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAYER = "CREATE TABLE " + Player.TABLE
                + "("
                + Player.KEY_NAME + " TEXT PRIMARY KEY AUTOINCREMENT ,"
                + Player.KEY_SCORE + " REAL)";
        db.execSQL(CREATE_TABLE_PLAYER);
        return;
    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDAtabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Player.TABLE);
        onCreate(db);
        return;
    }
}