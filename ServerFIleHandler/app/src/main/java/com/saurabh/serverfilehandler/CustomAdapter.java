package com.saurabh.serverfilehandler;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public abstract class CustomAdapter  extends BaseAdapter {
    ArrayList folderList;
    LayoutInflater layoutInflater;
    Context context;
    CustomAdapter(ArrayList arrayList, LayoutInflater layoutInflat,Context cntx){
        folderList = arrayList;
        layoutInflater = layoutInflat;
        context = cntx;
    }
    @Override
    public int getCount() {
        return folderList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listeach ,null);
        ImageView folderImage = view.findViewById(R.id.folderIcon);
        TextView folderTitle = view.findViewById(R.id.EachTitle);
        folderTitle.setText(folderList.get(position).toString());
        if (!folderList.get(position).toString().contains(".")){
            folderImage.setImageDrawable(context.getDrawable(R.drawable.folder));
        }else {
            if (folderList.get(position).toString().endsWith("php")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.php));
            }else if (folderList.get(position).toString().endsWith("dll")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.dll));
            }else if (folderList.get(position).toString().endsWith("class")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.java));
            }else if (folderList.get(position).toString().endsWith("java")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.java));
            }else if (folderList.get(position).toString().endsWith("mp3")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.mp3));
            }else if (folderList.get(position).toString().endsWith("mp4")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.mp4));
            }else if (folderList.get(position).toString().endsWith("png")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.img));
            }else if (folderList.get(position).toString().endsWith("jpg")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.img));
            }else if (folderList.get(position).toString().endsWith("ico")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.img));
            }else if (folderList.get(position).toString().endsWith("pdf")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.pdf));
            }else if (folderList.get(position).toString().endsWith("txt")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.txt));
            }
            // Windows:
            else if (folderList.get(position).toString().endsWith("xlsx")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.win));
            }else if (folderList.get(position).toString().endsWith("pptx")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.win));
            }
            // Windows End
            else if (folderList.get(position).toString().endsWith("html")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.html));
            }else if (folderList.get(position).toString().endsWith("css")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.css));
            }else if (folderList.get(position).toString().endsWith("js")){
                folderImage.setImageDrawable(context.getDrawable(R.drawable.js));
            }else {
                folderImage.setImageDrawable(context.getDrawable(R.drawable.sys));
            }
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClick(folderList.get(position).toString());
            }
        });
        return view;
    }

    abstract void OnClick(String data);
}
