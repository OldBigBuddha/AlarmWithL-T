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

import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.BR;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.MainActivity;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import io.realm.RealmResults;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private RealmResults<AlarmRealmData> mResults;
    private Context mContext;
    private List<String> list;

    public CustomRecyclerAdapter(RealmResults<AlarmRealmData> results, Context context) {
        mResults = results;
        mContext = context;
    }
//
//    public CustomRecyclerAdapter(List<String> list ,Context context) {
//        this.list = list;
//        mContext = context;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmRealmData data = mResults.get(position);
        Log.d("toString", data.toString());
        holder.tvIndex.setText( (position + 1) + "");
        holder.tvTitle.setText(data.getTitle());
        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "ItemClicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return mResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvIndex;
        public TextView tvTitle;
        public ImageButton ibEdit;

        public ViewHolder(View view) {
            super(view);
            tvIndex = (TextView)view.findViewById(R.id.tv_index);
            tvTitle = (TextView)view.findViewById(R.id.tv_title);
            ibEdit  = (ImageButton)view.findViewById(R.id.ib_edit_data);
        }
    }
}
