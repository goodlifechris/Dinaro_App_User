package com.dinaro.activities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.DataBindingUtil;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.databinding.ActivityNotificationBinding;
import com.dinaro.models.RequestModel.getProfile.profileBase;
import com.dinaro.models.RequestModel.notification.Data;
import com.dinaro.models.RequestModel.notification.GetDataModel;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userId = "";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        sharedPreferences = getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        getNotificationStatus();
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.success);
        sound.start();

        binding.checkSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });
        binding.checkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });
        binding.ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void updateStatus() {


        if (CommonUtils.isOnline(getApplication())) {
            int sendSuccess = 1, sendFail;
            if (binding.checkSuccess.isChecked()) {
                sendSuccess = 1;
            } else {
                sendSuccess = 0;

            }
            if (binding.checkFail.isChecked()) {
                sendFail = 1;
            } else {
                sendFail = 0;
            }


            try {
                ProgressDialogUtils.show(getApplication());

                Api api = RequestController.createService(Api.class);
                api.updateStatus(userId, sendSuccess, sendFail).enqueue(new Callback<profileBase>() {
                    @Override
                    public void onResponse(Call<profileBase> call, Response<profileBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            String msg = response.body().getMessage();
                            if (response.body().getStatus() == 1) {

                                Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NotificationActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<profileBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(NotificationActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void getNotificationStatus() {


        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(getApplication());

                Api api = RequestController.createService(Api.class);
                api.getNotificationStatus(userId).enqueue(new Callback<GetDataModel>() {
                    @Override
                    public void onResponse(Call<GetDataModel> call, Response<GetDataModel> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            String msg = response.body().getMessage();
                            if (response.body().getStatus() == 1) {
                                Data data = response.body().getResult().getData();
                                if (data.getSendFailStatus() != null && data.getSendFailStatus().equalsIgnoreCase("1")) {
                                    binding.checkFail.setChecked(true);
                                } else {
                                    binding.checkFail.setChecked(false);
                                }
                                if (data.getSendSuccessStatus() != null && data.getSendSuccessStatus().equalsIgnoreCase("1")) {
                                    binding.checkSuccess.setChecked(true);
                                } else {
                                    binding.checkSuccess.setChecked(false);
                                }


                                Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(NotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(NotificationActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDataModel> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(NotificationActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }


}
