package com.example.romain.home;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetworkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkFragment extends MyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mMachinesText;
    private ProgressBar mProgress;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NetworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkFragment newInstance() {
        NetworkFragment fragment = new NetworkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    public void onResume(){
        super.onResume();
        mProgress = (ProgressBar) getActivity().findViewById(R.id.progressBar2);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LinearLayout lin = (LinearLayout) getActivity().findViewById(R.id.machinelist);
        if (lin != null) {
            lin.removeAllViews();
        }
    }


    public void refresh(){
        super.refresh();
        mProgress.setVisibility(View.VISIBLE);
        getRequest request = new getRequest(this);
        asyncTaskWeakRef = new WeakReference<getRequest>(request);
        request.execute("http://" + ip + ":8889/api?user=romain&password=azerty&datatype=network");
    }

    public void updateUI(JSONObject json) throws JSONException {
        mProgress.setVisibility(View.GONE);
        LinearLayout lin = (LinearLayout) getActivity().findViewById(R.id.machinelist);
        JSONArray array = json.getJSONArray("network");
        for (int i =0; i<array.length(); i++){
            String name = array.getJSONObject(i).getString("name");
            String ip = array.getJSONObject(i).getString("ip");
            TextView txt = new TextView(getActivity().getApplication());
            txt.setText(ip + ": " + name);
            txt.setBackgroundColor(Color.parseColor("#99CC00"));
            txt.setTextColor(Color.parseColor("#669900"));
            txt.setTextSize(15);
            txt.setPadding(5, 5, 5, 5);
            lin.addView(txt);
        }
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
