package com.dinaro.activities;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dinaro.R;
import com.dinaro.databinding.ActivityConfirmpaymentBinding;

public class ConfirmPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityConfirmpaymentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmpayment);
        binding.btnCpPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cp_pay:
                startActivity(new Intent(ConfirmPaymentActivity.this, PaymentMethodActivity.class));

                break;
        }
    }
}
