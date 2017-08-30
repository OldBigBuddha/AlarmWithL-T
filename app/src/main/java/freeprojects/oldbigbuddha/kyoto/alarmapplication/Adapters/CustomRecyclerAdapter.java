package freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters;

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

import freeprojects.oldbigbuddha.kyoto.alarmapplication.BR;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.MainActivity;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
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
        Log.d("toString", data.toString());
        holder.tvIndex.setText( (position + 1) + "");
        holder.tvTitle.setText(data.getTitle());
        holder.tvCreatedData.setText( formatData( Long.parseLong( data.getGeofenceId() )) );
        if (data.getDate() != null) {
            holder.tvAlarmData.setText( formatData(data.getDate().getTime()) );
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnItemClick", "OnClick");
                mListener.OnItemClick(position);
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

        public TextView tvIndex;
        public TextView tvTitle, tvCreatedData, tvAlarmData;
        public ImageButton ibEdit;

        public ViewHolder(View view) {
            super(view);
            tvIndex = (TextView)view.findViewById(R.id.tv_index);
            tvTitle = (TextView)view.findViewById(R.id.tv_title);
            tvCreatedData = (TextView)view.findViewById(R.id.tv_created_data);
            tvAlarmData   = (TextView)view.findViewById(R.id.tv_alarm_data);
            ibEdit  = (ImageButton)view.findViewById(R.id.ib_edit_data);
            ibEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "OnClicked", Toast.LENGTH_LONG).show();
                    mListener.OnClickEditButton(getAdapterPosition());
                }
            });
        }
    }
}
