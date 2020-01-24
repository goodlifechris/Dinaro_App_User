package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.databinding.ActivityResetPasswordBinding;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.changePasswordRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityResetPasswordBinding binding;
    private String newpassword;
    private String oldpassword;
    private String userId;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        userId = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        System.out.println(userId);
        setFilter();

        binding.btnRpcontinue.setOnClickListener(this);
        binding.ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setFilter() {
        binding.edtoldpassword.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});
        binding.edtNewpassword.setFilters(new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(16)});

        binding.edtConfirmpassword.setFilters(new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(16)});
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Rpcontinue:
                if (checkValidation()) {

                    setNewPassword();
                }
                break;
        }
    }

    private void setNewPassword() {

        if (CommonUtils.isOnline(this)) {
            try {
                ProgressDialogUtils.show(ResetPasswordActivity.this);

                changePasswordRequest changePasswordRequest = new changePasswordRequest();
                changePasswordRequest.setNewpassword(newpassword);
                changePasswordRequest.setOldPassword(oldpassword);
                changePasswordRequest.setUserId(userId);

                Api api = RequestController.createService(Api.class);
                api.setNewPassword(changePasswordRequest).enqueue(new BaseCallback<BaseResponse>(getApplication()) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if (response.getStatus() == 1) {

                                Intent i = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                Toast.makeText(getApplication(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                                Bundle bundle = new Bundle();
                                bundle.putString("2", "xyz");
                                i.putExtras(bundle);
                                startActivity(i);
                                finish();


                            } else {
                                String msg = response.getMessage();
                                Toast.makeText(ResetPasswordActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, R.string.noResponse, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();
                        String msg = baseResponse.getMessage();
                        Toast.makeText(ResetPasswordActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

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
        oldpassword = binding.edtoldpassword.getText().toString().trim();
        newpassword = binding.edtNewpassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmpassword.getText().toString().trim();

        if (oldpassword.isEmpty()) {
            binding.edtoldpassword.requestFocus();
            binding.tvOldPasswordError.setVisibility(View.VISIBLE);
            binding.tvOldPasswordError.setText(R.string.empty_password);
            return false;
        } else if (!(oldpassword.length() >= 6 && oldpassword.length() <= 16)) {
            binding.edtoldpassword.requestFocus();
            binding.tvOldPasswordError.setVisibility(View.VISIBLE);
            binding.tvOldPasswordError.setText(R.string.invalid_password);
            return false;

        } else if (newpassword.isEmpty()) {

            binding.edtNewpassword.requestFocus();
            binding.tvNewPasswordError.setVisibility(View.VISIBLE);
            binding.tvNewPasswordError.setText(R.string.empty_password);
            return false;

        } else if (!(newpassword.length() >= 6 && newpassword.length() <= 16)) {
            binding.edtNewpassword.requestFocus();
            binding.tvNewPasswordError.setVisibility(View.VISIBLE);
            binding.tvNewPasswordError.setText(R.string.invalid_password);
            return false;
        } else if (confirmPassword.isEmpty()) {
            binding.edtConfirmpassword.requestFocus();
            binding.tvCPasswordError.setVisibility(View.VISIBLE);
            binding.tvCPasswordError.setText(R.string.empty_c_password);
            return false;
        } else if (!(confirmPassword.length() >= 6 && confirmPassword.length() <= 16)) {
            binding.edtConfirmpassword.requestFocus();
            binding.tvCPasswordError.setVisibility(View.VISIBLE);
            binding.tvCPasswordError.setText(R.string.invalid_c_password);
            return false;

        } else if (!confirmPassword.matches(newpassword)) {
            binding.edtConfirmpassword.requestFocus();
            binding.tvCPasswordError.setVisibility(View.VISIBLE);
            binding.tvCPasswordError.setText(R.string.password_not_match);
            return false;
        } else {
            return true;

        }
    }


    private void clearError() {
        binding.tvCPasswordError.setVisibility(View.GONE);
        binding.tvNewPasswordError.setVisibility(View.GONE);
        binding.tvOldPasswordError.setVisibility(View.GONE);
    }

}
