package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;

/**
 * Created by developer on 17/08/02.
 */

public class DateDialogFragment extends DialogFragment {

    public interface OnDataFragmentListener {
        void onClickPositive(int year, int month, int day);
    }

    private OnDataFragmentListener mListener;

    private NumberPicker mYear;
    private NumberPicker mMonth;
    private NumberPicker mDay;

    private Date today;



    public void setOnDataFragmentListener(OnDataFragmentListener mListener) {
        this.mListener = mListener;
    }

    public DateDialogFragment() {
        today = new Date( System.currentTimeMillis() );
    }

    public static DateDialogFragment newInstance() {
        return new DateDialogFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.fragment_dialog_date, null);

        builder.setView(view);
        mYear  = (NumberPicker)view.findViewById(R.id.np_year);
        mMonth = (NumberPicker)view.findViewById(R.id.np_month);
        mDay   = (NumberPicker)view.findViewById(R.id.np_day);

        initYearNumPicker();
        initMonthNumPicker();
        initDayNumPicker();
        settingNowNumPicker();

        builder.setMessage( getString(R.string.title_date_dialog))
        .setPositiveButton(getString(R.string.dialog_date_set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year   = mYear.getValue();
                int month  = mMonth.getValue();
                int day    = mDay.getValue();

                mListener.onClickPositive(year, month, day);

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

    public void settingNowNumPicker() {
        mYear.setValue( today.getYear() + 1 );
        mMonth.setValue( today.getMonth() + 1 );
        mDay.setValue( today.getDate() + 1 );
    }

    public void initYearNumPicker() {
        List<String> years = new ArrayList<>();
        years.add( getString(R.string.every) );
        for (int i = today.getYear(); i < (today.getYear() + 10 ); i++) {
            years.add(i + "");
        }
        mYear.setDisplayedValues( years.toArray(new String[years.size()]) );
    }

    public void initMonthNumPicker() {
        List<String> months = new ArrayList<>();
        months.add( getString(R.string.every) );
        for (int i = 1; i < 13; i++) {
            months.add(i + "");
        }
        mYear.setDisplayedValues( months.toArray( new String[months.size()] ) );
    }

    public void initDayNumPicker() {
        List<String> days = new ArrayList<>();
        days.add( getString(R.string.every) );
        for (int i = 1; i < 32; i++) {
            days.add(i + "");
        }
        mDay.setDisplayedValues( days.toArray( new String[days.size()] ) );
    }
}
