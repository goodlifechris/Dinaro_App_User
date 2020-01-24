package com.dinaro.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dinaro.PinEntryView;
import com.dinaro.R;


public class CreateOtpActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_continue_createotp;
    private String createpin;
    private PinEntryView pinEntryView;
    private TextView tvPinError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.create_otp_layout);
        btn_continue_createotp = findViewById(R.id.btn_continue_createotp);
        btn_continue_createotp.setOnClickListener(this);
        pinEntryView = findViewById(R.id.pinCreate);
        tvPinError = findViewById(R.id.tvPinError);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue_createotp:
                if (checkValidation()) {
                    Intent intent = new Intent(CreateOtpActivity.this, ConfirmPinActivity.class);
                    intent.putExtra("Pin", "" + createpin);
                    startActivity(intent);
                    finish();
                }
        }

    }

    private boolean checkValidation() {
        clearError();
        createpin = pinEntryView.getText().toString();
        if (createpin.isEmpty()) {
            tvPinError.setVisibility(View.VISIBLE);
            tvPinError.setText(R.string.empty_pin);
            tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        } else if (createpin.length() < 4) {
            tvPinError.setVisibility(View.VISIBLE);
            tvPinError.setText(R.string.invalid_pin);
            tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        }
        return true;
    }

    private void clearError() {
        tvPinError.setVisibility(View.GONE);

    }
}
