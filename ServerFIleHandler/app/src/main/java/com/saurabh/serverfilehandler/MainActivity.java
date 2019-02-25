package com.saurabh.serverfilehandler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SqlDatabaseHandler sqlDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlDatabaseHandler = new SqlDatabaseHandler(MainActivity.this);

        if (sqlDatabaseHandler.getIpCount()>0){
            //todo: check is valid;
            if (true){
                startActivity(new Intent(MainActivity.this , HomeActivity.class));
                finish();
            }
        }else {
            Button ConnectButton = findViewById(R.id.ConnectMain);
            final EditText editText = findViewById(R.id.LinkEditMain);

            ConnectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sqlDatabaseHandler.SaveData("Name",editText.getText().toString());
                    startActivity(new Intent(MainActivity.this , HomeActivity.class));
                }
            });

        }

    }
}
