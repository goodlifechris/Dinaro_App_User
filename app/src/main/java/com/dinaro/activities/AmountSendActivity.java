package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dinaro.R;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.paymentRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AmountSendActivity extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private EditText etamount;
    private Button btn1;
    private String amount, userId, cardId;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_amount_send);

        sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etamount = findViewById(R.id.etAmount);
        btn1 = findViewById(R.id.btnSend);
        btn1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        if (checkValidation()) {

            // submitData();
            userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
            if (getIntent().getStringExtra("Id") != null) {
                cardId = getIntent().getStringExtra("Id");

            }
            paymentRequest paymentRequest = new paymentRequest();
            paymentRequest.setAmount(amount);
            paymentRequest.setCardId(cardId);
            paymentRequest.setType(AppConstant.TYPE_RESTAURANT);
            paymentRequest.setUserId(userId);
            paymentRequest.setAccountType(AppConstant.ACCOUNT_TYPE_NO);

            Intent intent = new Intent(AmountSendActivity.this, PaymentMethodActivity.class);
            intent.putExtra("paymentRequest", paymentRequest);
            intent.putExtra("amount", amount);
            startActivity(intent);

//            Toast.makeText(this, "Please Wait.", Toast.LENGTH_SHORT).show();
        }

    }

    private void submitData() {

        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(getApplication());

                userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
                cardId = getIntent().getStringExtra("Id");
                paymentRequest paymentRequest = new paymentRequest();
                paymentRequest.setAmount(amount);
                paymentRequest.setCardId(cardId);
                paymentRequest.setType(AppConstant.TYPE_RESTAURANT);
                paymentRequest.setUserId(userId);


                Api api = RequestController.createService(Api.class);
                api.setPaymentDetails(paymentRequest).enqueue(new Callback<DetailsBase>() {
                    @Override
                    public void onResponse(Call<DetailsBase> call, Response<DetailsBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {


                                String id = response.body().getResult().getData().getId();
                                String msg = response.body().getMessage();
                                Toast.makeText(AmountSendActivity.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AmountSendActivity.this, PaymentMethodActivity.class);
                                intent.putExtra("trans_id", id);
                                startActivity(intent);

                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(AmountSendActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AmountSendActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(AmountSendActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkValidation() {

        amount = etamount.getText().toString().trim();
        if (amount.length() == 0) {
            etamount.requestFocus();
            etamount.setError(getResources().getString(R.string.empty_amount));
            return false;
        }
        return true;
    }
}
