package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;

public class NewCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);
//        ActivityNewCreateBinding binding = DataBindingUtil.setContentView( this, R.layout.activity_new_create );
//        setSupportActionBar( binding.toolBar );
    }
}
