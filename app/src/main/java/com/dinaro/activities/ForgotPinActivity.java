package com.dinaro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.models.RequestModel.forgetPin.Data;
import com.dinaro.models.RequestModel.forgetPin.ForgetUser;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.ForgotRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;

public class ForgotPinActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView tvCancel;
    private EditText ed1;
    private Button btn1;
    private  String mobile_number, user_id, mobileNumber;
    private SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private Integer otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_pin);

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        ed1 = findViewById(R.id.edtPinMobile);
        btn1 = findViewById(R.id.btnPin_Continue);
        btn1.setOnClickListener(this);
        tvCancel = findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        ed1.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPin_Continue:
                if (checkValidation()) {
                  //  forgotPinApi();
                    PrefManagerNew.saveStringPreferences(this,AppConstant.EMAIL,mobile_number);
                    Intent intent = new Intent(ForgotPinActivity.this, PintActivity.class);
                   // intent.putExtra("otp", "" + otp);
                    intent.putExtra("pinmail", mobile_number);
                    startActivity(intent);


                }
                break;

            case R.id.tvCancel:
                finish();
                break;
        }
    }

    private void forgotPinApi() {
        if (CommonUtils.isOnline(this)) {
            ProgressDialogUtils.show(ForgotPinActivity.this);
            ForgotRequest forgotRequest = new ForgotRequest();
            forgotRequest.setMobile(mobile_number);
            forgotRequest.setCountryCode(AppConstant.Country_Code);



            Api api = RequestController.createService(Api.class);
            api.getMobile(forgotRequest).enqueue(new BaseCallback<ForgetUser>(getApplication()) {
                @Override
                public void onSuccess(ForgetUser response) {
                    ProgressDialogUtils.dismiss();
                    if (response != null) {

                        if (response.getStatus() == 1) {
                            String msg = response.getMessage();
                          //  Toast.makeText(ForgotPinActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Data data = response.getResult().getData();
                            String otp = data.getOtp();
                            Intent intent = new Intent(ForgotPinActivity.this, PintActivity.class);
                            intent.putExtra("otp", "" + otp);
                            intent.putExtra("pinmail", "" + AppConstant.Country_Code + mobile_number);
                            startActivity(intent);
                            // finish();
                        } else {
                            String msg = response.getMessage();
                            Toast.makeText(ForgotPinActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(ForgotPinActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                    }

                }


                @Override
                public void onFail(Call<ForgetUser> call, BaseResponse baseResponse) {

                    if (baseResponse!=null){

                        Toast.makeText(ForgotPinActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ForgotPinActivity.this, getString(R.string.server_not_responding), Toast.LENGTH_SHORT).show();


                    }

                    ProgressDialogUtils.dismiss();

                }
            });


        } else {
            Toast.makeText(ForgotPinActivity.this, R.string.networ_error, Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkValidation() {


        mobile_number = ed1.getText().toString();
        if (mobile_number.length() == 0) {
            ed1.requestFocus();
            ed1.setError(getResources().getString(R.string.empty_email));
            return false;
        } else if (!ValidationUtils.validEmail(mobile_number)) {
            ed1.requestFocus();
            ed1.setError(getResources().getString(R.string.invalid_email));
            return false;
        }
        return true;


//        mobile_number = ed1.getText().toString().trim();
//        String mobile_pattern = "((\\+*)((0[ -]+)*|(91)*(\\d{7,14}))|\\d{5}([- ]*)\\d{6})";
//        if (mobile_number.isEmpty()) {
//            ed1.requestFocus();
//            ed1.setError(getResources().getString(R.string.empty_phone));
//            return false;
//        } else if (!(mobile_number.matches(mobile_pattern))) {
//            ed1.requestFocus();
//            ed1.setError(getResources().getString(R.string.invalid_mobile));
//            return false;
//
//        }
//
//        return true;
    }


}
