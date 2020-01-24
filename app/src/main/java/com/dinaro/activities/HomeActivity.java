package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinaro.R;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    TextView txtSettings;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        initiateView();
        homeClicked();
    }


    private Drawable changeIconColor(Context context , int drawable){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorAccent));

        return wrappedDrawable;

    }

    private Drawable changeIconColorBack(Context context , int drawable){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, drawable);
        assert unwrappedDrawable != null;
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.grey));

        return wrappedDrawable;

    }

    ImageView img_settings,img_discovery,img_stats,img_activity,imageviewHome;

    private void initiateView() {

        img_activity = findViewById(R.id.img_activity);
        imageviewHome = findViewById(R.id.imageviewHome);
        img_settings = findViewById(R.id.img_settings);
        img_discovery = findViewById(R.id.img_discovery);
        img_stats = findViewById(R.id.img_stats);

        onClick();


    }


    private void onClick() {




        img_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityClicked();
                if (navController.getCurrentDestination().getId() == R.id.activityFragment) {


                } else if (navController.getCurrentDestination().getId() == R.id.homeFragment) {

                    navController.navigate(R.id.action_homeFragment_to_activityFragment);


                }else if (navController.getCurrentDestination().getId() == R.id.discoveryFragment) {

                    navController.navigate(R.id.action_discoveryFragment_to_activityFragment);


                } else if (navController.getCurrentDestination().getId() == R.id.settingsFragment) {

                    navController.navigate(R.id.action_settingsFragment_to_activityFragment);

                }else if (navController.getCurrentDestination().getId() == R.id.statsFragment) {

                    navController.navigate(R.id.action_statsFragment_to_activityFragment);

                }
            }
        });



        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settingsClicked();
                if (navController.getCurrentDestination().getId() == R.id.activityFragment) {
                    navController.navigate(R.id.action_activityFragment_to_settingsFragment);

                } else if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
                    navController.navigate(R.id.action_homeFragment_to_settingsFragment);


                }else if (navController.getCurrentDestination().getId() == R.id.discoveryFragment) {

                    navController.navigate(R.id.action_discoveryFragment_to_settingsFragment);


                } else if (navController.getCurrentDestination().getId() == R.id.settingsFragment) {

                }else if (navController.getCurrentDestination().getId() == R.id.statsFragment) {

                    navController.navigate(R.id.action_statsFragment_to_settingsFragment);

                }

            }

        });


        imageviewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeClicked();

                if (navController.getCurrentDestination().getId() == R.id.activityFragment) {
                    navController.navigate(R.id.action_activityFragment_to_homeFragment);

                } else if (navController.getCurrentDestination().getId() == R.id.homeFragment) {


                }else if (navController.getCurrentDestination().getId() == R.id.discoveryFragment) {

                    navController.navigate(R.id.action_discoveryFragment_to_homeFragment);


                } else if (navController.getCurrentDestination().getId() == R.id.settingsFragment) {

                    navController.navigate(R.id.action_settingsFragment_to_homeFragment);

                }else if (navController.getCurrentDestination().getId() == R.id.statsFragment) {

                    navController.navigate(R.id.action_statsFragment_to_homeFragment);

                }
            }
        });


        img_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statsClicked();

                if (navController.getCurrentDestination().getId() == R.id.activityFragment) {
                    navController.navigate(R.id.action_activityFragment_to_statsFragment);

                } else if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
                    navController.navigate(R.id.action_homeFragment_to_statsFragment);


                } else if (navController.getCurrentDestination().getId() == R.id.statsFragment) {

                }else if (navController.getCurrentDestination().getId() == R.id.discoveryFragment) {

                    navController.navigate(R.id.action_discoveryFragment_to_statsFragment);

                }else if (navController.getCurrentDestination().getId() == R.id.settingsFragment) {

                    navController.navigate(R.id.action_settingsFragment_to_statsFragment);

                }

            }
        });

        img_discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discoveryClicked();

                if (navController.getCurrentDestination().getId() == R.id.activityFragment) {
                    navController.navigate(R.id.action_activityFragment_to_discoveryFragment);

                } else if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
                    navController.navigate(R.id.action_homeFragment_to_discoveryFragment);


                } else if (navController.getCurrentDestination().getId() == R.id.discoveryFragment) {

                }else if (navController.getCurrentDestination().getId() == R.id.statsFragment) {

                    navController.navigate(R.id.action_statsFragment_to_discoveryFragment);

                }else if (navController.getCurrentDestination().getId() == R.id.settingsFragment) {

                    navController.navigate(R.id.action_settingsFragment_to_discoveryFragment);

                }
            }
        });
    }

    private void activityClicked(){
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorDefault = getResources().getColor(R.color.colorGreyPrimary);
        img_activity.setImageResource(R.drawable.ic_activity_selected);

        img_settings.setImageResource(R.drawable.ic_settings);
        img_stats.setImageResource(R.drawable.ic_stats);
        img_discovery.setImageResource(R.drawable.ic_events);
        imageviewHome.setImageResource(R.drawable.ic_non_clicked);



    }



    private void homeClicked(){
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorDefault = getResources().getColor(R.color.colorGreyPrimary);
        imageviewHome.setImageResource(R.drawable.ic_clicked_home);

        img_activity.setImageResource(R.drawable.ic_activity);
        img_settings.setImageResource(R.drawable.ic_settings);
        img_stats.setImageResource(R.drawable.ic_stats);
        img_discovery.setImageResource(R.drawable.ic_events);


    }

    private void settingsClicked(){
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorDefault = getResources().getColor(R.color.colorGreyPrimary);

        imageviewHome.setImageResource(R.drawable.ic_non_clicked);

        img_settings.setImageResource(R.drawable.ic_settings_selected);
        img_activity.setImageResource(R.drawable.ic_activity);
        img_stats.setImageResource(R.drawable.ic_stats);
        img_discovery.setImageResource(R.drawable.ic_events);

    }

    private void statsClicked(){
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorDefault = getResources().getColor(R.color.colorGreyPrimary);

        img_stats.setImageResource(R.drawable.ic_stats_selected);

        imageviewHome.setImageResource(R.drawable.ic_non_clicked);
        img_activity.setImageResource(R.drawable.ic_activity);
        img_settings.setImageResource(R.drawable.ic_settings);
        img_discovery.setImageResource(R.drawable.ic_events);



    }


    private void discoveryClicked(){
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorDefault = getResources().getColor(R.color.colorGreyPrimary);

        img_discovery.setImageResource(R.drawable.ic_events_selected);

        imageviewHome.setImageResource(R.drawable.ic_non_clicked);
        img_activity.setImageResource(R.drawable.ic_activity);
        img_settings.setImageResource(R.drawable.ic_settings);
        img_stats.setImageResource(R.drawable.ic_stats);


        setLocale();



    }




    public void setLocale() {
        Locale myLocale = new Locale("en");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();
    }
}
