package com.dinaro.fragments;


import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dinaro.R;
import com.dinaro.activities.DashBoardActivity;
import com.dinaro.activities.PaymentMethodActivity;
import com.dinaro.adapters.ResentAdapter;
import com.dinaro.databinding.FragmentRecentBinding;
import com.dinaro.models.RequestModel.resentActivity.RecentBase;
import com.dinaro.models.RequestModel.resentActivity.RecentResult;
import com.dinaro.models.RequestModel.resentActivity.ResentData;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentRecent extends Fragment implements View.OnClickListener {


    public TextView tvCurrentDate;
    private String date_n = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
    private RecyclerView rv;
    private Context context;
    private View view;
    private FragmentRecentBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userId;
    private ResentAdapter resentAdapter;
    private List<ResentData> recent = new ArrayList<>();

    private HashMap<String, ArrayList> myTransactionData = new HashMap<>();


    public FragmentRecent() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent, container, false);
        context = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");


        binding.tvCurrentDate.setText(date_n);


        setListener();
        setRecentAdapter();
        setResentActivity();
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setResentActivity();
            }
        });

        return binding.getRoot();
    }


    @SuppressLint("WrongConstant")
    private void setRecentAdapter() {
        resentAdapter = new ResentAdapter(getActivity(), recent);

        binding.rvRecent.setAdapter(resentAdapter);
        binding.rvRecent.setHasFixedSize(true);
        binding.rvRecent.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
    }

    private void setResentActivity() {
        if (CommonUtils.isOnline(getActivity())) {
            try {
                ProgressDialogUtils.show(getActivity());
                Api api = RequestController.createService(Api.class);
                api.getPaymentList(userId).enqueue(new Callback<RecentBase>() {
                    @Override
                    public void onResponse(Call<RecentBase> call, Response<RecentBase> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                binding.layoutActivvityAdded.setVisibility(View.VISIBLE);
                                binding.layoutNoActivity.setVisibility(View.GONE);
                                RecentResult result = response.body().getResult();
                                recent.clear();
                                recent.addAll(result.getData());
                                for (int i = 0; i < recent.size(); i++) {
                                    recent.get(i).setmDate(formateDate(recent.get(i).getCreatedAt()));
                                    recent.get(i).setmDay(formateDate1(recent.get(i).getCreatedAt()));
                                    recent.get(i).setmMonth(formateDate2(recent.get(i).getCreatedAt()));
                                }

                                resentAdapter.notifyDataSetChanged();
                                setHeader();

                            } else {
                                binding.layoutActivvityAdded.setVisibility(View.GONE);
                                binding.layoutNoActivity.setVisibility(View.VISIBLE);
                                recent.clear();
                                resentAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    private String formateDate(String date) {
                        String newstr = date;
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(format1.parse(newstr));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return format.format(c.getTime());
                    }

                    private String formateDate1(String date) {
                        String newstr = date;
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat format = new SimpleDateFormat("dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(format1.parse(newstr));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return format.format(c.getTime());
                    }

                    private String formateDate2(String date){ //Added on 19August
                            String newstr = date;
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat format = new SimpleDateFormat("MM");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(format1.parse(newstr));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return format.format(c.getTime());
                    }



                    @Override
                    public void onFailure(Call<RecentBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }


    private void setListener() {

        binding.ivCalender.setOnClickListener(this);
        binding.tvAddPayment.setOnClickListener(this);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.CALENDER_DATA, myTransactionData);

        fragment.setArguments(bundle);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCalender:

                loadFragment(new CalanderFragment());


                //  startActivity(new Intent(context, ActivityCalender.class).putExtra(AppConstant.CALENDER_DATA, myTransactionData));
                break;
            case R.id.tv_addPayment:
                // ((DashBoardActivity) getActivity()).openPayBillFragment();


                Intent intent = new Intent(context, PaymentMethodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.FROM, "FragmentRecent");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            default:
                break;

        }
    }


    public void setHeader() {
        Collections.sort(recent, new Comparator<ResentData>() {
            @Override
            public int compare(ResentData resentData, ResentData t1) {

                return t1.getCreatedAt().compareTo(resentData.getCreatedAt());

            }
        });

        myTransactionData.clear();
        String lastDate = "";
        ArrayList<ResentData> mTemp = null;
        for (int i = 0; i < recent.size(); i++) {
            if (!lastDate.equalsIgnoreCase(recent.get(i).getCreatedAt().split(" ")[0])) {
                lastDate = recent.get(i).getCreatedAt().split(" ")[0];
                recent.get(i).setHeader(true);
                mTemp = new ArrayList<>();
                //mTemp.add(recent.get(i));
            } else {
                recent.get(i).setHeader(false);
            }

            if (mTemp != null) {
                mTemp.add(recent.get(i));
                myTransactionData.put(recent.get(i).getmDate(), mTemp);
            }
        }

    }
}

