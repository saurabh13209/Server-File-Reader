package com.saurabh.serverfilehandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText Link , Ip;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.SettingTool);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");

        button = findViewById(R.id.SettingButton);
        Link = findViewById(R.id.LinkAdd);
        Link.setText(SharedDataHolder.getLink(SettingActivity.this));
        setTextWatcher(Link);

        Ip = findViewById(R.id.IpAdd);
        Ip.setText(SharedDataHolder.getIp(SettingActivity.this));
        setTextWatcher(Ip);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedDataHolder.saveLink(SettingActivity.this,Link.getText().toString());
                SharedDataHolder.saveIP(SettingActivity.this,Ip.getText().toString());
                HomeActivity.isIpChanged = true;
                finish();
            }
        });


    }

    void setTextWatcher(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                makeButtonClickable();
            }
        });
    }

    void makeButtonClickable(){
        button.setClickable(true);
        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }
}
