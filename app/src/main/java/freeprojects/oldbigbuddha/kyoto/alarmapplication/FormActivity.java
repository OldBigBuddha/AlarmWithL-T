package freeprojects.oldbigbuddha.kyoto.alarmapplication;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import static android.support.design.R.color.background_material_dark;
import static android.support.design.R.color.primary_dark_material_dark;

public class FormActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private final String TAG = getClass().getSimpleName();
    private final int REQUEST_CODE_ACCESS_FINE_LOCATION = 1;

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

        checkPermission();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_create);  //Binding Layout
        Intent intent = getIntent();
        mFragment = (SettingFragment)getSupportFragmentManager().findFragmentById(R.id.setting_fragment);
        if ( intent.getStringExtra("data") != null ) {
            Bundle bundle = new Bundle();
            bundle.putString("data", intent.getStringExtra("data"));
            mFragment.setArguments(bundle);
        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( grantResults.length <= 0 ||
                requestCode != REQUEST_CODE_ACCESS_FINE_LOCATION ||
                grantResults[0]!= PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("isPermit", false);
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermission() {
        Log.d("onCreate", "checkPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_LOCATION);
            }
        }

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