package com.example.romain.home;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.romain.home.model.Requests;
import com.example.romain.home.views.ActionsFragment;
import com.example.romain.home.views.ItemFragment;
import com.example.romain.home.views.items.ActionsItem;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ItemFragment.OnFragmentInteractionListener, RequestReciever, ActionsFragment.OnActionInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Fragment current_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        switch(position) {
            case 0:
                getRequest request = new getRequest(this);
                request.execute(Requests.SUMMARY);
                break;
            case 1:
                current_fragment = new MinerFragment();
                current_fragment = MinerFragment.newInstance();
                break;
            case 2:
                current_fragment = new NetworkFragment();
                current_fragment = NetworkFragment.newInstance();
                break;
            case 3:
                getRequest request1 = new getRequest(this);
                request1.execute(Requests.LIST_ACTIONS);
//                current_fragment = new ActionFragment();
//                current_fragment = ActionFragment.newInstance();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = "NetWork";
                break;
            case 4:
                mTitle = "Actions";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.refresh) {
//            current_fragment.refresh();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponce(JSONObject responce, Requests request) {
        FragmentManager fragmentManager = getFragmentManager();

        switch (request){
            case SUMMARY:
                try {
                    current_fragment = ItemFragment.newInstance(responce.getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case LIST_ACTIONS:
                try {
                    current_fragment = ActionsFragment.newInstance(responce.getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, current_fragment)
                .commit();

    }

    @Override
    public Activity getAct() {
        return this;
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onActionInteraction(String id) {
        getRequest r = new getRequest(this);
        r.setArgs(id);
        r.execute(Requests.SEND_ACTION);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
