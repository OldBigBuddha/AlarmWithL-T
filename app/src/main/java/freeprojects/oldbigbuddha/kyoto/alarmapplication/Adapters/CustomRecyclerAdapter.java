package freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;

/**
 * Created by lifeistech on 2017/08/02.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private List<AlarmRealmData> datas;

    public CustomRecyclerAdapter(List<AlarmRealmData> list) {
        datas = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder vh =

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ViewHolder {

        public TextView tvIndex;
        public TextView tvContext;
        public ImageButton ibEdit;

        public ViewHolder(View view) {
            tvIndex = (TextView)view.findViewById(R.id.tv_index);
            tvContext = (TextView)view.findViewById(R.id.tv_title);
            ibEdit = (ImageButton)view.findViewById(R.id.ib_edit_data);
        }
    }
}
