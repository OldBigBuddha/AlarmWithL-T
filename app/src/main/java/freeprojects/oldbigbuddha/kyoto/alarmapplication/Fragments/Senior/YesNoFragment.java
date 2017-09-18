package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Senior;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentYesNoBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class YesNoFragment extends Fragment {

    private FragmentYesNoBinding mBinding;

    public interface OnSelectedAnswerListener {
        void onSelectedYes();
        void onSelectedNo();
    }
    private OnSelectedAnswerListener mListener;

    public void setOnSelectedAnswerListener(OnSelectedAnswerListener listener) {
        mListener = listener;
    }


    private String[] questions;

    public YesNoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_yes_no, container, false);
        mBinding.btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedYes();
            }
        });

        mBinding.btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedNo();
            }
        });
        Bundle args = getArguments();
        if (args == null) {
            new NullPointerException("args is null object");
        } else {
            questions = getArguments().getStringArray(getString(R.string.key_questions));
            mBinding.tvQuestion.setText(questions[0]);
        }
        return mBinding.getRoot();
    }

}
