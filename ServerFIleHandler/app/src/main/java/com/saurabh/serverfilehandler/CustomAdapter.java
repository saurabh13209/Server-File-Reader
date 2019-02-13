package com.saurabh.serverfilehandler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listeach ,null);
        TextView folderTitle = view.findViewById(R.id.EachTitle);
        folderTitle.setText(folderList.get(position).toString());
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
