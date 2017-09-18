package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentYesNoBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class YesNoFragment extends Fragment {

    public interface OnClickButtonListener {
        void onClickYes(View view);
        void onClickNo(View view);
    }

    private OnClickButtonListener mListener;

    public void setOnClickButtonListener(@NonNull OnClickButtonListener listener) {
        mListener = listener;
    }

    private FragmentYesNoBinding mBinding;

    private String[] mQuestions = new String[]{
            "今日ですか？",
            "今週ですか？",
            "今月ですか？",
            "今年ですか？",
    };
    private int pointer = 0;


    public YesNoFragment() {
        // Required empty public constructor
    }

    public static YesNoFragment getInstance() {
        return new YesNoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_yes_no, container, false);
        mBinding.tvQuestion.setText(mQuestions[pointer]);
        mBinding.btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick","OK");
                mListener.onClickYes(v);
//                pointer++;
//                if (pointer >= questions.length ) pointer = 0;
//                mBinding.tvQuestion.setText(questions[pointer]);
            }
        });
        mBinding.btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickNo(v);
            }
        });
        return mBinding.getRoot();
    }

    public void setQuestions(@NonNull String[] questions) {
        mQuestions = questions;
    }

}
