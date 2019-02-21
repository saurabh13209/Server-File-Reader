package com.saurabh.serverfilehandler;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedDataHolder {
    public static String DatabaseName = "DatabaseMain";

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

    public static String getIp(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        return sharedPreferences.getString("IP","");
    }

    public static void saveIP(Context context,String IP){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IP",IP);
        editor.commit();
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
