package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding mBinding;

    private SharedPreferences mConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE );

        if ( mConfig.getBoolean( getString(R.string.key_is_night_mode), false ) ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        setSupportActionBar(mBinding.toolbarConfig);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent(SettingActivity.this, MainActivity.class) );
    }

}
