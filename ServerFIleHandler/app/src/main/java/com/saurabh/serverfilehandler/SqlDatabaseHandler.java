package com.saurabh.serverfilehandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlDatabaseHandler extends SQLiteOpenHelper {

    public SqlDatabaseHandler(Context context) {
        super(context, "DatabaseSql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table IpSaver(" +
                "Name text , " +
                "IpName text " +
                ");";
        db.execSQL(sql);

        String messageTable = "Create table MessageTable(" +
                "pack Text, " +
                "title Text," +
                "txt Text" +
                ")";
        db.execSQL(messageTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SaveData(String name, String Ip){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("IpName",Ip);
        SQLiteDatabase database = getWritableDatabase();
        database.insert("IpSaver",null,contentValues);
    }

    public void saveMessage(String pack, String title , String text){
        ContentValues contentValues = new ContentValues();
        contentValues.put("pack",pack);
        contentValues.put("title",title);
        contentValues.put("txt",text);
        SQLiteDatabase database = getWritableDatabase();
        database.insert("MessageTable",null,contentValues);
    }

    public int getIpCount(){
        String sql = "select Name from IpSaver";
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);
        int c=0;
        while (cursor.moveToNext()){
            c++;
        }
        return c;
    }

    public Cursor getIps(){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "select Name, IpName from IpSaver";
        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }
}
