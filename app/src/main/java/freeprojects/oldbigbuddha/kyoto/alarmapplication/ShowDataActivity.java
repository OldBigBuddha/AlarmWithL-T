package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityShowDataBinding;

public class ShowDataActivity extends AppCompatActivity {

    private  ActivityShowDataBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);


    }
}
