package com.example.egeudareniafinal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stats.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "stats";
    public static final String COLUMN_ATTEMPT = "attempt";
    public static final String COLUMN_WRONG_WORDS = "wrongWords";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ATTEMPT + " INTEGER," +
                    COLUMN_WRONG_WORDS + " TEXT)";

    public StatsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление таблицы при обновлении версии БД
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Добавление записи с неверными словами для указанной попытки.
     *
     * @param attempt     номер попытки
     * @param wrongWords  строка с неверными словами
     */
    public boolean addWrongWords(int attempt, String wrongWords) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTEMPT, attempt);
        values.put(COLUMN_WRONG_WORDS, wrongWords);
        long result = db.insert(TABLE_NAME, null, values);
        return (result == -1) ? false : true;
    }

    @SuppressLint("Range")
    public void addWordToWrongWords(int attempt, String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Получаем текущее значение столбца COLUMN_WRONG_WORDS для указанной попытки
        String[] columns = {COLUMN_WRONG_WORDS};
        String whereClause = COLUMN_ATTEMPT + "=?";
        String[] whereArgs = {String.valueOf(attempt)};
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null);
        String currentWrongWords = "";
        if (cursor.moveToFirst()) {
            currentWrongWords = cursor.getString(cursor.getColumnIndex(COLUMN_WRONG_WORDS));
        }
        cursor.close();

        // Составляем новое значение для COLUMN_WRONG_WORDS с добавлением слова через пробел
        String newWrongWords = currentWrongWords + ", " + word;

        // Обновляем значение COLUMN_WRONG_WORDS в БД для указанной попытки
        ContentValues values = new ContentValues();
        values.put(COLUMN_WRONG_WORDS, newWrongWords);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public String getWrongWordsString(int attempt) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Запрос на выборку строки со всеми неправильными словами для указанной попытки
        String[] columns = {COLUMN_WRONG_WORDS};
        String whereClause = COLUMN_ATTEMPT + "=?";
        String[] whereArgs = {String.valueOf(attempt)};
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, null, null, null);

        // Получаем строку со всеми неправильными словами из Cursor
        String wrongWordsString = "";
        if (cursor.moveToFirst()) {
            wrongWordsString = cursor.getString(cursor.getColumnIndex(COLUMN_WRONG_WORDS));
        }
        cursor.close();

        return wrongWordsString;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}