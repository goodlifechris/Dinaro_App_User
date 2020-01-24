package com.dinaro.activities;

import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dinaro.R;
import com.dinaro.databinding.ActivityDashBoardBinding;
import com.dinaro.fragments.FragementPayBill;
import com.dinaro.fragments.FragementSetting;
import com.dinaro.fragments.FragmentRecent;
import com.dinaro.utils.AppConstant;


public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ActivityDashBoardBinding binding;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        preferences.edit().putInt(AppConstant.VERIFIED_TYPE, 1).apply();

        getIntentProfile();
        loadFragment(new FragmentRecent());
        binding.bottomNavigation.ivActivityPaybill.setImageResource(R.drawable.menu);
        binding.bottomNavigation.ivActivityPaybill.setColorFilter(getResources().getColor(R.color.color_black));
        binding.bottomNavigation.tvActivityPaybill.setTextColor(getResources().getColor(R.color.color_black));
        binding.bottomNavigation.llActivity.setOnClickListener(this);
        binding.bottomNavigation.ivPayBill.setOnClickListener(this);
        binding.bottomNavigation.llProfile.setOnClickListener(this);
    }

    private void getIntentProfile() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("1")) {

                loadFragment(new FragmentRecent());
            } else if (bundle.containsKey("2")) {
                loadFragment(new FragementPayBill());
            } else {
                loadFragment(new FragmentRecent());

            }
        }
    }


    public void openPayBillFragment(){
        binding.bottomNavigation.ivPayBill.setImageResource(R.drawable.ic_paybill_button_sel);
        binding.bottomNavigation.ivActivityPaybill.setImageResource(R.drawable.menu);
        binding.bottomNavigation.ivActivityPaybill.setColorFilter(getResources().getColor(R.color.colorTextView));
        binding.bottomNavigation.tvActivityPaybill.setTextColor(getResources().getColor(R.color.colorTextView));
        binding.bottomNavigation.ivProfilePaybill.setImageResource(R.drawable.settings);
        binding.bottomNavigation.ivProfilePaybill.setColorFilter(getResources().getColor(R.color.colorTextView));
        binding.bottomNavigation.tvProfilePaybill.setTextColor(getResources().getColor(R.color.colorTextView));
        loadFragment(new FragementPayBill());
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llActivity:
                loadFragment(new FragmentRecent());
                binding.bottomNavigation.ivPayBill.setImageResource(R.drawable.ic_paybill_button);
                binding.bottomNavigation.ivProfilePaybill.setImageResource(R.drawable.settings);
                binding.bottomNavigation.ivProfilePaybill.setColorFilter(getResources().getColor(R.color.colorTextView));
                binding.bottomNavigation.tvProfilePaybill.setTextColor(getResources().getColor(R.color.colorTextView));
                binding.bottomNavigation.ivActivityPaybill.setImageResource(R.drawable.menu);
                binding.bottomNavigation.ivActivityPaybill.setColorFilter(getResources().getColor(R.color.color_black));
                binding.bottomNavigation.tvActivityPaybill.setTextColor(getResources().getColor(R.color.color_black));

                break;
            case R.id.llProfile:

                binding.bottomNavigation.ivPayBill.setImageResource(R.drawable.ic_paybill_button);
                binding.bottomNavigation.ivActivityPaybill.setImageResource(R.drawable.menu);
                binding.bottomNavigation.ivActivityPaybill.setColorFilter(getResources().getColor(R.color.colorTextView));
                binding.bottomNavigation.tvActivityPaybill.setTextColor(getResources().getColor(R.color.colorTextView));
                binding.bottomNavigation.ivProfilePaybill.setImageResource(R.drawable.settings);
                binding.bottomNavigation.ivProfilePaybill.setColorFilter(getResources().getColor(R.color.color_black));
                binding.bottomNavigation.tvProfilePaybill.setTextColor(getResources().getColor(R.color.color_black));
                loadFragment(new FragementSetting());


                break;
            case R.id.ivPayBill:
               openPayBillFragment();
                break;
        }
    }
}
