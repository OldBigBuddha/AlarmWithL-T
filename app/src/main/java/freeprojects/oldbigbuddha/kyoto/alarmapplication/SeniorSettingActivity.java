package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.YesNoFragment;
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
        FragmentManager     manager     = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        YesNoFragment fragment = YesNoFragment.getInstance();
        fragment.setQuestions(new String[]{"お年寄りモードを使いますか？"});
        fragment.setOnClickButtonListener(new YesNoFragment.OnClickButtonListener() {
            @Override
            public void onClickYes(View view) {
                Log.d("Yes Button", "OnClicked");
            }
            @Override
            public void onClickNo(View view) {
                startActivity(new Intent(SeniorSettingActivity.this, MainActivity.class));
            }
        });
        transaction.add(R.id.senior_fragment, fragment);
        transaction.commit();

    }
}
