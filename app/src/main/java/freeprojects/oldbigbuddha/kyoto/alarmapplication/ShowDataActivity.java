package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.content.Intent;
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

    private ItemTouchHelper mHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT);
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
            adapter.notifyDataSetChanged();

            AlarmRealmData deleteData = mResults.get(position);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.setType(deleteData.getDate().getTime() + "");
        }
    });

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
        mHelper.attachToRecyclerView(mBinding.recycler);
        mBinding.recycler.addItemDecoration(mHelper);
    }
}