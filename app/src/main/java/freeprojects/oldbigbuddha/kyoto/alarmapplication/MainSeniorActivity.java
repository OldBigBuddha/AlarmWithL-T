package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainSeniorBinding;

public class MainSeniorActivity extends AppCompatActivity {

    private ActivityMainSeniorBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_senior);
        mBinding.btStartSenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "スタート", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
