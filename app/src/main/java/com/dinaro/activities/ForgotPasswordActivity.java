package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.models.RequestModel.ForgetPasswordEmail.Base;
import com.dinaro.models.RequestModel.ForgetPasswordEmail.Data;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.ForgetPasswordRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private String email_pattern = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    private EditText ed1;
    private String email;
    private String otp = "";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot_password);
        context = ForgotPasswordActivity.this;
        ed1 = findViewById(R.id.edtEmailMobile);
        Button btn1 = findViewById(R.id.fpContinue);
        btn1.setOnClickListener(this);
        ed1.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fpContinue:
                if (checkValidation()) {
                    getEmailApi();
                }
                break;
        }
    }

    private void getEmailApi() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
                forgetPasswordRequest.setEmail(email.toLowerCase());

                Api api = RequestController.createService(Api.class);
                api.getEmail(forgetPasswordRequest).enqueue(new Callback<Base>() {
                    @Override
                    public void onResponse(Call<Base> call, Response<Base> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if (response.body().getStatus() == 1) {
                                Data data = response.body().getResult().getData();
                                if (data != null) {
                                    otp = data.getOtp();
                                }
                                System.out.println("otp=" + otp);
                                PrefManagerNew.saveStringPreferences(context,AppConstant.EMAIL,email);
                                Intent intent = new Intent(ForgotPasswordActivity.this, PintActivity.class);
                                Bundle bundle = new Bundle();
                                intent.putExtra("email", "" + email);
                                bundle.putString(AppConstant.FROM, AppConstant.FORGOT_PASSWORD_ACTIVITY);
                                intent.putExtras(bundle);
                                intent.putExtra("otp", "" + otp);
                                startActivity(intent);
                                finish();
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                String msg = response.body().getMessage();
                                Toast.makeText(ForgotPasswordActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Base> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(ForgotPasswordActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

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

    private boolean checkValidation() {
        email = ed1.getText().toString();
        if (email.length() == 0) {
            ed1.requestFocus();
            ed1.setError(getResources().getString(R.string.empty_email));
            return false;
        } else if (!ValidationUtils.validEmail(email)) {
            ed1.requestFocus();
            ed1.setError(getResources().getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

    private boolean isValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
