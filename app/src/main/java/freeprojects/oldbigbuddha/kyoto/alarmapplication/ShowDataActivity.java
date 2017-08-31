package freeprojects.oldbigbuddha.kyoto.alarmapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.Adapters.CustomRecyclerAdapter;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragmennts.Dialogs.ShowDateDialogFragment;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.POJO.AlarmRealmData;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.Receivers.AlarmReceiver;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.ActivityShowDataBinding;
import io.realm.Realm;
import io.realm.RealmResults;

public class ShowDataActivity extends AppCompatActivity {

    private  ActivityShowDataBinding mBinding;

    private Realm mRealm;
    private RealmResults<AlarmRealmData> mResults;

    private SharedPreferences mConfig;


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

            AlarmRealmData deleteData = mResults.get(position);
            Log.d("Selected Position","Position:" + position);
            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.setType(deleteData.getGeofenceId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            pendingIntent.cancel();
            ((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(pendingIntent);

            CustomRecyclerAdapter adapter = (CustomRecyclerAdapter)mBinding.recycler.getAdapter();
            adapter.removeItem(position);
            adapter.notifyDataSetChanged();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mConfig = getSharedPreferences( getString(R.string.key_config), MODE_PRIVATE );
        boolean isNightMode = mConfig.getBoolean( getString(R.string.key_is_night_mode), false );
        if ( isNightMode ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_data);

        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(AlarmRealmData.class).findAll();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recycler.setLayoutManager(manager);
        final CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(mResults, getApplicationContext());
        adapter.setListener(new CustomRecyclerAdapter.AdapterListener() {
            @Override
            public void OnItemClick(int position) {
                AlarmRealmData data = mResults.get(position);
                ShowDateDialogFragment fragment = ShowDateDialogFragment.newInstance();

                Bundle args = new Bundle();
                args.putString( getString(R.string.key_title), data.getTitle() );
                args.putString( getString(R.string.key_content), data.getContent());
                args.putBoolean( getString(R.string.key_is_location), data.isLocation());
                if (data.getDate() != null) {
                    args.putLong( getString(R.string.key_data), data.getDate().getTime() );
                }
                if (data.isLocation()) {
                    args.putDouble( getString(R.string.key_latitude), data.getLatitude() );
                    args.putDouble( getString(R.string.key_longitude), data.getLongitude() );
                }
                fragment.setArguments(args);

                fragment.show(getSupportFragmentManager(), "ShowDateFragment");
            }

            @Override
            public void OnClickEditButton(int position) {
                Log.d("onClickEditButton", "OnClicked");
//                AlarmRealmData data = mResults.get(position);
//                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
//                Gson gson = new Gson();
//                intent.putExtra("data", gson.toJson(data));
//                startActivity(intent);
            }
        });
        mBinding.recycler.setAdapter(adapter);
        mHelper.attachToRecyclerView(mBinding.recycler);
        mBinding.recycler.addItemDecoration(mHelper);
    }

    private String formatData(long data) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(data);
    }

}
