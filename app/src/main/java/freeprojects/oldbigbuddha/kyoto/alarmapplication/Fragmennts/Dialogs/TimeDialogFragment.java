package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.Calendar;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class TimeDialogFragment extends DialogFragment{

    public interface OnTimeDialogFragmentListener{
        void onClickPositive(int hour, int minute);
    }

    private OnTimeDialogFragmentListener mListener;

    public void setOnTimeDialogFragmentListener(OnTimeDialogFragmentListener mListener) {
        this.mListener = mListener;
    }

    private NumberPicker mHour;
    private NumberPicker mMinute;

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

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.fragment_dialog_time, null);

        builder.setView(view);
        mHour = (NumberPicker)view.findViewById(R.id.np_hour);
        mMinute = (NumberPicker)view.findViewById(R.id.np_minute);

        initHour();
        initMinute();
        settingNowNumPicker();

        builder.setMessage(R.string.title_time_dialog)
        .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onClickPositive(mHour.getValue(), mMinute.getValue());
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
        mHour.setMinValue(0);
        mHour.setMaxValue(23);
    }

    public void initMinute() {
        mMinute.setMinValue(0);
        mMinute.setMaxValue(59);
    }

    public void settingNowNumPicker() {
        mHour.setValue( today.get(Calendar.HOUR_OF_DAY) );
        mMinute.setValue( today.get(Calendar.MINUTE) );
    }

}
