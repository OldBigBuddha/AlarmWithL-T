package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Senior.YesNoFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivitySeniorBinding;

public class SeniorActivity extends AppCompatActivity {

    private ActivitySeniorBinding mBinding;

    private String[] mQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senior);
        Bundle bundle = getIntent().getExtras();
        mQuestions = bundle.getStringArray( getString(R.string.key_questions) );
        String[] answers = null;
        if (bundle.getStringArray( getString(R.string.key_answers) ) != null) {
            answers = bundle.getStringArray( getString(R.string.key_answers) );
        }

        YesNoFragment fragment = new YesNoFragment();
        final Bundle args = new Bundle();
        args.putStringArray( getString(R.string.key_answers), answers);
        args.putStringArray( getString(R.string.key_questions), mQuestions );
        fragment.setArguments(args);
        fragment.setOnSelectedAnswerListener(new YesNoFragment.OnSelectedAnswerListener() {
            @Override
            public void onSelectedYes(boolean isFirst) {
                if (isFirst) {
                    startActivity(new Intent(SeniorActivity.this, MainSeniorActivity.class));
                }
                Toast.makeText(SeniorActivity.this, "Select Yes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectedNo() {
                startActivity(new Intent(SeniorActivity.this, MainActivity.class));
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.senior_fragment, fragment);
        transaction.commit();


    }
}
