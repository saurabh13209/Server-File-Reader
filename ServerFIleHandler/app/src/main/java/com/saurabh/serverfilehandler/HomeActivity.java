package com.saurabh.serverfilehandler;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.saurabh.volleyhelper.GetRequest;
import com.saurabh.volleyhelper.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class HomeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList FolderList;
    ArrayList dirList;
    ProgressBar progressBar;
    Toolbar toolbar;
    String serverLink;
    FloatingActionButton floatingActionButton;
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
        floatingActionButton = findViewById(R.id.floatingMain);

        serverLink = "http://" + SharedDataHolder.getIp(HomeActivity.this);
        setClickFunction();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select image "), 1);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                try{
                    ClipData clipData = data.getClipData();
                    int indexMain = 0;
                    sendImage(indexMain,clipData);
                }catch (Exception e){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData() );
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            } else {
            }


    }

    void sendImage(int index , final ClipData clipData){
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), clipData.getItemAt(index).getUri());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            final int m = index;
            new PostRequest() {
                @Override
                public void getResponse(String res) {
                    if ((m+1)<clipData.getItemCount()){
                        sendImage(m+1,clipData);
                    }
                }

                @Override
                public Map setParams() {
                    Map map = new HashMap();
                    map.put("name", "fileReader" + new Random().nextInt(10000));
                    map.put("image", Base64.encodeToString(byteArray, Base64.DEFAULT));
                    return map;
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(HomeActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }.request(HomeActivity.this, "http://" + SharedDataHolder.getIp(HomeActivity.this) + "/imageUpload.php");

        } catch (IOException e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    byte[] byteArray;
    Bitmap bitmap;

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