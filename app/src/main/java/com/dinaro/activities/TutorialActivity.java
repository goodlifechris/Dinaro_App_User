package com.dinaro.activities;

import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.adapters.TutorialAdapter;
import com.dinaro.adapters.ViewPagerAdapter;
import com.dinaro.fragments.FirstFragment;
import com.dinaro.fragments.FirstNewFragment;
import com.dinaro.fragments.ThirdFragment;
import com.dinaro.utils.PrefManager;

import java.util.Timer;
import java.util.TimerTask;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {


    private static int num = 2;
    private static int currentpage = 0;


    ViewPager viewPager;

    Button buttonContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tutorial);
        TutorialActivity mContext = this;
        PrefManager.savePref(mContext, "IsFirstLogin", "1");

        initViews();

        setUpViewpagerAndTablLayout(viewPager);

    }

    private void initViews() {

        buttonContinue = findViewById(R.id.btnContinue);
        viewPager = findViewById(R.id.viewPager);

    }

    private void setUpViewpagerAndTablLayout(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());


        // Add Fragments to adapter one by one
        adapter.addFragment(new FirstNewFragment(), null);
        adapter.addFragment(new FirstFragment(), null);
        adapter.addFragment(new ThirdFragment(), null);

        viewPager.setAdapter(adapter);
//        viewPager.setPageTransformer(true, ZoomOutPageTransformer());

        buttonContinue.setOnClickListener(v -> {
            viewPager.setCurrentItem(1, true);

        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentpage=viewPager.getCurrentItem();


                if (viewPager.getCurrentItem()==0){


                    buttonContinue.setOnClickListener(v -> {
                        viewPager.setCurrentItem(currentpage++, true);


                    });


                }

                if (viewPager.getCurrentItem()==1){
                    buttonContinue.setOnClickListener(v -> {
                        viewPager.setCurrentItem(currentpage++, true);


                    });



                }


                if (viewPager.getCurrentItem() == 2) {

                    buttonContinue.setText("Get started");
                    buttonContinue.setOnClickListener(v -> {

                        Intent i = new Intent(TutorialActivity.this, OnBoardingGetStarted.class);
//                        ActivityOptionsCompat options = ActivityOptionsCompat.
//                                makeSceneTransitionAnimation(TutorialActivity.this, buttonContinue, "skip");
//                        startActivity(i, options.toBundle());
                        startActivity(i);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("2", "xyz");
//                        i.putExtras(bundle);
//                        startActivity(i);
//                        finish();
                    });





                }else{

                    buttonContinue.setOnClickListener(v -> {

                        viewPager.setCurrentItem(currentpage+1, true);

                    });
                }
            }
        });


    }

//                            Intent i = new Intent(TutorialActivity.this, LoginActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("2", "xyz");
//                            i.putExtras(bundle);
//                            startActivity(i);
//                            finishAffinity();


//
//                            Intent i = new Intent(TutorialActivity.this, LoginActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("2", "xyz");
//                            i.putExtras(bundle);
//                            startActivity(i);
//                            finishAffinity();


    @Override
    public void onClick(View view) {
        Intent i = new Intent(TutorialActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("2", "xyz");
        i.putExtras(bundle);
        startActivity(i);
        finishAffinity();
    }
}
