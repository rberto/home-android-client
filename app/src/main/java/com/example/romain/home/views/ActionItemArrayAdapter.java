package com.example.romain.home.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.romain.home.R;
import com.example.romain.home.views.items.ActionsItem;

import java.util.List;

/**
 * Created by romain on 14/08/15.
 */
public class ActionItemArrayAdapter extends ArrayAdapter<ActionsItem> {

public ActionItemArrayAdapter(Context context, List<ActionsItem> objects) {
        super(context, -1, objects);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.actionitem, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.description);

        ActionsItem item = getItem(position);

        titleView.setText(item.getTitle());
        descriptionView.setText(item.getDescription());

        return rowView;
        }
}
