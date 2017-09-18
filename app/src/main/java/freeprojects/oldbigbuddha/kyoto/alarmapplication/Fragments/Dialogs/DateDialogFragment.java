package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import java.util.Calendar;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentDialogDateBinding;

/**
 * Created by developer on 17/08/02.
 */

public class DateDialogFragment extends DialogFragment {

    public interface OnDataDialogFragmentListener {
        void onClickPositive(int year, int month, int day);
    }

    private OnDataDialogFragmentListener mListener;

    private Calendar today;

    private FragmentDialogDateBinding mBinding;




    public void setOnDataFragmentListener(OnDataDialogFragmentListener mListener) {
        this.mListener = mListener;
    }

    public DateDialogFragment() {
        today = Calendar.getInstance();
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

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_dialog_date, null, false);

        builder.setView(mBinding.getRoot());

        initYearNumPicker();
        initMonthNumPicker();
        initDayNumPicker();
        settingNowNumPicker();

        builder.setMessage( getString(R.string.title_date_dialog))
        .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onClickPositive(mBinding.npYear.getValue(), mBinding.npMonth.getValue() - 1, mBinding.npDay.getValue());
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
        mBinding.npMonth.setValue( today.get(Calendar.MONTH) + 1 );
        mBinding.npDay.setValue( today.get(Calendar.DAY_OF_MONTH));
    }

    public void initYearNumPicker() {
        mBinding.npYear.setMinValue(today.get(Calendar.YEAR));
        mBinding.npYear.setMaxValue(today.get(Calendar.YEAR) + 20);
//        List<String> years = new ArrayList<>();
//        years.add( getString(R.string.every) );
//        for (int i = today.getYear(); i < (today.getYear() + 10 ); i++) {
//            years.add(i + "");
//        }
//        mYear.setDisplayedValues( years.toArray(new String[years.size()]) );
    }

    public void initMonthNumPicker() {
        mBinding.npMonth.setMinValue(1);
        mBinding.npMonth.setMaxValue(12);
//        List<String> months = new ArrayList<>();
//        months.add( getString(R.string.every) );
//        for (int i = 1; i < 13; i++) {
//            months.add(i + "");
//        }
//        mYear.setDisplayedValues( months.toArray( new String[months.size()] ) );
    }

    public void initDayNumPicker() {

        mBinding.npDay.setMinValue(1);
        mBinding.npDay.setMaxValue(31);
        //TODO 月による日の上限チャック

//        List<String> days = new ArrayList<>();
//        days.add( getString(R.string.every) );
//        for (int i = 1; i < 32; i++) {
//            days.add(i + "");
//        }
//        mDay.setDisplayedValues( days.toArray( new String[days.size()] ) );
    }
}
