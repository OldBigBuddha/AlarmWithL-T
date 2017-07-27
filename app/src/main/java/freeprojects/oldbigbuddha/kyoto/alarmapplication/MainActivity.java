package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainBinding;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        binding.setViewModel( new MainViewModel() );
        binding.setTitle( getString( R.string.title_home ) );
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
