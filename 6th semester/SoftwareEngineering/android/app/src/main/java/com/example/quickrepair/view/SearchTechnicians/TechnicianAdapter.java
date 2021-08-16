package com.example.quickrepair.view.SearchTechnicians;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.quickrepair.R;

import java.util.ArrayList;
import java.util.List;

public class TechnicianAdapter extends ArrayAdapter<Object> {

    List<Integer> technicianIds;
    List<String> technicianNames;
    List<Double> averageRatings;
    List<Double> prices;
    Context context;
    public TechnicianAdapter(Context context, int adapterRes){
        super(context , adapterRes);
        this.context = context;
        technicianIds = new ArrayList<>();
        technicianNames = new ArrayList<>();
        averageRatings = new ArrayList<>();
        prices = new ArrayList<>();
    }
    public void setSource(List<Integer> technicianIds, List<String> technicianNames, List<Double> averageRatings, List<Double> prices){
        this.technicianIds = technicianIds;
        this.technicianNames = technicianNames;
        this.averageRatings = averageRatings;
        this.prices = prices;
    }

    @Override
    public int getCount() {
        return technicianIds.size();
    }

    @Override
    public Object getItem(int position) {
        return technicianNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return technicianIds.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(technicianIds.size() == 0) return null;
        System.out.println("Get view at pos" + position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        LinearLayout result = (LinearLayout)inflater.inflate( R.layout.search_technicians_technician_list_layout, parent , false);
        TextView numberText =  result.findViewById(R.id.number);
        TextView nameText =  result.findViewById(R.id.name);
        TextView priceText =  result.findViewById(R.id.price);
        TextView averageRatingText =  result.findViewById(R.id.average_rating);

        numberText.setText(String.valueOf(position  + 1 ));
        nameText.setText(technicianNames.get(position));
        priceText.setText("" +prices.get(position));
        averageRatingText.setText("" +averageRatings.get(position));

        return  result;
    }
}
