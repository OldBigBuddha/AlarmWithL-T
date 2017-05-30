package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        String text = getString( R.string.title_home );
        binding.tvHome.setText( fromhtml( text ) );
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
