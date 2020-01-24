package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dinaro.PinEntryView;
import com.dinaro.R;
import com.dinaro.SharePre;
import com.dinaro.models.RequestModel.pin.ResultPin;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.PinConformRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;

public class ConfirmPinActivity extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private Button btn_continue_confirmpin;
    private TextView tvPinError;
    private String pin, getPin, userId;
    private PinEntryView pinEntryView;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pin = SharePre.getUserBuyerId(mContext);
        setContentView(R.layout.activity_confirmpin);

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        userId = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");


        btn_continue_confirmpin = findViewById(R.id.btn_continue_ConfirmPin);
        tvPinError = findViewById(R.id.tvPinError);
        pinEntryView = findViewById(R.id.pinConfirm);
        btn_continue_confirmpin.setOnClickListener(this);


        Intent intent = getIntent();
        getPin = intent.getStringExtra("Pin");
        preferences.edit().putString(AppConstant.PIN_PREF, "" + getPin).apply();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue_ConfirmPin:
                if (checkValidation()) {

                    if (pin.equalsIgnoreCase("2")) {
                        getUserPin();
                    }
                    else if (pin.equalsIgnoreCase("1")) {
                        getUserPin();

                    }

                }
                break;
        }
    }

    private void getUserPin() {
        if (CommonUtils.isOnline(mContext)) {
            try {
                ProgressDialogUtils.show(ConfirmPinActivity.this);
                PinConformRequest pinConformRequest = new PinConformRequest();
                pinConformRequest.setPin(getPin);
                pinConformRequest.setUserId(userId);

                Api api = RequestController.createService(Api.class);
                api.getPin(pinConformRequest).enqueue(new BaseCallback<ResultPin>(mContext) {
                    @Override
                    public void onSuccess(ResultPin response) {
                        ProgressDialogUtils.dismiss();
                        if(response!=null) {
                            if (response.getStatus() == 1) {
                                if (pin.equalsIgnoreCase("2")) {
                                    Intent intent = new Intent(ConfirmPinActivity.this, LoginActivity.class);
                                    Toast.makeText(mContext, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("1", "abc");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else if (pin.equalsIgnoreCase("1")) {
                                    preferences.edit().putString(AppConstant.PIN, "" + response.getPin()).apply();
                                    Intent intent = new Intent(ConfirmPinActivity.this, DashBoardActivity.class);
                                    Toast.makeText(mContext, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("1", "abc");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                String msg = response.getMessage();
                                Toast.makeText(ConfirmPinActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(ConfirmPinActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFail(Call<ResultPin> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();
                        String msg = baseResponse.getMessage();
                        Toast.makeText(ConfirmPinActivity.this, "" + msg, Toast.LENGTH_SHORT).show();


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkValidation() {
        clearError();
        String confirmepin = pinEntryView.getText().toString();
        if (confirmepin.isEmpty()) {
            tvPinError.setVisibility(View.VISIBLE);
            tvPinError.setText(R.string.empty_pin);
            tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        } else if (!confirmepin.equals(getPin)) {
            tvPinError.setVisibility(View.VISIBLE);
            tvPinError.setText(R.string.pin_not_match);
            tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        }
        return true;
    }

    private void clearError() {
        tvPinError.setVisibility(View.GONE);

    }
}