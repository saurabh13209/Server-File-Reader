package com.saurabh.serverfilehandler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.saurabh.volleyhelper.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList FolderList;
    ArrayList dirList;
    ProgressBar progressBar;
    Toolbar toolbar;
    String serverLink;
    public static boolean isIpChanged = false;

    @Override
    protected void onResume() {
        if (isIpChanged) {
            serverLink = "http://" + SharedDataHolder.getIp(HomeActivity.this);
            isIpChanged = false;
            setClickFunction();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listView = findViewById(R.id.HomeList);
        FolderList = new ArrayList();
        dirList = new ArrayList();
        progressBar = findViewById(R.id.progressHome);
        toolbar = findViewById(R.id.HomeTool);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        serverLink = "http://" + SharedDataHolder.getIp(HomeActivity.this);
        setClickFunction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.CreateFile:
                break;
            case R.id.Setting:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                break;
            case R.id.CraeteFolder:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void setClickFunction() {
        listView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Log.v("TAG", formPath());
        new GetRequest() {
            @Override
            public void getResponse(String res) {
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                FolderList = new ArrayList();

                try {
                    JSONArray jsonArray = new JSONArray(res);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FolderList.add(jsonArray.getString(i));
                    }

                    CustomAdapter customAdapter = new CustomAdapter(FolderList, getLayoutInflater(), HomeActivity.this) {
                        @Override
                        void OnClick(String data) {

                            if (data.contains(".")) {
                                String localData = serverLink;
                                for (int i = 0; i < dirList.size(); i++) {
                                    localData = localData + "/" + dirList.get(i);
                                }
                                localData = localData + "/" + data;
                                localData = localData.replace(" ", "%20");
                                if (data.endsWith(".pdf")) {
                                    startActivity(new Intent(HomeActivity.this, PDFActivity.class).putExtra("LINK", localData));
                                } else {
                                    startActivity(new Intent(HomeActivity.this, WebActivity.class).putExtra("LINK", localData));
                                    Log.v("TAG", localData);
                                }
                            } else {
                                dirList.add(data);
                                setClickFunction();
                            }
                        }
                    };

                    listView.setAdapter(customAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.request(HomeActivity.this, formPath());

    }


    String formPath() {
        String homeLink = serverLink + "/index.php?link=" + GetLinks.homeLink;
        for (int i = 0; i < dirList.size(); i++) {
            homeLink = homeLink + dirList.get(i).toString() + "/";
        }
        homeLink = homeLink.replace(" ", "%20");
        return homeLink;
    }

    @Override
    public void onBackPressed() {
        dirList.remove(dirList.size() - 1);
        setClickFunction();
    }

}