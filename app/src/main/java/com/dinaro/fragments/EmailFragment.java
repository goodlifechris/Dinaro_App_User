package com.dinaro.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.activities.DashBoardActivity;
import com.dinaro.activities.LoginActivity;
import com.dinaro.activities.PintActivity;
import com.dinaro.databinding.FragmentEmailBinding;
import com.dinaro.models.RequestModel.UserModel;
import com.dinaro.models.RequestModel.login.Data;
import com.dinaro.models.RequestModel.login.User;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.LoginRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import java.util.Objects;

import retrofit2.Call;

public class EmailFragment extends Fragment implements View.OnClickListener {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private  String passwordPattern = "^([a-zA-Z0-9@*#]{7,15})$";
    private Context context;
    private FragmentEmailBinding binding;
    private String email, password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false);
        context=getActivity();
        sharedPreferences = context.getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {

        binding.etPassword.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});
        binding.etEmail.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});

        ((LoginActivity)getActivity()).binding.btnfLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnfLogin:

                if (checkValidation()) {
                    getLoginData();
                }
                break;


        }


    }


    private void getLoginData() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(email.toLowerCase());
                loginRequest.setPassword(password);
                loginRequest.setType(AppConstant.TYPE_EMAIL);
                loginRequest.setDeviceType(AppConstant.Device_Type);
                loginRequest.setDeviceToken(PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN));
                Api api = RequestController.createService(Api.class);
                api.getLoginDataByEmail(loginRequest).enqueue(new BaseCallback<User>((AppCompatActivity) context) {
                    @Override
                    public void onSuccess(User response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if (response.getStatus() == 1) {

                                Data data = response.getResult().getData();
                                String userId = data.getId();
                                String email = data.getEmail();
                                System.out.println("user id is=" + userId);

                                sharedPreferences.edit().putString(AppConstant.USER_ID_BY_SIGNUP, userId).apply();
                                sharedPreferences.edit().putString(AppConstant.EMAIL, email).apply();
                                sharedPreferences.edit().putString(AppConstant.MOBILE, data.getMobile()).apply();
                                sharedPreferences.edit().putString(AppConstant.PIN, data.getPin()).apply();
                                PrefManagerNew.saveStringPreferences(context,AppConstant.EMAIL,email);
                                sharedPreferences.edit().putInt(AppConstant.VERIFIED_TYPE,data.getIsVerifiedMobile()).apply();
                                PrefManagerNew.saveStringPreferences(context,AppConstant.MOBILE,data.getMobile());
                                if (data.getIsVerifiedMobile() == 1) {

                                    Intent intent = new Intent(context, DashBoardActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("3","second fragment");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    ((AppCompatActivity) context).finishAffinity();

                                } else {
                                    Intent intent = new Intent(context, PintActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstant.FROM, AppConstant.LOGIN_ACTIVITY);

                                    intent.putExtras(bundle);
                                    intent.putExtra("phone","" + AppConstant.Country_Code +data.getMobile());
                                    startActivity(intent);
                                }

                                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                ProgressDialogUtils.dismiss();

                                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            ProgressDialogUtils.dismiss();

                            Toast.makeText(context, "" + Objects.requireNonNull(response).getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFail(Call<User> call, BaseResponse baseResponse) {

                        if (baseResponse != null) {
                            String msg = baseResponse.getMessage();

                            Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();
                        } else {
                             Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                        }

                        ProgressDialogUtils.dismiss();
                    }
                });

            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            ProgressDialogUtils.dismiss();

            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    private UserModel createUserModel(Data data) {
        UserModel userModel = new UserModel();
        userModel.setId(data.getId());

        return userModel;
    }

    private boolean checkValidation() {
        clearError();
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            binding.etEmail.requestFocus();
            binding.tvEmailError.setVisibility(View.VISIBLE);
            binding.tvEmailError.setText(R.string.empty_email);
            return false;
        } else if (!ValidationUtils.validEmail(email)) {
            binding.etEmail.requestFocus();
            binding.tvEmailError.setVisibility(View.VISIBLE);
            binding.tvEmailError.setText(R.string.invalid_email);

            return false;
        } else if (password.isEmpty()) {
            binding.etPassword.requestFocus();
            binding.tvPasswordError.setVisibility(View.VISIBLE);
            binding.tvPasswordError.setText(R.string.empty_password);
            return false;
        } else if (!(password.length() >= 6 && password.length() <= 16)) {
            binding.etPassword.requestFocus();
            binding.tvPasswordError.setVisibility(View.VISIBLE);
            binding.tvPasswordError.setText(R.string.invalid_password);
            return false;

        } else {
            return true;

        }
    }

    private void clearError() {
        binding.tvEmailError.setVisibility(View.GONE);
        binding.tvPasswordError.setVisibility(View.GONE);
    }


}
