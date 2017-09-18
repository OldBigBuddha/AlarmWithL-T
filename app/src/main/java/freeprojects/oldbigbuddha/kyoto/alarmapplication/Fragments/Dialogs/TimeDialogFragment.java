package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.LayoutInflater;

import java.util.Calendar;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentDialogTimeBinding;

/**
 * Created by BigBuddha on 2017/08/02.
 */

public class TimeDialogFragment extends DialogFragment{

    public interface OnTimeDialogFragmentListener{
        void onClickPositive(int hour, int minute);
    }

    private OnTimeDialogFragmentListener mListener;

    public void setOnTimeDialogFragmentListener(OnTimeDialogFragmentListener mListener) {
        this.mListener = mListener;
    }

    private FragmentDialogTimeBinding mBinding;
    private Calendar today;


    public TimeDialogFragment() {
        today = Calendar.getInstance();
    }
    public static TimeDialogFragment newInstance() {
        return new TimeDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_dialog_time, null, false);

        builder.setView(mBinding.getRoot());

        initHour();
        initMinute();
        settingNowNumPicker();

        builder.setMessage(R.string.title_time_dialog)
        .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onClickPositive(mBinding.npHour.getValue(), mBinding.npMinute.getValue());
            }
        })
        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        });


        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    public void initHour() {
        mBinding.npHour.setMinValue(0);
        mBinding.npHour.setMaxValue(23);
    }

    public void initMinute() {
        mBinding.npMinute.setMinValue(0);
        mBinding.npMinute.setMaxValue(59);
    }

    public void settingNowNumPicker() {
        mBinding.npHour.setValue( today.get(Calendar.HOUR_OF_DAY) );
        mBinding.npMinute.setValue( today.get(Calendar.MINUTE) );
    }

}
