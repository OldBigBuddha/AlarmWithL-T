package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mConfig;

    private static final String[] QUESTIONS = new String[] {
            "お年寄りモードを使いますか？"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE );
        SharedPreferences.Editor editor = mConfig.edit();

        if (!(mConfig.getBoolean( getString(R.string.key_is_senior_mode), false ))) {
            editor.putBoolean( getString(R.string.key_is_senior_mode), true );
            editor.commit();

            Intent intent = new Intent( this, SeniorActivity.class );
            Bundle args   = new Bundle();
            args.putStringArray(getString(R.string.key_questions), QUESTIONS);
            args.putBoolean( getString(R.string.key_is_first), true );
            intent.putExtras(args);
            startActivity(new Intent(this, SeniorActivity.class));
        }

        // Check Night Mode
        boolean isNightMode = mConfig.getBoolean( getString(R.string.key_is_night_mode), false );
        if ( isNightMode ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }

        ActivityMainBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        binding.setViewModel( new MainViewModel() );
        binding.setTitle( getString( R.string.title_home ) );
        setSupportActionBar(binding.toolbarMain);

        if (isNightMode) {
            binding.btNew.setBackground( getDrawable(R.drawable.shape_rouded_corners_30dp_dark) );
            binding.btEdit.setBackground( getDrawable(R.drawable.shape_rouded_corners_30dp_dark) );
        } else {
            binding.btNew.setBackground( getDrawable(R.drawable.shape_rouded_corners_30dp_light) );
            binding.btEdit.setBackground( getDrawable(R.drawable.shape_rouded_corners_30dp_light) );
        }

        if (!getIntent().getBooleanExtra("isPermit", true)) {
            Snackbar.make( binding.linearMain, getString( R.string.no_permission ), Snackbar.LENGTH_SHORT ).show();
        }
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
