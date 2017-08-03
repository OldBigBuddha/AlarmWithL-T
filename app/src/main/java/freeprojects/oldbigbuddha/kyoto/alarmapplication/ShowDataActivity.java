package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters.CustomRecyclerAdapter;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityShowDataBinding;
import io.realm.Realm;
import io.realm.RealmResults;

public class ShowDataActivity extends AppCompatActivity {

    private  ActivityShowDataBinding mBinding;

    private Realm mRealm;
    private RealmResults<AlarmRealmData> mResults;
    private List<String> list = new ArrayList<>();

    private ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return 0;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            CustomRecyclerAdapter adapter = (CustomRecyclerAdapter)mBinding.recycler.getAdapter();
            adapter.removeItem(position);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);

        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(AlarmRealmData.class).findAll();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recycler.setLayoutManager(manager);
        mBinding.recycler.setAdapter(new CustomRecyclerAdapter(mResults, getApplicationContext()));

    }
}
