package com.example.egeudareniafinal;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WordDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "word.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WORDS = "words";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CORRECT_WORD = "correct_word";
    public static final String COLUMN_WRONG_WORD = "wrong_word";
    // изменяем название колонки
    public static final String COLUMN_CORRECT_DIGIT = "correct_digit";
    // добавляем новую колонку
    public static final String COLUMN_WRONG_DIGIT = "wrong_digit";

    private static final String CREATE_TABLE_WORDS =
            "CREATE TABLE " + TABLE_WORDS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CORRECT_WORD + " TEXT, " +
                    COLUMN_WRONG_WORD + " TEXT, " +
                    // изменяем название колонки в запросе создания таблицы
                    COLUMN_CORRECT_DIGIT + " INTEGER DEFAULT 0, " +
                    // добавляем новую колонку в запрос создания таблицы
                    COLUMN_WRONG_DIGIT + " INTEGER DEFAULT 0);";

    public WordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // добавляем обновление таблицы на новую версию
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_WORDS +
                    " ADD COLUMN " + COLUMN_WRONG_DIGIT + " INTEGER DEFAULT 0;");
        }
    }

    public void updateCorrectDigitByCorrectWord(String correctWord, int newDigit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CORRECT_DIGIT, newDigit);
        db.update(TABLE_WORDS, values, COLUMN_CORRECT_WORD + " = ?", new String[]{correctWord});
    }

    public void updateWrongDigitByWrongWord(String wrongWord, int newDigit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WRONG_DIGIT, newDigit);
        db.update(TABLE_WORDS, values, COLUMN_WRONG_WORD + " = ?", new String[]{wrongWord});
    }

    public void addWord(String correctWord, String wrongWord, int correctDigit, int wrongDigit) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if (correctWord != null && wrongWord != null) { // проверка на null
            values.put(COLUMN_CORRECT_WORD, correctWord);
            values.put(COLUMN_WRONG_WORD, wrongWord);
            values.put(COLUMN_CORRECT_DIGIT, correctDigit);
            // добавляем значение в новую колонку
            values.put(COLUMN_WRONG_DIGIT, wrongDigit);
            db.insertWithOnConflict(TABLE_WORDS, null, values, SQLiteDatabase.CONFLICT_IGNORE); // используем insertWithOnConflict()
        }
    }

    public boolean hasCorrectWord(String correctWord) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_WORDS, new String[]{COLUMN_CORRECT_WORD}, COLUMN_CORRECT_WORD + "=?", new String[]{correctWord}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public int getCorrectDigitByCorrectWord(String correctWord) {
        SQLiteDatabase db = getReadableDatabase();
        int digit = 0;
        Cursor cursor = db.query(TABLE_WORDS, new String[]{COLUMN_CORRECT_DIGIT}, COLUMN_CORRECT_WORD + " = ?", new String[]{correctWord}, null, null, null);
        if (cursor.moveToFirst()) {
            digit = cursor.getInt(cursor.getColumnIndex(COLUMN_CORRECT_DIGIT));
        }
        cursor.close();
        return digit;
    }

    @SuppressLint("Range")
    public int getWrongDigitByWrongWord(String wrongWord) {
        SQLiteDatabase db = getReadableDatabase();
        int digit = 0;
        Cursor cursor = db.query(TABLE_WORDS, new String[]{COLUMN_WRONG_DIGIT}, COLUMN_WRONG_WORD + " = ?", new String[]{wrongWord}, null, null, null);
        if (cursor.moveToFirst()) {
            digit = cursor.getInt(cursor.getColumnIndex(COLUMN_WRONG_DIGIT));
        }
        cursor.close();
        return digit;
    }
}