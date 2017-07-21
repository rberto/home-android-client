package com.example.romain.home.views.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.romain.home.R;

/**
 * Created by rbertolini on 21/07/2017.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_general);
    }

}
