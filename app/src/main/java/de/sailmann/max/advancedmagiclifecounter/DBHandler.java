package de.sailmann.max.advancedmagiclifecounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

/**
 * Created by Admin on 14.01.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    //Name der Datenbank
    private static final String DATABASE_NAME = "statistic.db";

    //Name der Tabelle
    private static final String TABLE_NAME_GAMES = "games";

    //Inhalt der Tabelle
    private static final String GAME = "game";

    //Tabelle anlegen
    private static final String TABLE_GAMES_CREATE = "CREATE TABLE " + TABLE_NAME_GAMES + " (" + GAME + " TEXT);";

    //Tabelle löschen
    private static final String TABLE_GAMES_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME_GAMES;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_GAMES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_GAMES_DELETE);
        onCreate(db);
    }

    public void insert(String game) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GAME, game);
            db.insert(TABLE_NAME_GAMES, null, values);
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getGames() {
        ArrayList<String> games = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME_GAMES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String s = cursor.getString(0);
                games.add(s);
            } while (cursor.moveToNext());
        }
        return games;
    }

    public void deleteGames() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_GAMES);
    }
}
