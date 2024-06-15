package com.example.reconaiddev;

import android.animation.ObjectAnimator;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    //initialize buttons
    private ImageButton buttonFragment1;
    private ImageButton buttonFragment2;
    private ImageButton buttonFragment3;

    private ImageButton currentSelectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialized by android studio
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set buttons to buttons on xml
        buttonFragment1 = findViewById(R.id.locationButton);
        buttonFragment2 = findViewById(R.id.homeButton);
        buttonFragment3 = findViewById(R.id.callButton);



        //make home default fragment
        if (savedInstanceState == null) {
            switchFragment(new HomeFragment(), false, null);
            setButtonSelected(buttonFragment2);
            currentSelectedButton = buttonFragment2;
        }

        //move to location fragment
        buttonFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if already on same page do nothing
                if (currentSelectedButton == buttonFragment1) {
                    return;
                }
                //change fragment
                switchFragment(new LocationFragment(), true, buttonFragment1);
                setButtonSelected(buttonFragment1);
            }
        });

        //move to home fragment
        buttonFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedButton == buttonFragment2) {
                    return;
                }
                switchFragment(new HomeFragment(), true, buttonFragment2);
                setButtonSelected(buttonFragment2);
            }
        });

        //move to call fragment
        buttonFragment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedButton == buttonFragment3) {
                    return;
                }
                switchFragment(new CallFragment(), true, buttonFragment3);
                setButtonSelected(buttonFragment3);
            }
        });
    }

    //called by setonclicklistener to change fragment and animate the transition
    //will move left if button is to the left of original button, vice versa
    private void switchFragment(Fragment fragment, boolean animate, ImageButton selectedButton) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(animate) {
            int enterAnim;
            int exitAnim;

            if (currentSelectedButton == null || selectedButton == null) {
                enterAnim = R.anim.slide_in_right;
                exitAnim = R.anim.slide_out_left;
            } else {
                //determine direction based on buttons positions
                if (currentSelectedButton.getX() < selectedButton.getX()) {
                    enterAnim = R.anim.slide_in_right;
                    exitAnim = R.anim.slide_out_left;
                } else {
                    enterAnim = R.anim.slide_in_left;
                    exitAnim = R.anim.slide_out_right;
                }
            }

            transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        }

        transaction.replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit();
    }

    //change background color when button is selected
    private void setButtonSelected(ImageButton selectedButton) {
        if (currentSelectedButton !=null) {
            animateButtonColor(currentSelectedButton, getResources().getColor(R.color.darkPink), getResources().getColor(R.color.lightPink));
        }

        animateButtonColor(selectedButton, getResources().getColor(R.color.lightPink), getResources().getColor(R.color.darkPink));
        currentSelectedButton = selectedButton;
    }

    //animates background colors
    private void animateButtonColor(final ImageButton button, int colorFrom, int colorTo) {
        ObjectAnimator colorAnim = ObjectAnimator.ofObject(button, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo);
        colorAnim.setDuration(300);
        colorAnim.start();
    }

}