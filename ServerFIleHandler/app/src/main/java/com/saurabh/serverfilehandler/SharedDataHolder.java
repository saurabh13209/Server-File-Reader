package com.saurabh.serverfilehandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public static void saveLink(Context context,String Link){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LINK",Link);
        editor.commit();
    }

    public static String getLink(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        return sharedPreferences.getString("LINK","");
    }

    public static void setHomeDir(Context context, String homeDir){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("homeDir",homeDir);
        editor.commit();
    }

    public static String getHomeDir(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        return sharedPreferences.getString("homeDir","");
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
