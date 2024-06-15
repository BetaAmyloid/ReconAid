package com.example.reconaiddev;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {

    private SeekBar callSlider;
    private TextView seekBarText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        callSlider = view.findViewById(R.id.call_slider1);
        seekBarText = view.findViewById(R.id.call_slider_text);

        //whole of these is for handling the seekbar functions
        callSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 100) {
                    triggerCall();
                    seekBar.setProgress(99);
                    seekbarReturnAnimate(seekBar);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                fadeTextOut();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    seekbarReturnAnimate(seekBar);
                }
                fadeTextIn();



            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //function for hiding text
    private void fadeTextOut() {
        seekBarText.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        seekBarText.setVisibility(View.GONE);
                        seekBarText.setAlpha((1.0f));
                    }
                });
    }

    //function for showing text fade in
    private void fadeTextIn() {
        seekBarText.setVisibility(View.VISIBLE);
        seekBarText.setAlpha(0.0f);
        seekBarText.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start();

    }

    //resetting the seekbar when calling or when letting go
    private void resetSeekBar() {
        callSlider.setProgress(0);
    }

    private void seekbarReturnAnimate(SeekBar seekBar) {
        ObjectAnimator animator = ObjectAnimator.ofInt(seekBar, "progress", 0);
        animator.setDuration(250);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                seekBar.setProgress(0);
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                seekBar.setProgress(0);
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }


    private void triggerCall() {
        // trigger to do things here after calling
        Toast.makeText(getActivity(), "Calling 911...", Toast.LENGTH_SHORT).show();
    }
}