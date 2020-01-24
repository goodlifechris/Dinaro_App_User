package com.dinaro.fragments;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dinaro.R;
import com.dinaro.adapters.CommonAdapter;
import com.dinaro.adapters.FavouriteAdapter;
import com.dinaro.adapters.PayBillrvRestaurantAdapter;
import com.dinaro.adapters.PayBillrvUtilityAdapter;
import com.dinaro.databinding.FragmentPaybillBinding;
import com.dinaro.listners.FavouriteListener;
import com.dinaro.listners.MyItemOncClickListner;
import com.dinaro.models.RequestModel.addFavourite.favouriteBase;
import com.dinaro.models.RequestModel.payBill.Restaurant;
import com.dinaro.models.RequestModel.payBill.Utility;
import com.dinaro.models.RequestModel.payBill.payBillBase;
import com.dinaro.models.RequestModel.payBill.payBillData;
import com.dinaro.models.RequestModel.recipt.ReciptData;
import com.dinaro.models.RequestModel.recipt.ReciptResponse;
import com.dinaro.models.RequestModel.recipt.ReciptResult;
import com.dinaro.models.billpaymodel.BillPayDataResponseDto;
import com.dinaro.models.billpaymodel.BillPayResponseDto;
import com.dinaro.models.billpaymodel.CommonDataModel;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragementPayBill extends Fragment {

    private FragmentPaybillBinding binding;
    private Context context;

    private FavouriteListener favouriteListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userId, cardId, title, image, utilityId, restaurantId;
    private List<Utility> utilities = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();
    private PayBillrvRestaurantAdapter adapter;
    private PayBillrvUtilityAdapter payAdapter;
    private List<ReciptData> data = new ArrayList<>();
    private FavouriteAdapter favouriteAdapter;
    private int clickPosition = -1;
    private boolean isSearch;
    private String text;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paybill, container, false);
        context = getActivity();

        sharedPreferences = getActivity().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");

        setFavouriteAdapter();
        getFavourite();
        setUtilities();
        //setRestaurant();

        binding.etSearchPaybill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 text = s.toString();

                if (!(text.equalsIgnoreCase("") && text.isEmpty())) {
                    if(text.length()>=3)
                    {
                        searchCategory();
                    }
                } else {

                    setUtilities();
                }
            }

            private void searchCategory() {
                    if (CommonUtils.isOnline(context)) {
                        try {
                           // ProgressDialogUtils.show(context);

                            Api api = RequestController.createService(Api.class);
                            api.getSearch(userId,text).enqueue(new Callback<BillPayResponseDto>() {
                                @Override
                                public void onResponse(Call<BillPayResponseDto> call, Response<BillPayResponseDto> response) {
                                   // binding.swipeRefreshLayout.setRefreshing(false);
                                    BillPayResponseDto responseDto = response.body();
                                  //  ProgressDialogUtils.dismiss();
                                      if (response != null && response.body() != null) {
                                    if (response.body().getStatus() == 1) {

                                      //  Toast.makeText(context, "" + responseDto.getBillPayDataResponseDto().getArrayListMap().size(), Toast.LENGTH_SHORT).show();
                                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                                        binding.rvUtility.setLayoutManager(linearLayoutManager1);

                                        CommonAdapter commonAdapter = new CommonAdapter(context, responseDto.getBillPayDataResponseDto().getArrayListMap(),true );
                                        binding.rvUtility.setAdapter(commonAdapter);

                                    } else {
                                        String msg = response.body().getMessage();
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    }

                                    } else {
                                        Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<BillPayResponseDto> call, Throwable t) {
                                    //ProgressDialogUtils.dismiss();
                                    String msg = t.getMessage();
                                    Toast.makeText(context, "No category available", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
                    }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUtilities();
                getFavourite();
            }
        });
        return binding.getRoot();
    }


    private void setFavouriteAdapter() {


        favouriteAdapter = new FavouriteAdapter(getActivity(), data);
        binding.rvFavourite.setAdapter(favouriteAdapter);
        binding.rvFavourite.setHasFixedSize(true);
        binding.rvFavourite.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
    }
    private void getFavourite() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                Api api = RequestController.createService(Api.class);
                api.getRecipt(userId).enqueue(new Callback<ReciptResponse>() {
                    @Override
                    public void onResponse(Call<ReciptResponse> call, Response<ReciptResponse> response) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                ReciptResult payBillResult = response.body().getResult();

                                data.clear();
                                data.addAll(payBillResult.getData());
                                favouriteAdapter.notifyDataSetChanged();


                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReciptResponse> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                ProgressDialogUtils.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }
    private void setUtilities() {

        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                Api api = RequestController.createService(Api.class);
                api.getList(userId).enqueue(new Callback<BillPayResponseDto>() {
                    @Override
                    public void onResponse(Call<BillPayResponseDto> call, Response<BillPayResponseDto> response) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        BillPayResponseDto responseDto=response.body();
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                //Toast.makeText(context, ""+responseDto.getBillPayDataResponseDto().getArrayListMap().size(), Toast.LENGTH_SHORT).show();
                                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
                                binding.rvUtility.setLayoutManager(linearLayoutManager1);
                                CommonAdapter commonAdapter=new CommonAdapter(context,responseDto.getBillPayDataResponseDto().getArrayListMap(),false);
                                binding.rvUtility.setAdapter(commonAdapter);
                                commonAdapter.notifyDataSetChanged();

//                                payBillData payBillResult1 = response.body().getResult().getPayBillData();
//                                if (payBillResult1.getUtility() != null) {
//                                    utilities.clear();
//                                    utilities.addAll(payBillResult1.getUtility());
//                                }
//
//                                setUtilityAdapter();
//                                payAdapter.notifyDataSetChanged();


                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BillPayResponseDto> call, Throwable t) {
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


}





