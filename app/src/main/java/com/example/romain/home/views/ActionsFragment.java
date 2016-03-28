package com.example.romain.home.views;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
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
import com.example.romain.home.getRequest;
import com.example.romain.home.model.RequestSender;
import com.example.romain.home.model.Requests;
import com.example.romain.home.model.factories.DataFactory;
import com.example.romain.home.views.dummy.DummyContent;
import com.example.romain.home.views.items.ActionsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnActionInteractionListener}
 * interface.
 */
public class ActionsFragment extends Fragment implements AbsListView.OnItemClickListener, RequestReciever<JSONObject> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private JSONArray actions;
    private ArrayList<ActionsItem> actionsItems;

    private OnActionInteractionListener mListener;

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
    public static ActionsFragment newInstance(/*JSONArray actions*/) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, actions);
//        fragment.actions = actions;
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ActionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getParcelableArray(ARG_PARAM1) != null) {
            actionsItems = new ArrayList<ActionsItem>();
            for (Parcelable item : getArguments().getParcelableArray(ARG_PARAM1)){
                actionsItems.add((ActionsItem)item);
            }
        }

        // TODO: Change Adapter to display your content

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnActionInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (actionsItems != null){
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
        ActionsItem ac = (ActionsItem) mListView.getItemAtPosition(position);
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onActionInteraction(ac.getTitle());
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

        if (getActivity() != null && getActivity().findViewById(R.id.progressBar) != null) {
            getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }

        mAdapter = new ActionItemArrayAdapter(getActivity(), actionsItems);

        // Set the adapter
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
            actions = resonce.getJSONArray("data");
            actionsItems = (ArrayList<ActionsItem>) DataFactory.builActionsItems(actions);
            this.getArguments().putParcelableArray(ARG_PARAM1, actionsItems.toArray(new Parcelable[actionsItems.size()]));
            updateList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    public interface OnActionInteractionListener {
        // TODO: Update argument type and name
        public void onActionInteraction(String id);
    }

}
