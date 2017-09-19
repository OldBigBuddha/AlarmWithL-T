package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.SeniorActivity;

/**
 * Created by developer on 8/10/17.
 */

public class ConfigurationFragment extends PreferenceFragment {

    private SharedPreferences mConfig;
    private SharedPreferences.Editor mEditor;

    private boolean isNightMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);
        mConfig = getActivity().getSharedPreferences( getString(R.string.key_config), Context.MODE_PRIVATE );
        isNightMode = mConfig.getBoolean( getString(R.string.key_is_night_mode), false );
        mEditor = mConfig.edit();

        findPreference( getString(R.string.key_is_night_mode) )
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        boolean isChecked = (boolean)o;
                        mEditor.putBoolean( getString(R.string.key_is_night_mode), isChecked );
                        mEditor.commit();

                        getActivity().finish();
                        getActivity().startActivity(new Intent(getActivity(), getActivity().getClass()));
                        return true;
                    }
                });

        findPreference( getString(R.string.key_is_senior_mode) )
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        boolean isChecked = (boolean)o;
                        mEditor.putBoolean( getString(R.string.key_is_senior_mode), isChecked );
                        mEditor.commit();
                        Log.d("OnChangeSenior", "isChecked = " + isChecked);
                        goSenior();

                        return true;
                    }
                });

        findPreference("licenses")
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        return true;
                    }
                });
    }

    private void goSenior() {
        ArrayList<String> questions = new ArrayList<>();
        questions.add("お年寄りモードを使いますか？");

        Intent intent = new Intent( getActivity(), SeniorActivity.class );
        Bundle args   = new Bundle();
        args.putStringArrayList(getString(R.string.key_questions), questions);
        intent.putExtras(args);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
