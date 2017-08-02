package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.DateDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityNewCreateBinding;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class NewCreateViewModel {

    public static final int REQUEST_CODE_SCHEDUEL = 121;

    private ActivityNewCreateBinding mBinding;
    private Activity mActivity;




    public NewCreateViewModel(ActivityNewCreateBinding binding, Activity activity) {
        mBinding = binding;
        mActivity = activity;
    }

    public void getCheckDate(CompoundButton button, boolean isChecked) {
        mBinding.switchDate.setChecked( isChecked );
        mBinding.expandDate.toggle();
        Log.d("Date", isChecked + "");
        Log.d("Location", mBinding.switchLocation.isChecked() + "");
    }

    public void getCheckLocation(CompoundButton button, boolean isChecked) {
        mBinding.switchLocation.setChecked( isChecked );
        mBinding.expandLocation.toggle();
        Log.d("Location", isChecked + "");
        Log.d("Date", mBinding.switchDate.isChecked() + "");
    }

    public void onAddSchedule(View view) {
        DateDialogFragment fragment = DateDialogFragment.newInstance();


    }

}
