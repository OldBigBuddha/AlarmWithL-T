package freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public CustomRecyclerAdapter(RealmResults<AlarmRealmData> results, Context context) {
        mResults = results;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlarmRealmData data = mResults.get(position);
        holder.binding.setVariable(BR.data, data);
        holder.binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
//       holder.binding.tvTitle.setText(position + "");
//        holder.tvContext.setText(data.getContext());
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding binding;

        public ViewHolder(View view) {
            super(view);
            DataBindingUtil.bind(view);
        }
    }
}
