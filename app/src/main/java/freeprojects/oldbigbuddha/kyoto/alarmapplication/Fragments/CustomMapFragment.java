package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by BigBuddha on 2017/08/06.
 */

public class CustomMapFragment extends MapFragment {
    private ScrollView parent;

    public CustomMapFragment() {}

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View layout = super.onCreateView(layoutInflater, viewGroup, bundle);

        TouchableWrapper wrapper = new TouchableWrapper(getActivity());
        ((ViewGroup)layout).addView(wrapper);
        return layout;
    }

    public void setParent(ScrollView scrollView) {
        parent = scrollView;
    }

    private class TouchableWrapper extends FrameLayout {

        public TouchableWrapper(@NonNull Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN: {
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                }
            }
            return super.dispatchTouchEvent(ev);
        }
    }
}
