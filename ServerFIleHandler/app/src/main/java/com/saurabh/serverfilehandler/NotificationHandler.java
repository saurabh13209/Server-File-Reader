package com.saurabh.serverfilehandler;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import com.saurabh.volleyhelper.PostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationHandler extends NotificationListenerService {
    Context context;
    static ArrayList packArray = new ArrayList();
    static ArrayList titleArray = new ArrayList();
    static ArrayList textArray = new ArrayList();

    String pack;
    String title;
    String text;

    SqlDatabaseHandler sqlDatabaseHandler;

    static boolean canSend = true;

    @Override

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sqlDatabaseHandler = new SqlDatabaseHandler(context);

    }
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {

        if (!SharedDataHolder.getNotificationRun(getApplicationContext())){
            return;
        }
        Bundle extras = sbn.getNotification().extras;
        pack = sbn.getPackageName();
        title = extras.getString("android.title");
        if (extras.getCharSequence("android.text") != null){
            text =     extras.getCharSequence("android.text").toString();
        }else {
            text = "";
        }

        packArray.add(pack);
        titleArray.add(title);
        textArray.add(text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (packArray.size() == 0) {
                    return;
                }
                if (packArray.get(0).toString().equals("com.whatsapp")) {
                    if (packArray.size() == 2) {
                        if (canSend) {
                            Log.v("TAG", packArray.get(0).toString()+ " : "+textArray.get(0).toString());
                            sendMessage(packArray.get(0).toString() ,titleArray.get(0).toString(), textArray.get(0).toString());
                            packArray = new ArrayList();
                            textArray = new ArrayList();
                            titleArray = new ArrayList();
                             canSend = false;
                        }else {
                            canSend = true;
                        }

                    } else {
                        packArray.remove(0);
                        textArray.remove(0);
                        titleArray.remove(0);
                    }
                } else {
                    Log.v("TAG", packArray.get(packArray.size()-1).toString()+ " : "+textArray.get(0).toString());

                    sendMessage(packArray.get(packArray.size() -1).toString() ,titleArray.get(titleArray.size() -1).toString(), textArray.get(textArray.size() - 1).toString());

                    packArray = new ArrayList();
                    textArray = new ArrayList();
                    titleArray = new ArrayList();

                }
            }

            public void sendMessage(final String pack, final String title, final String text){
                new PostRequest() {
                @Override
                public void getResponse(String res) {

                }

                @Override
                public Map setParams() {
                    Map map = new HashMap();
                    map.put("Title",title);
                    map.put("Text",text);
                    map.put("Pack",pack);
                    return map;
                }

                @Override
                public void onError(String err) {
                    setIpCheck(0);
                }
            }.request(context , "http://" + SharedDataHolder.getMessageLink(context) + "/noti.php");

            }
        }, 1000);
    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {

    }


    public void setIpCheck(final int index) {
        final Cursor cursor = sqlDatabaseHandler.getIps();
        if (cursor.getCount()==0 || cursor==null){
            Toast.makeText(this, "Please add network in Settings", Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.moveToPosition(index);
        new PostRequest() {
            @Override
            public void getResponse(String res) {
                Cursor cursor1 = sqlDatabaseHandler.getIps();
                cursor1.moveToPosition(index);
                String Ip = cursor1.getString(1);
                SharedDataHolder.saveMessageLink(context, Ip);
                }

            @Override
            public Map setParams() {
                return null;
            }

            @Override
            public void onError(String err) {
                if (index + 1 < sqlDatabaseHandler.getIpCount()) {
                    setIpCheck(index + 1);
                } else {
                    Toast.makeText(context, "No network found", Toast.LENGTH_SHORT).show();
                }
            }
        }.request(context, "http://" + cursor.getString(1));
    }

}
