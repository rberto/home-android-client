package com.example.romain.home;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.romain.home.chart.ChartUtils;
import com.example.romain.home.model.Requests;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends Fragment implements RequestReciever<JSONObject>, OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private JSONArray mParam1;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(/*JSONArray param1*/) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
//        fragment.mParam1 = param1;
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
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
        LineChart chart = (LineChart) getActivity().findViewById(R.id.chart);
        chart.setData(new LineData(new ArrayList<String>()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResponce(JSONObject resonce, Requests request) {

        getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        try {
            mParam1 = resonce.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LineData data = ChartUtils.getLineData(mParam1, "title", Color.BLUE);
        LineChart chart = (LineChart) getActivity().findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(0);
        chart.setOnChartValueSelectedListener(this);

    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void onValueSelected(Entry entry, int i) {
/*
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
*/
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
