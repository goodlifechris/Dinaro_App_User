package com.dinaro.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dinaro.R;
import com.dinaro.SharePre;
import com.dinaro.databinding.ActivityLoginBinding;
import com.dinaro.fragments.EmailFragment;
import com.dinaro.fragments.PinFragment;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public ActivityLoginBinding binding;
    private Context context;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = LoginActivity.this;
        binding.forgotPassword.setVisibility(View.VISIBLE);


        spannableString();

        Bundle bundle = getIntent().getExtras();


        if (Objects.requireNonNull(bundle).containsKey("1")) {
            binding.forgotPassword.setVisibility(View.INVISIBLE);
            loadFragment(new PinFragment());
            binding.tvEmail.setTextColor(context.getResources().getColor(R.color.color_black));
            binding.tvEmail.setBackground(context.getResources().getDrawable(R.drawable.backgroundwhite));
            binding.tvPin.setTextColor(context.getResources().getColor(R.color.colorWhite));
            binding.tvPin.setBackground(context.getResources().getDrawable(R.drawable.backgroundblack));
        } else {
            binding.tvPin.setTextColor(context.getResources().getColor(R.color.color_black));
            binding.tvPin.setBackground(context.getResources().getDrawable(R.drawable.backgroundwhite));
            binding.tvEmail.setTextColor(context.getResources().getColor(R.color.colorWhite));
            binding.tvEmail.setBackground(context.getResources().getDrawable(R.drawable.backgroundblack));

            loadFragment(new EmailFragment());
        }
        addListener();

    }


    private void loadFragment(Fragment Fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, Fragment);
        ft.commit();
    }

    private void spannableString() {
        final TextView textView = findViewById(R.id.tv_Login);
        final String text = "Log in to Dinaro.";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint text) {
                text.setColor(context.getResources().getColor(R.color.colorSignUpPink));

            }
        };
        ss.setSpan(clickableSpan, 16, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(ss);


    }

    private void addListener() {
        binding.tvEmail.setOnClickListener(this);
        binding.tvPin.setOnClickListener(this);
        binding.tvPin.setOnClickListener(this);
        binding.forgotPassword.setOnClickListener(this);
        binding.tvSignupEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEmail:
                binding.forgotPassword.setVisibility(View.VISIBLE);

                binding.tvPin.setTextColor(context.getResources().getColor(R.color.color_black));
                binding.tvPin.setBackground(context.getResources().getDrawable(R.drawable.backgroundwhite));
                binding.tvEmail.setTextColor(context.getResources().getColor(R.color.colorWhite));
                binding.tvEmail.setBackground(context.getResources().getDrawable(R.drawable.backgroundblack));

                loadFragment(new EmailFragment());
                break;

            case R.id.tvPin:
                binding.forgotPassword.setVisibility(View.INVISIBLE);
                binding.tvEmail.setTextColor(context.getResources().getColor(R.color.color_black));
                binding.tvEmail.setBackground(context.getResources().getDrawable(R.drawable.backgroundwhite));
                binding.tvPin.setTextColor(context.getResources().getColor(R.color.colorWhite));
                binding.tvPin.setBackground(context.getResources().getDrawable(R.drawable.backgroundblack));
                loadFragment(new PinFragment());
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                //finish();
                break;
            case R.id.tv_Signup_email:
                SharePre.SaveId(context, "pin", "1");
                startActivity(new Intent(context, SignUpActivity.class));

                break;
            default:

        }

    }
}
