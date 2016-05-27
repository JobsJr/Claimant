package com.example.rajeevkr.wheresmybus.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB Helper class
 *
 * @author rajeevkr
 */
public class PlatformDBOpenHelper extends SQLiteOpenHelper {
    private static PlatformDBOpenHelper platFormDBOpenHelperInstanse;
    private static final String DB_NAME = "platforms.db";
    static final String TABLE_NAME = "platforms";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "( " +
            PlatformInfoContract.PlatformItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PlatformInfoContract.PlatformItems.COLUMN_BUS + " TEXT NOT NULL, " +
            PlatformInfoContract.PlatformItems.COLUMN_ROUTE + " TEXT NOT NULL, " +
            PlatformInfoContract.PlatformItems.COLUMN_PLATFORM + " TEXT NOT NULL" + ");";

    /***
     * Since theres a one-one relation between DBHelper &
     * DB Connnection,The SqliteDatabase object uses
     * locks to keep access serialized.
     * So, if 100 threads have one db instance,
     * calls to the actual on-disk database are serialized.
     */
    public static PlatformDBOpenHelper getInstanse(Context context) {
        if (platFormDBOpenHelperInstanse == null) {
            //Singleton with double-checked locking
            synchronized (PlatformDBOpenHelper.class) {
                if (platFormDBOpenHelperInstanse == null) {
                    platFormDBOpenHelperInstanse = new PlatformDBOpenHelper(context);
                }
            }
        }
        return platFormDBOpenHelperInstanse;
    }

    private PlatformDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
