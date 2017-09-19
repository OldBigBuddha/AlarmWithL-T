package freeprojects.oldbigbuddha.kyoto.alarmapplication.Fragments.Senior;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import freeprojects.oldbigbuddha.kyoto.alarmapplication.R;
import freeprojects.oldbigbuddha.kyoto.alarmapplication.databinding.FragmentYesNoBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class YesNoFragment extends Fragment {

    private FragmentYesNoBinding mBinding;

    private ArrayList<String> mAnswers   = new ArrayList<>(),
                              mQuestions = new ArrayList<>();

    private int answerCount = 0;

    private OnSelectedAnswerListener mListener;
    public interface OnSelectedAnswerListener {
        // TODO 列挙型管理
        void onSelectedYes(boolean isFirst);
        void onSelectedNo();
    }
    public void setOnSelectedAnswerListener(OnSelectedAnswerListener listener) {
        mListener = listener;
    }

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
                mListener.onSelectedYes( getArguments().getBoolean( getString(R.string.key_is_senior_mode), true ) );
            }
        });
        mBinding.btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedNo();
            }
        });

        Bundle args = getArguments();
        mQuestions = args.getStringArrayList(getString(R.string.key_questions));
        if (args.getStringArray( getString(R.string.key_answers) ) != null) {
            mAnswers = args.getStringArrayList( getString(R.string.key_answers) );
        } else {
            mAnswers.add("はい");
            mAnswers.add("いいえ");
        }

        mBinding.btYes.setText( mAnswers.get(0) );
        mBinding.btNo.setText(  mAnswers.get(1) );
        mBinding.tvQuestion.setText(mQuestions.get( answerCount ));
        answerCount++;

        return mBinding.getRoot();
    }

}
