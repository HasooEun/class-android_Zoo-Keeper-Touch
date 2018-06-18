package com.example.hasoo.zookeepertouch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {

    private ArrayList<ScoreRecords> arrayList;
    private Context context;

    ScoreAdapter(ArrayList<ScoreRecords> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = View.inflate(context, R.layout.listview_score, null);

        TextView tvRank = convertView.findViewById(R.id.tvRank);
        TextView tvScore= convertView.findViewById(R.id.tvScore);
        TextView tvName = convertView.findViewById(R.id.tvName);

        ScoreRecords record = arrayList.get(position);
        tvRank.setText(String.valueOf(position+1));
        tvScore.setText(record.getScore());
        tvName.setText(record.getUserName());

        return convertView;
    }
}
