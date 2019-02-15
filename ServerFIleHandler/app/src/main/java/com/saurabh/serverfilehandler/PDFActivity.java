package com.saurabh.serverfilehandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFActivity extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfView = findViewById(R.id.PDFView);
        Log.v("TAG","d "+getIntent().getExtras().getString("LINK"));
        pdfView.fromAsset(getIntent().getExtras().getString("LINK")).load();
    }
}
