package com.saurabh.serverfilehandler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saurabh.volleyhelper.GetRequest;
import com.saurabh.volleyhelper.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList FolderList;
    ArrayList dirList;
    ProgressBar progressBar;
    Toolbar toolbar;
    String serverLink;
    FloatingActionButton floatingActionButton;
    public static boolean isIpChanged = false;
    ProgressDialog sharingLoading;
    byte[] byteArray;
    SwipeRefreshLayout swipeRefreshLayout;


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
        sharingLoading = new ProgressDialog(HomeActivity.this);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        floatingActionButton = findViewById(R.id.floatingMain);
        sharingLoading.setTitle("Please wait");
        sharingLoading.setCancelable(false);
        swipeRefreshLayout = findViewById(R.id.homeSwipe);

        serverLink = "http://" + SharedDataHolder.getIp(HomeActivity.this);
        setClickFunction();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                View view = getLayoutInflater().inflate(R.layout.upload_menu, null);
                builder.setView(view);
                LinearLayout imageSelect = view.findViewById(R.id.imageSelectView);
                LinearLayout videoSelect = view.findViewById(R.id.videoSelectView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                imageSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select image "), 1);
                        alertDialog.dismiss();
                    }
                });

                videoSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            alertDialog.dismiss();
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 152);
                        } else {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            photoPickerIntent.setType("video/*");
                            startActivityForResult(Intent.createChooser(photoPickerIntent, "Select image "), 2);
                            alertDialog.dismiss();
                        }

                    }
                });
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setClickFunction();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 152:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ClipData clipData = data.getClipData();
                sharingLoading.setMessage("");
                sharingLoading.show();
                sendMedia(0, clipData, true);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                ClipData clipData = data.getClipData();
                sharingLoading.setMessage("");
                sharingLoading.show();
                sendMedia(0, clipData, false);
            }
        }

    }

    void sendMedia(final int index, final ClipData clipData, final boolean type) {
        // type: true - image;
        // type: false - video;

        String sendUrl = "http://" + SharedDataHolder.getIp(HomeActivity.this);
        if (type) {
            sendUrl = sendUrl + "/imageUpload.php";
        } else {
            sendUrl = sendUrl + "/videoUpload.php";
        }

        try {
            int tempIndex = index + 1;
            sharingLoading.setMessage("Sending file " + tempIndex + "/" + clipData.getItemCount());
            FileInputStream fis = new FileInputStream(getPath(clipData.getItemAt(index).getUri()));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            for (int readNum; (readNum = fis.read(b)) != -1; ) {
                bos.write(b, 0, readNum);
            }
            byteArray = bos.toByteArray();


            final int m = index;
            new PostRequest() {
                @Override
                public void getResponse(String res) {
                    if (((m + 1) < clipData.getItemCount())) {
                        sendMedia(m + 1, clipData, type);
                    } else {
                        sharingLoading.dismiss();
                    }
                }

                @Override
                public Map setParams() {
                    Map map = new HashMap();
                    map.put("name", "fileReader" + SharedDataHolder.getFileNumber(HomeActivity.this));
                    map.put("image", Base64.encodeToString(byteArray, Base64.DEFAULT));
                    return map;
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(HomeActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }.request(HomeActivity.this, sendUrl);


        } catch (Exception e) {
            Log.v("TAG", e.toString());
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
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
            case R.id.Setting:
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
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
        String homeLink = serverLink + "/index.php?link=/home/saurabh/";
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