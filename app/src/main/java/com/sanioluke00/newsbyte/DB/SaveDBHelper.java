package com.sanioluke00.newsbyte.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sanioluke00.newsbyte.Models.NewsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SaveDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SaveDB";
    public static final String TABLE_NAME = "ReadLaterTable";
    public static final String COL_1 = "author";
    public static final String COL_2 = "title";
    public static final String COL_3 = "description";
    public static final String COL_4 = "url";
    public static final String COL_5 = "urlToImage";
    public static final String COL_6 = "publishedAt";
    public static final String COL_7 = "content";
    public static final String COL_8 = "sourcename";
    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, " + COL_1 + " TEXT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT, " + COL_8 + " TEXT)";

    public SaveDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    } // Create the database.

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    } // Upgrade the database.

    public void add_SaveNews(String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String source_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, author);
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, url);
        contentValues.put(COL_5, urlToImage);
        contentValues.put(COL_6, publishedAt);
        contentValues.put(COL_7, content);
        contentValues.put(COL_8, source_name);
        db.insert(TABLE_NAME, null, contentValues);
    } //Add a news data

    public void deleteNews(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_4 + "= '" + url + "'";
        Log.d("delete_query", "deleteName: query: " + query);
        db.execSQL(query);
    } // Delete a news data

    @SuppressLint("Recycle")
    public boolean checkNews(String url) {
        boolean check_news;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " where " + COL_4 + "=\"" + url + "\"", null);
        Log.e("check_news_query", "Query is : " + cursor);
        check_news = cursor.getCount() > 0;

        if (cursor.getColumnCount() > 0) {
            Log.e("check_news_count", "The size of the saved news is : " + cursor.getCount());
        } else {
            Log.e("check_news_count", "The size of the saved news is : 0");
        }
        return check_news;
    } //Check if a specific news from the saved exists or not.

    @SuppressLint("Recycle")
    public ArrayList<NewsModel> getAllSavedNews() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<NewsModel> arrayList = new ArrayList<>();
        Cursor cursor_1 = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        cursor_1.moveToFirst();
        while (!cursor_1.isAfterLast()) {
            arrayList.add(new NewsModel(
                    cursor_1.getString(cursor_1.getColumnIndex(COL_1)), cursor_1.getString(cursor_1.getColumnIndex(COL_2)),
                    cursor_1.getString(cursor_1.getColumnIndex(COL_3)), cursor_1.getString(cursor_1.getColumnIndex(COL_4)),
                    cursor_1.getString(cursor_1.getColumnIndex(COL_5)), cursor_1.getString(cursor_1.getColumnIndex(COL_6)),
                    cursor_1.getString(cursor_1.getColumnIndex(COL_7)), cursor_1.getString(cursor_1.getColumnIndex(COL_8))));
            cursor_1.moveToNext();
        }
        return arrayList;
    } //Get all the saved news.


}
