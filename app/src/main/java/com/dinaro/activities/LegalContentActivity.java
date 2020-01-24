package com.dinaro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dinaro.R;
import com.dinaro.databinding.ActivityLegalContentBinding;
import com.dinaro.models.RequestModel.legal.legalBase;
import com.dinaro.models.RequestModel.legal.legalData;
import com.dinaro.models.RequestModel.legal.legalResult;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LegalContentActivity extends AppCompatActivity {
    public ActivityLegalContentBinding binding;
    public ArrayList<legalData> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_legal_content);
        getLegalContent();

        binding.ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getLegalContent() {

        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(getApplication());

                Api api = RequestController.createService(Api.class);
                api.getLegalContent().enqueue(new Callback<legalBase>() {
                    @Override
                    public void onResponse(Call<legalBase> call, Response<legalBase> response) {
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                legalResult result = response.body().getResult();
                                data = (ArrayList<legalData>) result.getData();
                                if (data != null) {
                                    String description = data.get(0).getDescription();
                                    binding.legalWebView.loadData(description, "text/html", null);
                                    binding.legalWebView.setBackgroundColor(Color.TRANSPARENT);
                                } else {
                                    Toast.makeText(LegalContentActivity.this, "No content", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(LegalContentActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LegalContentActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<legalBase> call, Throwable t) {
                        String msg = t.getMessage();
                        Toast.makeText(LegalContentActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
