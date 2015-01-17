package com.example.romain.home;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MinerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MinerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinerFragment extends MyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView mhashratetext;
    private TextView merrorratetext;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MinerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MinerFragment newInstance() {
        MinerFragment fragment = new MinerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MinerFragment() {
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
        return inflater.inflate(R.layout.fragment_miner, container, false);
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

    public void refresh(){
        super.refresh();
        mhashratetext.setText("--");
        merrorratetext.setText("--");
        getRequest request = new getRequest(this);
        this.asyncTaskWeakRef = new WeakReference<getRequest>(request);
        request.execute("http://" + ip + ":8889/api?user=romain&password=azerty&datatype=miner");
    }

    public void updateUI(JSONObject json) throws JSONException{
        JSONObject summary = json.getJSONArray("SUMMARY").getJSONObject(0);
        mhashratetext.setText(summary.getString("GHS av"));
        merrorratetext.setText(summary.getString("Device Hardware%"));
    }

    @Override
    public void onResume(){
        super.onResume();
        mhashratetext = (TextView) getActivity().findViewById(R.id.hashrate);
        merrorratetext = (TextView) getActivity().findViewById(R.id.errorrate);
        refresh();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mhashratetext = null;
        merrorratetext = null;
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
