package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivitySeniorSettingBinding;

public class SeniorSettingActivity extends AppCompatActivity {

    private ActivitySeniorSettingBinding mBinding;

    private static final String[] questions = new String[]{
            "今日ですか？",
            "今週ですか？",
            "今月ですか？",
            "今年ですか？",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senior_setting);
    }
}
