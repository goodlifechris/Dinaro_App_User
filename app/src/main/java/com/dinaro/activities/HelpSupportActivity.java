package com.dinaro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.databinding.ActivityHelpSupportBinding;
import com.dinaro.models.RequestModel.helpSupport.HelpResult;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.HelpSupportRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;

public class HelpSupportActivity extends AppCompatActivity {

    private ActivityHelpSupportBinding binding;
    private String subject, description, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_help_support);


        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPoint()) {



                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@dinaropay.com"});

                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, description);

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        finish();

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(HelpSupportActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }



                    //setData();
                //}


            }
        });


        binding.arrowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setData() {
        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(getApplication());

                HelpSupportRequest helpSupportRequest = new HelpSupportRequest();
                helpSupportRequest.setDescription(description);
                helpSupportRequest.setSubject(subject);
                helpSupportRequest.setUserid(userId);

                Api api = RequestController.createService(Api.class);
                api.setData(helpSupportRequest).enqueue(new BaseCallback<HelpResult>(getApplication()) {
                    @Override
                    public void onSuccess(HelpResult response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null) {
                            if (response.getStatus() == 1) {
                                String msg = response.getMessage();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                finish();

                            } else {
                                ProgressDialogUtils.dismiss();
                                String msg = response.getMessage();
                                Toast.makeText(HelpSupportActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HelpSupportActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFail(Call<HelpResult> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(HelpSupportActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean checkPoint() {
        subject = binding.edSubject.getText().toString().trim();
        description = binding.edDescription.getText().toString().trim();
        if (subject.isEmpty()) {
            binding.edSubject.requestFocus();
            binding.edSubject.setError("*Please enter subject.");
            return false;
        }
        else if(subject.length()<=3)
        {
            binding.edSubject.requestFocus();
            binding.edSubject.setError("*Please enter valid subject.");
            return false;

        }
        else if (description.isEmpty()) {
            binding.edDescription.requestFocus();
            binding.edDescription.setError("*Please enter description.");
            return false;
        }
        else if(description.length()<=5)
        {
            binding.edDescription.requestFocus();
            binding.edDescription.setError("*Please enter valid description.");
            return false;

        }

        return true;

    }


}
