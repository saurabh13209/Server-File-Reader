package com.saurabh.serverfilehandler;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class SettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText Link;
    ListView listView;
    FloatingActionButton floatingActionButton;
    SqlDatabaseHandler sqlDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        floatingActionButton = findViewById(R.id.SettingFloat);
        toolbar = findViewById(R.id.SettingTool);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sqlDatabaseHandler = new SqlDatabaseHandler(SettingActivity.this);
        getSupportActionBar().setTitle("Settings");

        Link = findViewById(R.id.LinkAdd);
        listView = findViewById(R.id.SettingList);
        listView.setAdapter(new CustomAdapter());
        Link.setText(SharedDataHolder.getLink(SettingActivity.this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                View view = getLayoutInflater().inflate(R.layout.add_network, null);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final EditText ip = view.findViewById(R.id.addIp);
                final EditText  name = view.findViewById(R.id.addName);
                Button button = view.findViewById(R.id.ipSave);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sqlDatabaseHandler.SaveData(name.getText().toString(), ip.getText().toString());
                        HomeActivity.isIpChanged = true;
                        alertDialog.dismiss();
                        finish();
                    }
                });

            }
        });
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sqlDatabaseHandler.getIpCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.network_each, null);
            TextView ip = convertView.findViewById(R.id.ipText);
            TextView networkName = convertView.findViewById(R.id.networkName);
            CheckBox checkBox = convertView.findViewById(R.id.checkbox);
            checkBox.setClickable(false);
            Cursor cursor = sqlDatabaseHandler.getIps();
            cursor.moveToPosition(position);
            if (cursor.getString(1).equals(HomeActivity.Ip)) {
                checkBox.setChecked(true);
            }
            networkName.setText(cursor.getString(0));
            ip.setText(cursor.getString(1));
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        SharedDataHolder.saveLink(SettingActivity.this, Link.getText().toString());
        super.onBackPressed();
    }

}
