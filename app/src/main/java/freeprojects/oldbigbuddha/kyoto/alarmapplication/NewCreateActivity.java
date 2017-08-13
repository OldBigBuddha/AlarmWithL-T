package freeprojects.oldbigbuddha.kyoto.alarmapplication;


import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.SettingFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;
import io.realm.Realm;

public class NewCreateActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ActivityNewCreateBinding mBinding;
    private SettingFragment mFragment;

    private Realm mRealm;

    private SharedPreferences mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE );
        boolean isNightMode = mConfig.getBoolean( getString(R.string.key_is_night_mode), false );
        if ( isNightMode ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_create);  //Binding Layout
        mFragment = (SettingFragment)getSupportFragmentManager().findFragmentById(R.id.setting_fragment);

        initToolbar();
        mRealm = Realm.getDefaultInstance(); // Initialize Realm
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Selected create Icon
            case R.id.create_menu: {
                Log.d(TAG, "Selected Create");
                // Get AlarmData from SettingFragment
                AlarmRealmData data = mFragment.getAlarmData();

                // Save AlarmData
                mRealm.beginTransaction();
                mRealm.copyToRealm(data);
                mRealm.commitTransaction();

                // Back home
                onBackPressed();
                break;
            }
            default: {
                Log.d(TAG, "Selected Other Item");
                // Back home
                onBackPressed();
            }
        }
        return true;
    }

    // Initialize ToolBar
    private void initToolbar() {
        Toolbar toolBar = mBinding.toolbarCreate;
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}