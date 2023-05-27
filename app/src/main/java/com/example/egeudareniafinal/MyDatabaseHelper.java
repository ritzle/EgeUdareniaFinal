package com.example.egeudareniafinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    private static final String TABLE_NAME = "attempts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ATTEMPT_NUMBER = "attempt";
    private static final String COLUMN_WRONG_WORDS = "wrongWords";

    // SQL statement to create the table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ATTEMPT_NUMBER + " INTEGER," +
                    COLUMN_WRONG_WORDS + " TEXT)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    public void appendToWrongWords(int attempt, String newWord) {
        SQLiteDatabase db = getWritableDatabase();

        // Get the current value of wrongWords for the specified attempt
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_WRONG_WORDS},
                COLUMN_ATTEMPT_NUMBER + " = ?",
                new String[]{String.valueOf(attempt)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));

            // Append the new word to the current value of wrongWords
            String currentWrongWords = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRONG_WORDS));
            String updatedWrongWords = currentWrongWords.isEmpty() ? newWord : currentWrongWords + " " + newWord;

            ContentValues values = new ContentValues();
            values.put(COLUMN_WRONG_WORDS, updatedWrongWords);

            // Update the row in the database
            db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void addAttempt(int attemptNumber, String wrongWords) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTEMPT_NUMBER, attemptNumber);
        values.put(COLUMN_WRONG_WORDS, wrongWords);

        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    public int getAttemptsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void deleteAttempt(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ATTEMPT_NUMBER + " = ?", new String[]{String.valueOf(id)});
    }

    public String getWrongWords(int attempt) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_WRONG_WORDS},
                COLUMN_ATTEMPT_NUMBER + " = ?",
                new String[]{String.valueOf(attempt)},
                null, null, null);

        String wrongWords = "";
        if (cursor != null && cursor.moveToFirst()) {
            wrongWords = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WRONG_WORDS));
        }

        if (cursor != null) {
            cursor.close();
        }

        return wrongWords;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("VACUUM");
    }

}