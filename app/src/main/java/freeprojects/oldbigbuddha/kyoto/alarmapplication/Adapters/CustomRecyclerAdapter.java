package freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.MainActivity;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentDialogTimeBinding;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentShowDateBinding;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.RowBinding;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>{

    private Realm mRealm;
    private RealmResults<AlarmRealmData> mResults;
    private Context mContext;

    private AdapterListener mListener;
    public interface AdapterListener {
        void OnClickEditButton(int position);
        void OnItemClick(int position);
    }

    public void setListener(AdapterListener listener) {
        mListener = listener;
    }

    public CustomRecyclerAdapter(RealmResults<AlarmRealmData> results, Context context) {
        mResults = results;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mRealm = Realm.getDefaultInstance();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AlarmRealmData data = mResults.get(position);
        holder.binding.setPosition(position + 1 + "");
        Log.d("toString", data.toString());

        holder.binding.tvTitle.setText(data.getTitle());
        holder.binding.tvCreatedData.setText( formatData( Long.parseLong( data.getGeofenceId() )) );
        if (data.getDate() != null) {
            holder.binding.tvAlarmData.setText( formatData(data.getDate().getTime()) );
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnItemClick", "OnClick");
                mListener.OnItemClick(position);
            }
        });
        holder.binding.ibEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClickEditButton", "OnClick");
                mListener.OnClickEditButton(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public void removeItem(int position) {
        mRealm.beginTransaction();
        mResults.deleteFromRealm(position);
        notifyDataSetChanged();
        mRealm.commitTransaction();

    }

    private String formatData(long data) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(data);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RowBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}
