package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Senior.YesNoFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivitySeniorBinding;

public class SeniorActivity extends AppCompatActivity {

    private ActivitySeniorBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senior);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<String>
                answers   = null,
                questions;

        questions = bundle.getStringArrayList( getString(R.string.key_questions) );

        if (bundle.getStringArray( getString(R.string.key_answers) ) != null) {
            answers = bundle.getStringArrayList( getString(R.string.key_answers) );
        } else {
            answers.add("はい");
            answers.add("いいえ");
        }

        YesNoFragment fragment = new YesNoFragment();
        final Bundle args = new Bundle();
        args.putStringArrayList( getString(R.string.key_answers), answers);
        args.putStringArrayList( getString(R.string.key_questions), questions);
        fragment.setArguments(args);
        fragment.setOnSelectedAnswerListener(new YesNoFragment.OnSelectedAnswerListener() {
            @Override
            public void onSelectedYes(boolean isFirst) {
                if (isFirst) {
                    startActivity(new Intent(SeniorActivity.this, MainSeniorActivity.class));
                }
                Log.d("YesNoFragment", "Yes");
            }

            @Override
            public void onSelectedNo() {
                Log.d("YesNoFragment", "No");
                startActivity(new Intent(SeniorActivity.this, MainActivity.class));
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.senior_fragment, fragment);
        transaction.commit();


    }
}
