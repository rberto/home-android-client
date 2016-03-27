package com.example.romain.home;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.romain.home.chart.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TempFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TempFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TempFragment extends MyFragment implements OnChartValueSelectedListener{

    private OnFragmentInteractionListener mListener;

    private TextView mInsideTempText;

    private TextView mOutideTempText;

    private TextView mInsidePressure;

    private TextView mOutsidePressure;

    private TextView mAverageOutTemp;

    private TextView mAverageInTemp;

    private TextView mSelinTemp;

    private TextView mSeloutTemp;

    private TextView mSelTime;

    private LineChart lcTempLineChart;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TempFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TempFragment newInstance() {
        TempFragment fragment = new TempFragment();
        return fragment;
    }

    public TempFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.newtemplayout, container, false);

    }

    public void refresh(){
        super.refresh();
        mInsideTempText.setText("--");
        mOutideTempText.setText("--");
        mOutsidePressure.setText("--");
        mInsidePressure.setText("--");
        lcTempLineChart.clear();
//        getRequest request = new getRequest(this);
//        asyncTaskWeakRef = new WeakReference<getRequest>(request);
//        request.execute("http://" + ip + ":8889/api?user=romain&password=azerty&datatype=weather");
    }

    public void updateUI(JSONObject json) throws JSONException {
        mInsideTempText.setText(json.getString("temp"));
        mOutideTempText.setText(json.getString("temp_ext"));
        mOutsidePressure.setText(json.getString("pressure_ext"));
        mInsidePressure.setText(json.getString("pressure"));
        JSONArray temp24json = json.getJSONArray("temp24");
        LineData data = ChartUtils.createLineData(temp24json, new String[]{"Inside Temp", "Outside Temp"}, new int[]{DKGRAY, GRAY});
        lcTempLineChart.setDrawHorizontalGrid(false);
        lcTempLineChart.setDrawVerticalGrid(false);
        lcTempLineChart.setDrawGridBackground(false);
        lcTempLineChart.setDescription("");
        lcTempLineChart.setStartAtZero(false);
        lcTempLineChart.setDrawLegend(false);
        lcTempLineChart.setDoubleTapToZoomEnabled(false);
        lcTempLineChart.setData(data);
        lcTempLineChart.setOnChartValueSelectedListener(this);
        lcTempLineChart.animateX(1500);

        // Updating the 24h average.
        Float averageintemp = data.getDataSetByIndex(0).getYValueSum() / data.getDataSetByIndex(0).getEntryCount();
        Float averageouttemp = data.getDataSetByIndex(1).getYValueSum() / data.getDataSetByIndex(1).getEntryCount();
        mAverageInTemp.setText(new DecimalFormat("##.##").format(averageintemp));
        mAverageOutTemp.setText(new DecimalFormat("##.##").format(averageouttemp));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mInsideTempText = (TextView) getActivity().findViewById(R.id.inside_temp2);
        mOutideTempText = (TextView) getActivity().findViewById(R.id.out_temp2);
        mInsidePressure = (TextView) getActivity().findViewById(R.id.inside_pressure2);
        mOutsidePressure = (TextView) getActivity().findViewById(R.id.out_pressure2);
        lcTempLineChart = (LineChart) getActivity().findViewById(R.id.tempchart);
        mSelinTemp = (TextView) getActivity().findViewById(R.id.selectedintemp);
        mSeloutTemp = (TextView) getActivity().findViewById(R.id.selectedextemp);
        mSelTime = (TextView) getActivity().findViewById(R.id.seltime);
        mAverageInTemp = (TextView) getActivity().findViewById(R.id.averageintemp);
        mAverageOutTemp = (TextView) getActivity().findViewById(R.id.averageouttemp);
        refresh();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mInsideTempText = null;
        mOutideTempText = null;
        mInsidePressure = null;
        mOutsidePressure = null;
        lcTempLineChart = null;
        mSelinTemp = null;
        mSeloutTemp = null;
        mSelTime = null;
        mAverageInTemp =null;
        mAverageOutTemp = null;
    }

    @Override
    public void onValueSelected(Entry entry, int i) {
        int index = entry.getXIndex();
        Float selintemp = null;
        Float selextemp = null;


        for (int j = 0; j < 2; j++) {
            DataSet<Entry> data = lcTempLineChart.getData().getDataSetByIndex(j);
            if (data.getLabel() == "Inside Temp") {
                selintemp = data.getYValForXIndex(index);
            }
            if (data.getLabel() == "Outside Temp") {
                selextemp = data.getYValForXIndex(index);
            }
        }
        // Could not use the following api because of a bug in getDataSetByLabel
        //Float selintemp = lcTempLineChart.getYValue(index, "Inside Temp");
        //Float selextemp = lcTempLineChart.getYValue(index, "Outside Temp");
        String seltime = lcTempLineChart.getXValue(index);
        mSelinTemp.setText(String.valueOf(selintemp));
        mSeloutTemp.setText(String.valueOf(selextemp));

        mSelTime.setText(seltime);
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
