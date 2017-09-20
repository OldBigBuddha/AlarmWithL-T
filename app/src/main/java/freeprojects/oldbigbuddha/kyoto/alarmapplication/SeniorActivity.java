package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senior);

        mEditor = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE ).edit();


        Bundle bundle = getIntent().getExtras();
        ArrayList<String> answers, questions;

        questions = bundle.getStringArrayList( getString(R.string.key_questions) );
        if (bundle.getStringArrayList( getString(R.string.key_answers) ) != null) {
            answers = bundle.getStringArrayList( getString(R.string.key_answers) );
        } else {
            answers = new ArrayList<>();
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
//                if (isFirst) {
//                    mEditor.putBoolean( getString(R.string.key_is_senior_mode), true );
//                }
                Toast.makeText( getApplicationContext(), "未実装", Toast.LENGTH_SHORT );
                Log.d("YesNoFragment", "Yes");

                startActivity(new Intent(SeniorActivity.this, MainSeniorActivity.class));
            }

            @Override
            public void onSelectedNo() {
                Log.d("YesNoFragment", "No");
                mEditor.putBoolean( getString(R.string.key_is_senior_mode), false );
                mEditor.commit();

                Toast.makeText( getApplicationContext(), "未実装", Toast.LENGTH_SHORT );

                startActivity(new Intent(SeniorActivity.this, MainActivity.class));
            }
        });

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.senior_fragment, fragment);
        transaction.commit();


    }
}
