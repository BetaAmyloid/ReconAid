package com.example.reconaiddev;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.animation.ArgbEvaluator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    //initialize buttons
    private ImageButton locationButton;
    private ImageButton homeButton;
    private ImageButton contactsButton;
    private ImageButton currentSelectedButton;
    private Button markAsSafe;
    private Button markAsUnsafe;
    private ViewPager2 viewPager;

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
        locationButton = findViewById(R.id.locationButton);
        homeButton = findViewById(R.id.homeButton);
        contactsButton = findViewById(R.id.contactsButton);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1);
        setButtonSelected(homeButton);

        locationButton.setOnClickListener(v -> {
            viewPager.setCurrentItem(0);
            setButtonSelected(locationButton);
        });

        homeButton.setOnClickListener(v -> {
            viewPager.setCurrentItem(1);
            setButtonSelected(homeButton);
        });

        contactsButton.setOnClickListener(v -> {
            viewPager.setCurrentItem(2);
            setButtonSelected(contactsButton);
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setButtonSelected(locationButton);
                        break;
                    case 1:
                        setButtonSelected(homeButton);
                        break;
                    case 2:
                        setButtonSelected(contactsButton);
                        break;

                }
            }
        });

        viewPager.setPageTransformer(new SlidePageTransformer());


        if (savedInstanceState == null) {
            setButtonSelected(homeButton);
        }

        //make home default fragment
        /*
        if (savedInstanceState == null) {
            switchFragment(new HomeFragment(), false, null);
            setButtonSelected(homeButton);
            currentSelectedButton = homeButton;
        }
        //move to location fragment
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if already on same page do nothing
                if (currentSelectedButton == locationButton) {
                    return;
                }
                //change fragment
                switchFragment(new LocationFragment(), true, locationButton);
                viewPager.setCurrentItem(1);
                setButtonSelected(locationButton);
            }
        });

        //move to home fragment
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedButton == homeButton) {
                    return;
                }
                switchFragment(new HomeFragment(), true, homeButton);
                viewPager.setCurrentItem(0);
                setButtonSelected(homeButton);
            }
        });

        //move to call fragment
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedButton == contactsButton) {
                    return;
                }
                switchFragment(new CallFragment(), true, contactsButton);
                viewPager.setCurrentItem(2);
                setButtonSelected(contactsButton);
            }
        });*/
    }

    //called by setonclicklistener to change fragment and animate the transition
    //will move left if button is to the left of original button, vice versa
    /*private void switchFragment(Fragment fragment, boolean animate, ImageButton selectedButton) {
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
    }*/

    //change background color when button is selected
    private void setButtonSelected(ImageButton selectedButton) {
        if (currentSelectedButton != null) {
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
