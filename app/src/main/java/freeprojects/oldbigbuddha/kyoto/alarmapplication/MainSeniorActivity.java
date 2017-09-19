package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityMainSeniorBinding;

public class MainSeniorActivity extends AppCompatActivity {

    private ActivityMainSeniorBinding mBinding;

    private static final String[] QUESTIONS = new String[] {
            "何で教えてほしいですか？"
    };
    private static final String[] ANSWERS   = new String[] {
            "場所",
            "時間",
            "何で教えてほしいですか？",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_senior);
        mBinding.btStartSenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "スタート", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainSeniorActivity.this, SeniorActivity.class);
                Bundle args = new Bundle();
//                args.putStringArray( "answer", ANSWERS);
                args.putStringArray( getString(R.string.key_questions), QUESTIONS);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
    }
}
