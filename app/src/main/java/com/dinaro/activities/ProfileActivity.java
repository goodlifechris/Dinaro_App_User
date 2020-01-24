package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dinaro.R;
import com.dinaro.databinding.ActivityProfileBinding;
import com.dinaro.models.RequestModel.getProfile.profileBase;
import com.dinaro.models.RequestModel.getProfile.profileData;
import com.dinaro.models.RequestModel.getProfile.profileResult;
import com.dinaro.models.RequestModel.updateProfile.updateBase;
import com.dinaro.models.RequestModel.updateProfile.updateData;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.UpdateProfileRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    public Context context;
    private String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ActivityProfileBinding binding;
    private String first_name, last_Name, middle_Name, Mobile_number, email, phone, userId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        context = ProfileActivity.this;

        sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        binding.edemail.setFilters( new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});

        getUserProfile();

        binding.ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    ChangeData();
                }

            }
        });

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserProfile();

            }
        });


    }

    private void getUserProfile() {


        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                Api api = RequestController.createService(Api.class);
                api.getUserProfile(userId).enqueue(new Callback<profileBase>() {
                    @Override
                    public void onResponse(Call<profileBase> call, Response<profileBase> response) {
                        binding.refreshLayout.setRefreshing(false);
                        ProgressDialogUtils.dismiss();

                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                profileResult result = response.body().getResult();
                                profileData data = result.getData();

                                first_name = data.getName();
                                last_Name = data.getLastName();
                                middle_Name = data.getMiddleName();
                                phone = data.getMobile();
                                email = data.getEmail();

                                if (data.getIsVerifiedMobile() == 0) {

                                    binding.edMobilenumber.setText(data.getMobile());
                                } else if (data.getIsVerifiedMobile() == 1) {
                                    binding.edMobilenumber.setText(data.getMobile());
                                }

                                binding.edemail.setText(data.getEmail());
                                binding.edMiddlename.setText(data.getMiddleName());
                                binding.edLastname.setText(data.getLastName());
                                binding.edFirstname.setText(data.getName());

                                String msg = response.body().getMessage();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<profileBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();

                        String msg = t.getMessage();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void ChangeData() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                updateProfileRequest.setUserId(userId);
                updateProfileRequest.setEmail(email.toLowerCase());
                updateProfileRequest.setfName(first_name);
                updateProfileRequest.setmName(middle_Name);
                updateProfileRequest.setlName(last_Name);
                updateProfileRequest.setMobile(Mobile_number);

                Api api = RequestController.createService(Api.class);
                api.updateProfile(updateProfileRequest).enqueue(new Callback<updateBase>() {
                    @Override
                    public void onResponse(Call<updateBase> call, Response<updateBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                updateData updateData = response.body().getResult().getData();


                                if (updateData.getIsVerifiedMobile() == 0) {
                                    Intent intent = new Intent(getApplication(), PintActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(AppConstant.FROM, AppConstant.PROFILE_SCREEN);
                                    intent.putExtras(bundle);
                                    intent.putExtra("phone", AppConstant.Country_Code + updateData.getMobile());
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();

                                }

                                getUserProfile();


                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<updateBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkValidation() {
        Mobile_number = binding.edMobilenumber.getText().toString();
        email = binding.edemail.getText().toString();
        first_name = binding.edFirstname.getText().toString();
        middle_Name = binding.edMiddlename.getText().toString();
        last_Name = binding.edLastname.getText().toString();

        String mobile_pattern = "((\\+*)((0[ -]+)*|(91)*(\\d{7,14}))|\\d{5}([- ]*)\\d{6})";
        if (first_name.length() == 0) {
            binding.edFirstname.requestFocus();
            binding.edFirstname.setError(getResources().getString(R.string.empty_first_name));
            return false;
        } else if (first_name.length() <= 2) {
            binding.edFirstname.requestFocus();
            binding.edFirstname.setError(getResources().getString(R.string.invalid_fname));
            return false;


        } else if (last_Name.length() == 0) {
            binding.edLastname.requestFocus();
            binding.edLastname.setError(getResources().getString(R.string.empty_last_name));
            return false;
        } else if (last_Name.length() <= 1) {
            binding.edLastname.requestFocus();
            binding.edLastname.setError(getResources().getString(R.string.invalid_fname));
            return false;


        } else if (Mobile_number.equalsIgnoreCase("")) {
            binding.edMobilenumber.requestFocus();
            binding.edMobilenumber.setError(getResources().getString(R.string.empty_phone));

            return false;
        }/* else if (!(Mobile_number.matches(mobile_pattern))) {
            binding.edMobilenumber.requestFocus();
            binding.edMobilenumber.setError(getResources().getString(R.string.invalid_mobile));

            return false;
        }*/ else if (email.length() == 0) {
            binding.edemail.requestFocus();
            binding.edemail.setError(getResources().getString(R.string.empty_email));
            return false;
        } else if (!(email.matches(email_pattern))) {
            binding.edemail.requestFocus();
            binding.edemail.setError(getResources().getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

}
