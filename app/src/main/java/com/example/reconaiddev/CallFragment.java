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

    private SeekBar callSlider1;
    private SeekBar callSlider2;
    private SeekBar callSlider3;
    private SeekBar callSlider4;
    private TextView seekBarText1;
    private TextView seekBarText2;
    private TextView seekBarText3;
    private TextView seekBarText4;


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

        callSlider1 = view.findViewById(R.id.call_slider1);
        callSlider2 = view.findViewById(R.id.call_slider2);
        callSlider3 = view.findViewById(R.id.call_slider3);
        callSlider4 = view.findViewById(R.id.call_slider4);
        seekBarText1 = view.findViewById(R.id.call_slider_text1);
        seekBarText2 = view.findViewById(R.id.call_slider_text2);
        seekBarText3 = view.findViewById(R.id.call_slider_text3);
        seekBarText4 = view.findViewById(R.id.call_slider_text4);

        //whole of these is for handling the seekbar functions
        callSlider1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                fadeTextOut1();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    seekbarReturnAnimate(seekBar);
                }
                fadeTextIn1();



            }
        });

        callSlider2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                fadeTextOut2();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    seekbarReturnAnimate(seekBar);
                }
                fadeTextIn2();



            }
        });

        callSlider3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                fadeTextOut3();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    seekbarReturnAnimate(seekBar);
                }
                fadeTextIn3();



            }
        });

        callSlider4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                fadeTextOut4();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    seekbarReturnAnimate(seekBar);
                }
                fadeTextIn4();



            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //function for hiding text
    private void fadeTextOut1() {
        seekBarText1.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        seekBarText1.setVisibility(View.GONE);
                        seekBarText1.setAlpha((1.0f));
                    }
                });
    }

    private void fadeTextOut2() {
        seekBarText2.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        seekBarText2.setVisibility(View.GONE);
                        seekBarText2.setAlpha((1.0f));
                    }
                });
    }

    private void fadeTextOut3() {
        seekBarText3.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        seekBarText3.setVisibility(View.GONE);
                        seekBarText3.setAlpha((1.0f));
                    }
                });
    }

    private void fadeTextOut4() {
        seekBarText4.animate()
                .alpha(0.0f)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        seekBarText4.setVisibility(View.GONE);
                        seekBarText4.setAlpha((1.0f));
                    }
                });
    }

    //function for showing text fade in
    private void fadeTextIn1() {
        seekBarText1.setVisibility(View.VISIBLE);
        seekBarText1.setAlpha(0.0f);
        seekBarText1.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start();

    }

    private void fadeTextIn2() {
        seekBarText2.setVisibility(View.VISIBLE);
        seekBarText2.setAlpha(0.0f);
        seekBarText2.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start();

    }

    private void fadeTextIn3() {
        seekBarText3.setVisibility(View.VISIBLE);
        seekBarText3.setAlpha(0.0f);
        seekBarText3.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start();

    }

    private void fadeTextIn4() {
        seekBarText4.setVisibility(View.VISIBLE);
        seekBarText4.setAlpha(0.0f);
        seekBarText4.animate()
                .alpha(1.0f)
                .setDuration(200)
                .start();

    }

    //resetting the seekbar when calling or when letting go
    private void resetSeekBar() {
        callSlider1.setProgress(0);
        callSlider2.setProgress(0);
        callSlider3.setProgress(0);
        callSlider4.setProgress(0);
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