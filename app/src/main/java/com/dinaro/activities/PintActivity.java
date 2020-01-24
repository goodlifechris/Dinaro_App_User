package com.dinaro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dinaro.R;
import com.dinaro.databinding.ActivityPintEntryBinding;
import com.dinaro.firebaseauth.FirebaseAuthentication;
import com.dinaro.firebaseauth.FirebasePhoneAuthListener;
import com.dinaro.models.RequestModel.ForgetPasswordEmail.Base;
import com.dinaro.models.RequestModel.VerifyEmailOtp.Result;
import com.dinaro.models.RequestModel.otp.OtpResult;
import com.dinaro.models.SignUpEmailData;
import com.dinaro.models.SingupVerifyModel;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.ForgetPasswordRequest;
import com.dinaro.service.apiRequest.ForgotEmailModel;
import com.dinaro.service.apiRequest.PinRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PintActivity extends AppCompatActivity implements View.OnClickListener, FirebasePhoneAuthListener {

    private String phone_num, userid;
    private String otp, signBundle, email = "", otpbyEmail = "", otp2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ActivityPintEntryBinding binding;
    private String from = "";
    private String verificationId, Enterpin,signupEmail;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(PintActivity.this, R.layout.activity_pint_entry);

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        binding.btncontinuePin.setOnClickListener(this);
        binding.tvResendOtp.setOnClickListener(this);

        userid = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");

//        FirebaseAuthentication.getInstance(this).setListener(this);
//        FirebaseAuthentication.getInstance(this).initialize();

        if (getIntent() != null) {



            if (getIntent().getStringExtra(AppConstant.FROM) != null) {
                from = getIntent().getStringExtra(AppConstant.FROM);

            }
            if ((getIntent().getStringExtra("otp") != null) && (getIntent().getStringExtra(AppConstant.USER_ID_BY_SIGNUP) != null)) {

                otp = getIntent().getStringExtra("otp");
                binding.pinPhone.setText(otp);
                userid = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
                phone_num = getIntent().getStringExtra("mobile");



            }

            if (getIntent().hasExtra("phone")) {

                phone_num = getIntent().getStringExtra("phone");
                signupEmail= PrefManagerNew.getStringPreferences(this,AppConstant.EMAIL);
                sendOtpOnEmail();
               // FirebaseAuthentication.getInstance(this).startPhoneNumberVerification(phone_num);




              /*  if (phone_num.equalsIgnoreCase("+254797655634")) {
                    if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
                        finish();
                    } else {
                        Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                } else {

                    FirebaseAuthentication.getInstance(this).startPhoneNumberVerification(phone_num);
                }*/
            }
            if(getIntent().hasExtra("pinmail"))
            {
                signupEmail=PrefManagerNew.getStringPreferences(this,AppConstant.EMAIL);
                sendOtpOnEmail();
            }


            email = getIntent().getStringExtra("email");
            otpbyEmail = getIntent().getStringExtra("otp");


        } else {
            Toast.makeText(this, R.string.NoData, Toast.LENGTH_SHORT).show();

        }

        Bundle bundle = getIntent().getExtras();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btncontinue_pin:
                if (checkValidation()) {

                    if (from.equalsIgnoreCase(AppConstant.SIGN_UP_ACTIVITY)) {


                        verifyOtpOnEmail();
//                        if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//
//
//                        } else {
//
//
//
//                           FirebaseAuthentication.getInstance(this).verifyPhoneNumberWithCode(verificationId, binding.pinPhone.getText().toString());
//                        }
                    }


                    else if (from.equalsIgnoreCase(AppConstant.FORGOT_PASSWORD_ACTIVITY)) {
                        VerifyOTPByEmail();
                    }


                    else if(from.equalsIgnoreCase(AppConstant.LOGIN_ACTIVITY))
                    {


                        verifyOtpOnEmaillogin();






//
//                        if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//                            if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
//                                finish();
//                            } else {
//                               Intent intent = new Intent(PintActivity.this, DashBoardActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("1", "abc");
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                finishAffinity();
//                            }
//
//                        } else {
//
//                            FirebaseAuthentication.getInstance(this).verifyPhoneNumberWithCode(verificationId, binding.pinPhone.getText().toString());
//                        }
//
//

                    }

                    else {



                        verifyOtpOnEmailPin();


                       // if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//                            if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
//                                finish();
//                            } else {
//                                Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
//                                startActivity(intent);
//                                finishAffinity();
//                            }

//                        } else {
//
//                            FirebaseAuthentication.getInstance(this).verifyPhoneNumberWithCode(verificationId, binding.pinPhone.getText().toString());
//                        }



                    }

                }
                break;
            case R.id.tvResendOtp:
                if (from.equalsIgnoreCase(AppConstant.SIGN_UP_ACTIVITY)) {

                    sendOtpOnEmail();

                   // if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//                        if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
//                            finish();
//                        } else {
//                            Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//                        }

//                    } else {
//
//                        if (resendToken != null) {
//                            FirebaseAuthentication.getInstance(this).resendVerificationCode(phone_num, resendToken);
//                        } else {
//                            FirebaseAuthentication.getInstance(this).startPhoneNumberVerification(phone_num);
//                        }
//
//                    }
                }

                else if (from.equalsIgnoreCase(AppConstant.FORGOT_PASSWORD_ACTIVITY)) {
                    forgotPinByEmailApi();
                }


                else if(from.equalsIgnoreCase(AppConstant.LOGIN_ACTIVITY))
                {

                    sendOtpOnEmail();
//                    if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//                        if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
//                            finish();
//                        } else {
//                            Intent intent = new Intent(PintActivity.this, DashBoardActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("1", "abc");
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            finishAffinity();
//                        }
//
//                    } else {
//
//
//                        FirebaseAuthentication.getInstance(this).verifyPhoneNumberWithCode(verificationId, binding.pinPhone.getText().toString());
//                    }
                }

                else {


                    sendOtpOnEmail();

                   // if (phone_num.equalsIgnoreCase(AppConstant.Country_Code+"0723994443")) {
//                        if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
//                            finish();
//                        } else {
//                            Intent intent = new Intent(PintActivity.this, DashBoardActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("1", "abc");
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            finishAffinity();
//                        }

//                    } else {
//
//                        if (resendToken != null) {
//                            FirebaseAuthentication.getInstance(this).resendVerificationCode(phone_num, resendToken);
//                        } else {
//                            FirebaseAuthentication.getInstance(this).startPhoneNumberVerification(phone_num);
//                        }
//
//                    }
                }

                break;

            default:
                break;
        }

    }

    private void forgotPinByEmailApi() {

        if (CommonUtils.isOnline(this)) {
            try {
                ProgressDialogUtils.show(PintActivity.this);

                ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
                forgetPasswordRequest.setEmail(email);

                Api api = RequestController.createService(Api.class);
                api.getEmail(forgetPasswordRequest).enqueue(new Callback<Base>() {
                    @Override
                    public void onResponse(Call<Base> call, Response<Base> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                com.dinaro.models.RequestModel.ForgetPasswordEmail.Data data = response.body().getResult().getData();
                                if (data != null) {

                                    otp = data.getOtp();
                                }

                                Toast.makeText(PintActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                            } else {

                                String msg = response.body().getMessage();
                                Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Base> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(PintActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }


                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ProgressDialogUtils.dismiss();
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    private void VerifyOTPByEmail() {

        if (CommonUtils.isOnline(getApplication())) {
            try {

                ProgressDialogUtils.show(PintActivity.this);
                ForgotEmailModel forgotEmailModel = new ForgotEmailModel();
                forgotEmailModel.setEmail(email);
                forgotEmailModel.setOtp(Enterpin);
                Api api = RequestController.createService(Api.class);
                api.verifyOtp(forgotEmailModel).enqueue(new BaseCallback<Result>(getApplication()) {
                    @Override
                    public void onSuccess(Result response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {

                            if (response.getStatus() == 1) {
                                /* binding.pinPhone.setText(otp);*/
                                startActivity(new Intent(PintActivity.this, ChangePasswordActivity.class));
                                Toast.makeText(PintActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String msg = response.getMessage();
                                Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String msg = Objects.requireNonNull(response).getMessage();
                            Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }


                    @Override
                    public void onFail(Call<Result> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOtpOnEmail()
    {
        if (CommonUtils.isOnline(getApplication())) {
            try {

                ProgressDialogUtils.show(PintActivity.this);

                SignUpEmailData signUpEmailData=new SignUpEmailData();
                signUpEmailData.setEmail(signupEmail);

                Api api = RequestController.createService(Api.class);
                api.sendOtpByEmail(signUpEmailData).enqueue(new BaseCallback<BaseResponse>(getApplication()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if(response.getResponseCode()==200)

                            {

                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        } else {
                            String msg = Objects.requireNonNull(response).getMessage();
                            Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }



                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();

        }
    }






    public void verifyOtpOnEmail()
    {
        if (CommonUtils.isOnline(getApplication())) {
            try {

                ProgressDialogUtils.show(PintActivity.this);

                SingupVerifyModel signUpEmailData=new SingupVerifyModel();
                signUpEmailData.setEmail(signupEmail);
                signUpEmailData.setOtp(binding.pinPhone.getText().toString().trim());

                Api api = RequestController.createService(Api.class);
                api.verifyOtpByEmail(signUpEmailData).enqueue(new BaseCallback<BaseResponse>(getApplication()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if(response.getResponseCode()==200)

                            {

                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
                                    startActivity(intent);
                                    finishAffinity();


                            }
                            else {
                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        } else {
                            String msg = Objects.requireNonNull(response).getMessage();
                            Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }



                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();

        }
    }









    public void verifyOtpOnEmailPin()
    {
        if (CommonUtils.isOnline(getApplication())) {
            try {

                ProgressDialogUtils.show(PintActivity.this);

                SingupVerifyModel signUpEmailData=new SingupVerifyModel();
                signUpEmailData.setEmail(signupEmail);
                signUpEmailData.setOtp(binding.pinPhone.getText().toString().trim());

                Api api = RequestController.createService(Api.class);
                api.verifyOtpByEmail(signUpEmailData).enqueue(new BaseCallback<BaseResponse>(getApplication()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if(response.getResponseCode()==200)

                            {

                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
                                startActivity(intent);
                                finishAffinity();


                            }
                            else {
                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        } else {
                            String msg = Objects.requireNonNull(response).getMessage();
                            Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }



                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();

        }
    }




    public void verifyOtpOnEmaillogin()
    {
        if (CommonUtils.isOnline(getApplication())) {
            try {

                ProgressDialogUtils.show(PintActivity.this);

                SingupVerifyModel signUpEmailData=new SingupVerifyModel();
                signUpEmailData.setEmail(signupEmail);
                signUpEmailData.setOtp(binding.pinPhone.getText().toString().trim());

                Api api = RequestController.createService(Api.class);
                api.verifyOtpByEmail(signUpEmailData).enqueue(new BaseCallback<BaseResponse>(getApplication()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if(response.getResponseCode()==200)

                            {

                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
                                    startActivity(intent);
                                    finishAffinity();


                            }
                            else {
                                Toast.makeText(PintActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                            }



                        } else {
                            String msg = Objects.requireNonNull(response).getMessage();
                            Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }


                    }



                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();

        }
    }











    private void VerifyOTP() {
        if (CommonUtils.isOnline(getApplication())) {
            ProgressDialogUtils.show(PintActivity.this);

            PinRequest pinRequest = new PinRequest();
            pinRequest.setCountry_code(AppConstant.Country_Code);
            pinRequest.setUser_id(userid);
            pinRequest.setPhone_number(phone_num);
            pinRequest.setOtp(binding.pinPhone.getText().toString().trim());
            Api api = RequestController.createService(Api.class);
            api.getOtp(pinRequest).enqueue(new BaseCallback<OtpResult>(getApplication()) {
                @Override
                public void onSuccess(OtpResult response) {
                    ProgressDialogUtils.dismiss();
                    if (response != null) {

                        if (response.getStatus() == 1) {

                            if (from.equalsIgnoreCase(AppConstant.PROFILE_SCREEN)) {
                                finish();
                            } else {
                                Intent intent = new Intent(PintActivity.this, CreateOtpActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            Toast.makeText(PintActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();


                        } else {
                            String msg = response.getMessage();
                            Toast.makeText(PintActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String msg = Objects.requireNonNull(response).getMessage();
                        Toast.makeText(PintActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFail(Call<OtpResult> call, BaseResponse baseResponse) {
                    ProgressDialogUtils.dismiss();

                    String msg = baseResponse.getMessage();
                    Toast.makeText(PintActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkValidation() {
        Enterpin = binding.pinPhone.getText().toString();
        clearError();
        if (Enterpin.isEmpty()) {
            binding.tvPinError.setVisibility(View.VISIBLE);
            binding.tvPinError.setText(R.string.empty_pin);
            binding.tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        } else if (!(Enterpin.length() == 6)) {
            binding.tvPinError.setVisibility(View.VISIBLE);
            binding.tvPinError.setText(R.string.invalid_pin);
            binding.tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        }


        return true;
    }

    private void clearError() {
        binding.tvPinError.setVisibility(View.GONE);
    }


    @Override
    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
        this.verificationId = verificationId;
        this.resendToken = token;
        Toast.makeText(this, "OTP sent to your registered mobile number.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationFailed() {

        Toast.makeText(this, "Verification failed. Please verify again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationSuccess() {

        // Call API to change verification status

        FirebaseAuthentication.getInstance(this).signOut();
        VerifyOTP();


    }
}
