package com.dinaro.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dinaro.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnBoardingGetStarted extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_get_started);
        ButterKnife.bind(this);

        //initialize the navController and make it reference the navhost
         navController= Navigation.findNavController(this,R.id.nav_host_fragment);



    }

    @OnClick(R.id.fab)
    public void onViewClicked() {

        switch (navController.getCurrentDestination().getId()) {
            case R.id.phoneNumberFragment:
                navController.navigate(R.id.action_phoneNumberFragment_to_checkRegisteredFragment);
                break;
            case R.id.passwordFragment:
                Intent i = new Intent(this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("2", "xyz");
                i.putExtras(bundle);
                startActivity(i);
                finish();
                break;

            case R.id.namesFragment:
               navController.navigate(R.id.action_namesFragment_to_emailAddressFragment);
               break;
//
            case R.id.emailAddressFragment:
                Bundle bundles = new Bundle();
                bundles.putString("title", "Welcome! Login and start enjoying smooooth payments!");
                bundles.putInt("type", 1);
                navController.navigate(R.id.action_emailAddressFragment_to_passwordFragment,bundles);

                break;
        }

    }
}
