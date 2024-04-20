package com.example.at_project_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TEXT + " TEXT, " +
            COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades
    }

    public void insertData(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<HistoryItem> getAllHistory() {
        List<HistoryItem> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_TEXT, COLUMN_TIMESTAMP};
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, COLUMN_TIMESTAMP + " DESC");

        if (cursor != null) {
            int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int textIndex = cursor.getColumnIndexOrThrow(COLUMN_TEXT);
            int timestampIndex = cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String text = cursor.getString(textIndex);
                String timestamp = cursor.getString(timestampIndex);
                HistoryItem item = new HistoryItem(id, text, timestamp);
                historyList.add(item);
            }
            cursor.close();
        }

        db.close();
        return historyList;
    }


    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    // Method to retrieve all extracted texts
    public List<HistoryItem> getAllTexts() {
        List<HistoryItem> texts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TEXT, COLUMN_TIMESTAMP},
                null, null, null, null, COLUMN_TIMESTAMP + " DESC");
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int idIndex = cursor.getColumnIndex(COLUMN_ID);
                        int textIndex = cursor.getColumnIndex(COLUMN_TEXT);
                        int timestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP);
                        if (idIndex != -1 && textIndex != -1 && timestampIndex != -1) {
                            long id = cursor.getLong(idIndex);
                            String text = cursor.getString(textIndex);
                            String timestamp = cursor.getString(timestampIndex);
                            texts.add(new HistoryItem(id, text, timestamp));
                        } else {
                            // Handle error: Column index not found
                            Log.e("DBHelper", "Column index not found");
                        }
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                // Handle exception
                Log.e("DBHelper", "Error retrieving texts from database: " + e.getMessage());
            } finally {
                cursor.close();
            }
        } else {
            // Handle error: Cursor is null
            Log.e("DBHelper", "Cursor is null");
        }
        db.close();
        return texts;
    }


    // Method to delete a specific text entry
    public void deleteText(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}


