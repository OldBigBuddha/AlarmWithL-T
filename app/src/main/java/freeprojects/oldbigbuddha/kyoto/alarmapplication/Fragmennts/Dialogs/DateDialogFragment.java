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

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;

/**
 * Created by developer on 17/08/02.
 */

public class DateDialogFragment extends DialogFragment {

    public DateDialogFragment() {}

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
        View content = inflater.inflate(R.layout.fragment_dialog_date, null);
        builder.setView(content);

        builder.setTitle( getString(R.string.title_date_dialog))
        .setPositiveButton(getString(R.string.dialog_date_set), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        })
        .show();

        return builder.create();
    }
}
