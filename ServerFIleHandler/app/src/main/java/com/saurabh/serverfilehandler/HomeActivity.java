package com.saurabh.serverfilehandler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.saurabh.volleyhelper.GetRequest;
import com.saurabh.volleyhelper.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ListView listView;
    ArrayList FolderList;
    ArrayList dirList;
    ProgressBar progressBar;
    Toolbar toolbar;
    String serverLink;
    public static String Ip = "";
    public static boolean isIpChanged = false;
    FloatingActionButton floatingActionButton;
    ProgressDialog sharingLoading;
    byte[] byteArray;
    SwipeRefreshLayout swipeRefreshLayout;
    SqlDatabaseHandler sqlDatabaseHandler;


    @Override
    protected void onResume() {
        if (isIpChanged) {
            isIpChanged = false;
            setIpCheck(0);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Toolbar toolbar = findViewById(R.id.HomeTool);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.HomeList);
        FolderList = new ArrayList();
        dirList = new ArrayList();
        progressBar = findViewById(R.id.progressHome);
        sqlDatabaseHandler = new SqlDatabaseHandler(HomeActivity.this);
        sharingLoading = new ProgressDialog(HomeActivity.this);
        floatingActionButton = findViewById(R.id.floatingMain);
        sharingLoading.setTitle("Please wait");
        sharingLoading.setCancelable(false);
        swipeRefreshLayout = findViewById(R.id.homeSwipe);

        setIpCheck(0);

        serverLink = "http://" + Ip;
        setClickFunction();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                View view = getLayoutInflater().inflate(R.layout.upload_menu, null);
                builder.setView(view);
                LinearLayout imageSelect = view.findViewById(R.id.imageSelectView);
                LinearLayout audioSelect = view.findViewById(R.id.audioSelectView);
                LinearLayout bigSelect = view.findViewById(R.id.bigSelectView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                imageSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            alertDialog.dismiss();
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 152);
                        } else {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            photoPickerIntent.setType("*/*");
                            startActivityForResult(Intent.createChooser(photoPickerIntent, "Select media "), 1);
                            alertDialog.dismiss();
                        }
                    }
                });


                audioSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            alertDialog.dismiss();
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 152);
                        } else {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            photoPickerIntent.setType("audio/*");
                            startActivityForResult(Intent.createChooser(photoPickerIntent, "Select image "), 3);
                            alertDialog.dismiss();
                        }
                    }
                });

                bigSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 152);
                        } else {
                            new MaterialFilePicker()
                                    .withActivity(HomeActivity.this)
                                    .withRequestCode(120)
                                    .start();
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


    private void setIpCheck(final int index) {
        final Cursor cursor = sqlDatabaseHandler.getIps();
        if (cursor.getCount()==0 || cursor==null){
            Toast.makeText(this, "Please add network in Settings", Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.moveToPosition(index);
        Log.v("TAG", cursor.getString(1));
        new PostRequest() {
            @Override
            public void getResponse(String res) {
                Cursor cursor1 = sqlDatabaseHandler.getIps();
                cursor1.moveToPosition(index);
                Ip = cursor1.getString(1);
                Toast.makeText(HomeActivity.this, "" + Ip, Toast.LENGTH_SHORT).show();
                serverLink = "http://" + Ip;
                setClickFunction();
            }

            @Override
            public Map setParams() {
                return null;
            }

            @Override
            public void onError(String err) {
                Log.v("TAG", err);
                Log.v("TAG", "http://" + cursor.getString(1));
                if (index + 1 < sqlDatabaseHandler.getIpCount()) {
                    setIpCheck(index + 1);
                } else {
                    Toast.makeText(HomeActivity.this, "No network found", Toast.LENGTH_SHORT).show();
                }
            }
        }.request(HomeActivity.this, "http://" + cursor.getString(1));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 152:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ClipData clipData = data.getClipData();
                sharingLoading.setMessage("");
                sharingLoading.show();
                sendMedia(0, clipData);
            }
        }
        if (requestCode == 3) {
            ClipData clipData = data.getClipData();
            sharingLoading.setMessage("");
            sharingLoading.show();
            String s = "/storage/emulated/0/" + clipData.getItemAt(0).getUri().getLastPathSegment().substring(8);
            sendAudio(0, s, clipData);
        }

        if (requestCode == 120 && resultCode == RESULT_OK) {

            sharingLoading.setMessage("Loading");
            sharingLoading.show();
            ;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type = getMimeType(f.getPath());

                    String file_path_2 = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type", content_type)
                            .addFormDataPart("uploaded_file", file_path_2.substring(file_path_2.lastIndexOf("/") + 1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url(serverLink + "/upload.php")
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        if (!response.isSuccessful()) {
                            Toast.makeText(HomeActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.v("TAG", response.message().toString());
                            sharingLoading.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        }

    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    void sendAudio(final int index, String s, final ClipData clipData) {
        FileInputStream fis = null;
        sharingLoading.setMessage("");
        int tempIndex = index + 1;


        try {
            sharingLoading.setMessage("Sending file " + tempIndex + "/" + clipData.getItemCount());
            fis = new FileInputStream(s);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            for (int readNum; (readNum = fis.read(b)) != -1; ) {
                bos.write(b, 0, readNum);
            }
            byteArray = bos.toByteArray();
            new PostRequest() {
                @Override
                public void getResponse(String res) {
                    Log.v("TAG", res);
                    if (((index + 1) < clipData.getItemCount())) {
                        String s = "/storage/emulated/0/" + clipData.getItemAt(index + 1).getUri().getLastPathSegment().substring(8);
                        sendAudio(index + 1, s, clipData);
                    } else {
                        sharingLoading.dismiss();
                    }
                }

                @Override
                public Map setParams() {
                    Map map = new HashMap();
                    map.put("name", "fileReader" + SharedDataHolder.getFileNumber(HomeActivity.this));
                    map.put("image", Base64.encodeToString(byteArray, Base64.DEFAULT));
                    Log.v("TAG", "d");
                    return map;
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(HomeActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                }
            }.request(HomeActivity.this, "http://" + Ip + "/audioUpload.php");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void sendMedia(final int index, final ClipData clipData) {
        String sendUrl = serverLink + "/mediaUpload.php";

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
                        sendMedia(m + 1, clipData);
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
                    Toast.makeText(HomeActivity.this, "Kickme", Toast.LENGTH_SHORT).show();
                }


            }
        }.request(HomeActivity.this, formPath());

    }


    String formPath() {
        String homeLink = serverLink + "/index.php?link=/home/" + SharedDataHolder.getHomeDir(HomeActivity.this) + "/";
        for (int i = 0; i < dirList.size(); i++) {
            homeLink = homeLink + dirList.get(i).toString() + "/";
        }
        homeLink = homeLink.replace(" ", "%20");
        return homeLink;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            dirList.remove(dirList.size() - 1);
            setClickFunction();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}