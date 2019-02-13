package com.saurabh.serverfilehandler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saurabh.volleyhelper.GetRequest;
import com.saurabh.volleyhelper.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    String mainLink;
    String homeLink;
    ListView listView;
    ArrayList FolderList;
    ArrayList dirList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainLink = SharedDataHolder.getLink(HomeActivity.this);
        mainLink = "http://" + mainLink + ".ngrok.io/index.php";
        homeLink = "/home/saurabh/";
        listView = findViewById(R.id.HomeList);
        FolderList = new ArrayList();
        dirList = new ArrayList();
        progressBar = findViewById(R.id.progressHome);
        setClickFunction();
    }

    void formPath() {
        homeLink = "/home/saurabh/";
        for (int i = 0; i < dirList.size(); i++) {
            homeLink = homeLink + dirList.get(i).toString() + "/";
        }
    }

    @Override
    public void onBackPressed() {
        dirList.remove(dirList.size() - 1);
        formPath();
        setClickFunction();
    }

    void setClickFunction() {
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        new PostRequest() {
            @Override
            public void getResponse(String res) {
                try {
                    FolderList = new ArrayList();
                    listView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONArray jsonArray = new JSONArray(res);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        FolderList.add(jsonArray.getString(i));
                    }
                    CustomAdapter customAdapter = new CustomAdapter(FolderList, getLayoutInflater(), HomeActivity.this) {
                        @Override
                        void OnClick(String data) {
                            for (int i=0 ; i<dirList.size() ; i++){
                                Log.v("TAG","PLACE :"+dirList.get(i));
                            }
                            if (data.endsWith(".txt") || data.endsWith(".pdf") || data.endsWith(".py")) {
                                String DL = new String();
                                for (int i=0 ; i<dirList.size() ; i++){
                                    Log.v("TAG","dir :"+dirList.get(i));
                                    DL = DL+"/"+dirList.get(i).toString();
                                }
                                DL = DL+"/"+data;
                                DL = DL.replace(" ","%20");
                                Log.v("TAG",DL);
                                startActivity(new Intent(HomeActivity.this , WebActivity.class).putExtra("LINK","http://drive.google.com/viewerng/viewer?embedded=true&url=http://2d3da987.ngrok.io/"+DL));
                            } else {
                                mainLink = "http://" + SharedDataHolder.getLink(HomeActivity.this) + ".ngrok.io/index.php";
                                dirList.add(data);
                                formPath();
                                setClickFunction();
                            }
                        }
                    };
                    listView.setAdapter(customAdapter);
                } catch (JSONException e) {
                    Toast.makeText(HomeActivity.this, "Downloaded.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public Map setParams() {
                Map map = new HashMap();
                map.put("link", homeLink);
                return map;
            }

            @Override
            public void onError(String err) {

            }
        }.request(HomeActivity.this, mainLink);

    }

}