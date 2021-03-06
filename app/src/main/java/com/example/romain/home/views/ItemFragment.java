package com.example.romain.home.views;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.romain.home.R;

import com.example.romain.home.RequestReciever;
import com.example.romain.home.model.Requests;
import com.example.romain.home.model.Summary;
import com.example.romain.home.model.factories.DataFactory;
import com.example.romain.home.model.factories.ItemsFactory;
import com.example.romain.home.views.dummy.DummyContent;
import com.example.romain.home.views.items.SummaryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.romain.home.model.factories.DataFactory.builItems;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener, RequestReciever<JSONObject> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUMMARY = "summary";

    // TODO: Rename and change types of parameters
    private JSONArray summary;
    private String mParam2;

    private ArrayList<SummaryItem> items;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(/*JSONArray summary*/) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        //args.putSerializable(ARG_SUMMARY, summary);
        //fragment.summary = summary;
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelableArray(ARG_SUMMARY) != null) {
            items = new ArrayList<SummaryItem>();
            for (Parcelable item : getArguments().getParcelableArray(ARG_SUMMARY)){
                items.add((SummaryItem)item);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item3, container, false);
        if (view.findViewById(R.id.progressBar) != null) {
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (items != null){
            updateList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        SummaryItem item = (SummaryItem) parent.getItemAtPosition(position);
        SummaryItem item = (SummaryItem) mAdapter.getItem(position);
//         mListView.getItemAtPosition(position);
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(item.getKey());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    public void updateList(){
        if (getActivity() != null && getActivity().findViewById(R.id.progressBar) != null){
            getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }

        this.getArguments().putParcelableArray(ARG_SUMMARY, items.toArray(new Parcelable[items.size()]));
        mAdapter = new SummaryItemArrayAdapter(getActivity(), items);
        mListView = (AbsListView) getActivity().findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onResponce(JSONObject resonce, Requests request) {

        if (getActivity() != null && getActivity().findViewById(R.id.progressBar) != null) {
            getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }

        try {
            summary = resonce.getJSONArray("data");
            items = (ArrayList<SummaryItem>) builItems(summary);
            updateList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //fragment.summary = summary;

    }

    @Override
    public Activity getAct() {
        return getActivity();
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
        public void onFragmentInteraction(String id);
    }

}
