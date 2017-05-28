package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.DataBindingUtil;
import android.databinding.adapters.TextViewBindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        TextView textView = (TextView)findViewById(R.id.tvHome);
        textView.setText(Html.fromHtml( getString(R.string.title_home) ) );
    }
}
