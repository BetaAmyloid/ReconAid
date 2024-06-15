package com.example.reconaiddev;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AlphaAnimation;


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

        callSlider = view.findViewById(R.id.call_slider);
        seekBarText = view.findViewById(R.id.call_slider_text);

        callSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 100) {
                    triggerCall();
                    resetSeekBar();
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarText.animate()
                        .alpha(0.0f)
                        .setDuration(100)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                seekBarText.setVisibility(View.GONE);
                                seekBarText.setAlpha((1.0f));
                            }
                        });
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 100) {
                    resetSeekBar();
                }
                seekBarText.setVisibility(View.VISIBLE);


            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void resetSeekBar() {
        callSlider.setProgress(0);
        seekBarText.setVisibility(View.VISIBLE);
    }



    private void triggerCall() {
        // trigger to do things here after calling
        Toast.makeText(getActivity(), "Calling 911...", Toast.LENGTH_SHORT).show();
    }
}