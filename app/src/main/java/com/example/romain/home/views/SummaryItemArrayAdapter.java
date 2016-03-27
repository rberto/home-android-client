package com.example.romain.home.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.romain.home.R;
import com.example.romain.home.views.items.SummaryItem;

import java.util.List;

/**
 * Created by romain on 09/07/15.
 */
public class SummaryItemArrayAdapter extends ArrayAdapter<SummaryItem>{

    public SummaryItemArrayAdapter(Context context, List<SummaryItem> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.summaryitem, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        TextView unitView = (TextView) rowView.findViewById(R.id.unit);
        TextView avgValueView = (TextView) rowView.findViewById(R.id.avgvalue);
        TextView avgUnitView = (TextView) rowView.findViewById(R.id.avgunit);

        SummaryItem item = getItem(position);

        if (item.getTitle() != null){
            titleView.setText(item.getTitle());
        }else {
            titleView.setVisibility(View.INVISIBLE);
        }
        if (item.getValue() != null){
            valueView.setText(item.getValue());
        }else {
            valueView.setVisibility(View.INVISIBLE);
            unitView.setVisibility(View.INVISIBLE);
        }
        if (item.getUnit() != null){
            unitView.setText(item.getUnit());
            avgUnitView.setText(item.getUnit());
        }else {
            unitView.setText("");
            avgUnitView.setText("");
        }
        if (item.getAvgValue() != null){
            avgValueView.setText(item.getAvgValue());
        }else{
            avgValueView.setVisibility(View.INVISIBLE);
            avgUnitView.setVisibility(View.INVISIBLE);
        }
        return rowView;
    }
}
