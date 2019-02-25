package com.saurabh.serverfilehandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
//
//    public static String[] getIps(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
//        String[] ip ;
//        int index=0;
//        for(index=0 ; index<getIpNumber(context) ; index++);
//        ip = new String[index];
//        for (int i=0;i<index;i++){
//            ip[i] = sharedPreferences.getString("IP"+i,"");
//        }
//        return ip;
//    }
//
//    public static int getIpNumber(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
//        if (sharedPreferences.getString("IpNumber","").equals("")){
//            return 0;
//        }else {
//            int i = Integer.valueOf(sharedPreferences.getString("IpNumber",""));
//            return i;
//        }
//    }
//
//    private static int getIPNumber(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
//        String n = sharedPreferences.getString("IpNumber","");
//        if(n.equals("")){
//            SharedPreferences.Editor editor =sharedPreferences.edit();
//            editor.putString("IpNumber",1+"");
//            editor.commit();
//            return 0;
//        }else {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("FileNumber",String.valueOf(Integer.valueOf(n)+1));
//            editor.commit();
//            return Integer.valueOf(n+1);
//        }
//    }
//
//    public static void saveIP(Context context,String IP){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(DatabaseName,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        int t = getIps(context).length+1;
//        editor.putString("IP"+t,IP);
//        Log.v("TAG","dvd"+t);
//        editor.commit();
//    }

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
