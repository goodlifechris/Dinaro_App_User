package com.dinaro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.databinding.ActivityChangePasswordBinding;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.ResetPasswordRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;

public class ChangePasswordActivity extends AppCompatActivity {
    private String password, ConfirmPassword, user_id;
    private ActivityChangePasswordBinding binding;
    public   SharedPreferences preferences;
    public SharedPreferences.Editor editor;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

         preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
       email= PrefManagerNew.getStringPreferences(this,AppConstant.EMAIL);
        binding.edtNewPassword.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});
        binding.edtConfirmPassword.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {

                    getNewPassword();

                }
            }
        });

    }

    private void getNewPassword() {

        if (CommonUtils.isOnline(this)) {
            try {
                ProgressDialogUtils.show(ChangePasswordActivity.this);
                user_id = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
                ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
                resetPasswordRequest.setCPassword(ConfirmPassword);
                resetPasswordRequest.setNewpassword(password);
                resetPasswordRequest.setUserId(email);

                Api api = RequestController.createService(Api.class);
                api.getNewPassword(resetPasswordRequest).enqueue(new BaseCallback<BaseResponse>(this) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if (response.getStatus() == 1) {

                                Toast.makeText(ChangePasswordActivity.this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("5", "gvyz");
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);


                            } else {
                                String msg = response.getMessage();
                                Toast.makeText(ChangePasswordActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, R.string.noResponse, Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFail(Call call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();
                        String msg = baseResponse.getMessage();
                        Toast.makeText(ChangePasswordActivity.this, "failure" + msg, Toast.LENGTH_SHORT).show();

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
        clearError();
        password = binding.edtNewPassword.getText().toString().trim();
        ConfirmPassword = binding.edtConfirmPassword.getText().toString().trim();

        if (password.isEmpty()) {
            binding.edtNewPassword.requestFocus();
            binding.tvPasswordError.setVisibility(View.VISIBLE);
            binding.tvPasswordError.setText(R.string.empty_password);
            return false;
        } else if (!(password.length() >= 6 && password.length() <= 16)) {
            binding.edtNewPassword.requestFocus();
            binding.tvPasswordError.setVisibility(View.VISIBLE);
            binding.tvPasswordError.setText(R.string.invalid_password);
            return false;

        } else if (ConfirmPassword.isEmpty()) {
            binding.edtConfirmPassword.requestFocus();
            binding.tvcPasswordError.setVisibility(View.VISIBLE);
            binding.tvcPasswordError.setText(R.string.empty_c_password);
            return false;
        } else if (!(ConfirmPassword.length() >= 6 && ConfirmPassword.length() <= 16)) {
            binding.edtConfirmPassword.requestFocus();
            binding.tvcPasswordError.setVisibility(View.VISIBLE);
            binding.tvcPasswordError.setText(R.string.invalid_c_password);
            return false;

        } else if (!ConfirmPassword.matches(password)) {
            binding.edtConfirmPassword.requestFocus();
            binding.tvcPasswordError.setVisibility(View.VISIBLE);
            binding.tvcPasswordError.setText(R.string.password_not_match);
            return false;
        } else {
            return true;

        }
    }


    private void clearError() {
        binding.tvcPasswordError.setVisibility(View.GONE);
        binding.tvPasswordError.setVisibility(View.GONE);
    }

}
