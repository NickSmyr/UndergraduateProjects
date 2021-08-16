package com.example.quickrepair.view.SearchTechnicians;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.quickrepair.R;

import java.util.ArrayList;
import java.util.List;

public class PlainTextAdapter extends BaseAdapter {

    List<Integer> ids;
    List<String> names;
    Context context;
    public PlainTextAdapter(Context context){
        ids = new ArrayList<>();
        names = new ArrayList<>();
        this.context = context;
    }
    public void setSource(List<Integer> ids , List<String> names){
        this.ids = ids;
        this.names = names;
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.e("s" , "Returing id" + position);
        return ids.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View result = inflater.inflate( R.layout.search_technicians_text_list_item, parent , false);
        TextView text = (TextView) result.findViewById(R.id.text);
        text.setText(names.get(position));
        return  result;
    }
}
