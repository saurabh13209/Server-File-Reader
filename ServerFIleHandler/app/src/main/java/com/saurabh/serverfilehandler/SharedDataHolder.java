package com.saurabh.serverfilehandler;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedDataHolder {
    public static String DatabaseName = "DatabaseMain";

    public static void saveMessageLink(Context context, String link){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MESSAGE_LINK",link);
        editor.commit();
    }

    public static String getMessageLink(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        return sharedPreferences.getString("MESSAGE_LINK","");
    }

    public static  void setNotificationRun(Context context, boolean isRun){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isRun){
            editor.putString("isRun","1");
        }else {
            editor.putString("isRun","0");
        }
    }

    public static boolean getNotificationRun(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("isRun","").equals("0")){
            return false;
        }else {
            return true;
        }
    }

    public static int getFileNumber(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        String n = sharedPreferences.getString("FileNumber","");
        if (n.equals("")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FileNumber",1+"");
            editor.commit();
            return 0;
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("FileNumber",String.valueOf(Integer.valueOf(n)+1));
            editor.commit();
            return Integer.valueOf(n);
        }
    }

}
