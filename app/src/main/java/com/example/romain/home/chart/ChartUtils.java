package com.example.romain.home.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by romain on 22/03/15.
 */
public class ChartUtils {


    public static LineData createLineData(JSONArray array, String[] names, int[] colors) throws JSONException {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        // Get Number of elements per elt.
        int lines = array.getJSONArray(0).length();
        ArrayList[] linesdata = new ArrayList[lines-1];
        for (int i = 0; i < array.length(); i++) {
            String date = array.getJSONArray(i).getString(0);
            xVals.add(date);
            for (int j = 1; j < lines; j++) {
                if (linesdata[j-1] == null) {
                    linesdata[j-1] = new ArrayList<Entry>();
                }
                Entry en = new Entry(Float.valueOf(array.getJSONArray(i).getString(j)), i);
                linesdata[j-1].add(en);
            }
        }
        int p = 0;
        for (ArrayList data : linesdata) {
            LineDataSet set = new LineDataSet(data, names[p]);
            //setinteriors.enableDashedLine(1,0,0);
            set.setDrawCircles(false);
            set.setColor(colors[p]);
            set.setLineWidth(2);
            dataSets.add(set);
            p++;
        }
        return new LineData(xVals, dataSets);
    }
}
