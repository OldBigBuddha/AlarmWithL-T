package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;

/**
 * Created by BigBuddha on 2017/08/30.
 */

public class ShowDateDialogFragment extends DialogFragment {

    private String mTitle, mContent;
    private boolean isLocation;
    private long mData;
    private double mLatitude, mLongitude;


    public static ShowDateDialogFragment newInstance() {
        return new ShowDateDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        getDate( getArguments() );
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_date, null);

        ((TextView)view.findViewById(R.id.tv_title_fragment)).setText( mTitle );
        ((TextView)view.findViewById(R.id.tv_content_fragment)).setText( mContent );
        if (mData != 0) {
            ((TextView)view.findViewById(R.id.tv_schedule_fragment)).setText( formatData( mData ) );
        }
        if (isLocation) {
            ((TextView)view.findViewById(R.id.tv_location_fragment)).setText( mLatitude + "," + mLongitude );
        }

        builder.setView(view)
                .setPositiveButton("OK", null);

        return builder.create();
    }

    private String formatData(long data) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(data);
    }

    private void getDate(Bundle args) {
        mTitle     = args.getString( getString(R.string.key_title) );
        mContent   = args.getString( getString(R.string.key_content) );
        isLocation = args.getBoolean( getString(R.string.key_is_location) );
        mData      = args.getLong( getString(R.string.key_data), 0 );
        if ( isLocation ) {
            mLatitude  = args.getDouble( getString(R.string.key_latitude) );
            mLongitude = args.getDouble( getString(R.string.key_longitude) );
        }
    }

}
