package com.dinaro.activities;

import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.adapters.FAQAdapter;
import com.dinaro.databinding.ActivityFaqBinding;
import com.dinaro.models.RequestModel.fAQ.FaqBase;
import com.dinaro.models.RequestModel.fAQ.FaqData;
import com.dinaro.models.RequestModel.fAQ.FaqResult;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQActivity extends AppCompatActivity {
    private ActivityFaqBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_faq);

        getQuestionAnswer();
        binding.ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuestionAnswer();
            }
        });

    }

    private void getQuestionAnswer() {

        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(getApplication());
                Api api = RequestController.createService(Api.class);
                api.getQuestions().enqueue(new Callback<FaqBase>() {
                    @Override
                    public void onResponse(Call<FaqBase> call, Response<FaqBase> response) {
                        binding.refreshLayout.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                FaqResult faqResult = response.body().getResult();
                                ArrayList<FaqData> faqData = (ArrayList<FaqData>) faqResult.getData();

                                FAQAdapter faqAdapter = new FAQAdapter(getApplicationContext(), faqData);
                                binding.faqRecyclerView.setAdapter(faqAdapter);
                                binding.faqRecyclerView.setHasFixedSize(true);
                                binding.faqRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication(),
                                        LinearLayoutManager.VERTICAL, false));

                                Toast.makeText(FAQActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                ProgressDialogUtils.dismiss();

                                String msg = response.body().getMessage();
                                Toast.makeText(FAQActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FAQActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FaqBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();

                        String msg = t.getMessage();
                        Toast.makeText(FAQActivity.this, msg, Toast.LENGTH_SHORT).show();

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
