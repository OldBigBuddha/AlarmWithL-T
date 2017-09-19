package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mConfig;

    private ArrayList<String> mQuestions;
    
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE );
        SharedPreferences.Editor editor = mConfig.edit();

        if (!(mConfig.getBoolean( getString(R.string.key_is_senior_mode), false ))) {
            editor.putBoolean( getString(R.string.key_is_senior_mode), true );
            editor.commit();

            goSenior();
        }

        // Check Night Mode
        boolean isNightMode = mConfig.getBoolean( getString(R.string.key_is_night_mode), false );
        if ( isNightMode ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        mBinding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        mBinding.setViewModel( new MainViewModel() );
        mBinding.setTitle( getString( R.string.title_home ) );
        setSupportActionBar(mBinding.toolbarMain);

        if (isNightMode) {
            setButtonTheme( getDrawable(R.drawable.shape_rouded_corners_30dp_dark) );
            setButtonTheme( getDrawable(R.drawable.shape_rouded_corners_30dp_dark) );
        } else {
            setButtonTheme( getDrawable(R.drawable.shape_rouded_corners_30dp_light) );
            setButtonTheme( getDrawable(R.drawable.shape_rouded_corners_30dp_light) );
        }

        if (!getIntent().getBooleanExtra("isPermit", true)) {
            Snackbar.make( mBinding.linearMain, getString( R.string.no_permission ), Snackbar.LENGTH_SHORT ).show();
        }
    }

    private void setButtonTheme(Drawable theme) {
        mBinding.btNew.setBackground(theme);
        mBinding.btEdit.setBackground(theme);
    }

    private void goSenior() {
        mQuestions = new ArrayList<>();
        mQuestions.add("お年寄りモードを使いますか？");

        Intent intent = new Intent( this, SeniorActivity.class );
        Bundle args   = new Bundle();
        args.putStringArrayList(getString(R.string.key_questions), mQuestions);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting_menu) {
            startActivity( new Intent(this, SettingActivity.class) );
        }
        return true;
    }

    @BindingAdapter("htmlText")
    public static void setText(TextView view, String text) {
        view.setText( fromhtml( text ) );
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromhtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
