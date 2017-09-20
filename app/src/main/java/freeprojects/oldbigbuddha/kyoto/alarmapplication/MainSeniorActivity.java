package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainSeniorBinding;

public class MainSeniorActivity extends AppCompatActivity {

    private ActivityMainSeniorBinding mBinding;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_senior);
        mBinding.btStartSenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( MainSeniorActivity.this, "未実装", Toast.LENGTH_SHORT );
//                ArrayList<String> questions = new ArrayList<>(),
//                                  answers   = new ArrayList<>();
//                questions.add("何を使ってお知らせしましょうか？");
//
//                answers.add("時間");
//                answers.add("場所");
//
//                Intent intent = new Intent(MainSeniorActivity.this, SeniorActivity.class);
//                Bundle args = new Bundle();
//
//                args.putStringArrayList( getString(R.string.key_questions), questions);
//                args.putStringArrayList( getString(R.string.key_answers)  , answers);
//                intent.putExtras(args);
//
//                startActivity(intent);
                SharedPreferences.Editor editor = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE ).edit();
                editor.putBoolean( getString(R.string.key_is_senior_mode), false );
                editor.commit();

                Intent intent = new Intent(MainSeniorActivity.this, MainActivity.class);
                intent.putExtra("none", true);
                startActivity(intent);
            }
        });
    }
}
