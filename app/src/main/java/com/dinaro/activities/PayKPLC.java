package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dinaro.R;
import com.dinaro.databinding.ActivityPayKplcBinding;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.paymentRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayKPLC extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private ActivityPayKplcBinding binding;
    private String numberEmail, name, amount, title = null, input, userId, cardId, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_kplc);
        sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");

        getIntentData();

//        ClipboardManager clipboard = (ClipboardManager)   getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clip = ClipData.newPlainText("Copied", PrefManagerNew.getStringPreferences(PayKPLC.this, AppConstant.DEVICE_TOKEN));
//        clipboard.setPrimaryClip(clip);
//        Toast.makeText(PayKPLC.this, "Copied data: "+PrefManagerNew.getStringPreferences(PayKPLC.this, AppConstant.DEVICE_TOKEN), Toast.LENGTH_SHORT).show();
        binding.btnPay.setOnClickListener(this);

    }

    private void getIntentData() {
        title = getIntent().getStringExtra("Title");
        cardId = getIntent().getStringExtra("Id");
        image = getIntent().getStringExtra("Image");

        String textTitle = String.format("Pay for %1$s.", title);

        SpannableString ss = new SpannableString(textTitle);


        ss.setSpan(new ForegroundColorSpan(Color.MAGENTA), textTitle.length() - 1, textTitle.length(), 0);

        binding.tvTitle.setText(ss, TextView.BufferType.SPANNABLE);
        binding.tvKplcName.setText("Enter your " + title + " account");
        Picasso.with(getApplication()).load(image).into(binding.imagePayBill);


    }

    private void submitData() {
        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(getApplication());
                paymentRequest paymentRequest = new paymentRequest();
                paymentRequest.setAmount(amount);
                paymentRequest.setBillNumber(input);
                paymentRequest.setCardId(cardId);
                paymentRequest.setType(AppConstant.TYPE_UTILITY);
                paymentRequest.setUserId(userId);
                paymentRequest.setConsumerName(name);

                Api api = RequestController.createService(Api.class);
                api.setPaymentDetails(paymentRequest).enqueue(new Callback<DetailsBase>() {
                    @Override
                    public void onResponse(Call<DetailsBase> call, Response<DetailsBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                String id = response.body().getResult().getData().getId();

                                String msg = response.body().getMessage();
                                Toast.makeText(PayKPLC.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PayKPLC.this, PaymentMethodActivity.class);
                                intent.putExtra("trans_id", id);
                                System.out.println("first : " + id);
                                startActivity(intent);
                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(PayKPLC.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PayKPLC.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(PayKPLC.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPay:
                if (checkValidation()) {
//                    submitData();
                    paymentRequest paymentRequest = new paymentRequest();
                    paymentRequest.setAmount(amount);
                    paymentRequest.setBillNumber(input);
                    paymentRequest.setCardId(cardId);
                    paymentRequest.setType(AppConstant.TYPE_UTILITY);
                    paymentRequest.setUserId(userId);
                    paymentRequest.setConsumerName(name);
                    paymentRequest.setAccountType(AppConstant.ACCOUNT_TYPE_YES);

                    Intent intent = new Intent(PayKPLC.this, PaymentMethodActivity.class);
                    intent.putExtra("paymentRequest", paymentRequest);
                    startActivity(intent);

//                    Toast.makeText(this, "Please Wait.", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }


    private boolean checkValidation() {
        clearAll();
        input = binding.etEmail.getText().toString().trim();
        //numberEmail = binding.etEmail.getText().toString().trim();
        name = binding.etName.getText().toString().trim();
        amount = binding.etAmount.getText().toString().trim();
        String mobile_pattern = "((\\+*)((0[ -]+)*|(91)*(\\d{7,14}))|\\d{5}([- ]*)\\d{6})";
        if (input.isEmpty()) {
            binding.etEmail.requestFocus();
            binding.tvNumberEmailError.setVisibility(View.VISIBLE);
            binding.tvNumberEmailError.setText("*Please enter account number.");
            return false;
        } else if (amount.isEmpty()) {
            binding.etAmount.requestFocus();
            binding.tvAmountError.setVisibility(View.VISIBLE);
            binding.tvAmountError.setText(R.string.empty_Ammount);
            return false;
        } /*else if (name.isEmpty()) {
            binding.etName.requestFocus();
            binding.tvNameError.setVisibility(View.VISIBLE);
            binding.tvNameError.setText(R.string.empty_name);
            return false;
        }*/ /*else if (input.contains("@")) {
            binding.etEmail.requestFocus();
            binding.tvNumberEmailError.setVisibility(View.VISIBLE);
            binding.tvNumberEmailError.setText(R.string.invalid_email);
            return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();

        } else if (!input.matches(mobile_pattern)) {
            binding.etEmail.requestFocus();
            binding.tvNumberEmailError.setVisibility(View.VISIBLE);
            binding.tvNumberEmailError.setText(R.string.invalid_mobile);
            return false;
        } */ else {
            return true;
        }


    }

    private void clearAll() {
        binding.tvNumberEmailError.setVisibility(View.GONE);
        binding.tvAmountError.setVisibility(View.GONE);
        binding.tvNameError.setVisibility(View.GONE);

    }

}
