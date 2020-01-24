package com.dinaro.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.SharePre;
import com.dinaro.adapters.RecyclerItemTouchHelper;
import com.dinaro.adapters.SaveCardAdapter;
import com.dinaro.databinding.ActivitySavedCard1Binding;
import com.dinaro.listners.OnCheckClickListener;
import com.dinaro.models.RequestModel.DeleteCard;
import com.dinaro.models.RequestModel.cardList.cardListBase;
import com.dinaro.models.RequestModel.cardList.cardListData;
import com.dinaro.models.RequestModel.cardList.cardListResult;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.paymentRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedCardActivity extends AppCompatActivity implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private String holder_number, transId;
    private String userId, msgNoData, cardId;
    private SaveCardAdapter saveCardAdapter;
    private ArrayList<cardListData> data = new ArrayList<>();
    private  String temp;
    public SwipeRefreshLayout layout;
    private Bundle bundle;
    private ActivitySavedCard1Binding binding;
    private ArrayList<Integer> selectCheck;
    private Context context;
    private paymentRequest paymentRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        temp = SharePre.getUserBuyerId(getApplication());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_card1);
        context = SavedCardActivity.this;
        paymentRequest = (paymentRequest) getIntent().getSerializableExtra("paymentRequest") ;

        sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");

        Intent intent = getIntent();
        holder_number = intent.getStringExtra("holder_number");
        transId = getIntent().getStringExtra("transId");

        if(getIntent().hasExtra("setting")){
            binding.layoutPay.setVisibility(View.GONE);
        }

        binding.ivBack.setOnClickListener(this);
        binding.ivAddcard.setOnClickListener(this);
        binding.btnPayAddCard.setOnClickListener(this);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSavedCard();
            }
        });

    }

    @Override
    protected void onResume() {
        getSavedCard();
        getAdapter();
        super.onResume();
    }

    @SuppressLint("WrongConstant")
    private void getAdapter() {

        saveCardAdapter = new SaveCardAdapter(getApplication(), data, new OnCheckClickListener() {
            @Override
            public void onClick(View view, int position, boolean checked) {
                if (data.get(position).isSelected()) {
                    data.get(position).setSelected(false);
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setSelected(false);
                    }
                    data.get(position).setSelected(true);
                    if(paymentRequest!=null)
                    paymentRequest.setPaymentType(data.get(position).getCardType());
                }
                saveCardAdapter.notifyDataSetChanged();
            }
        });

        binding.rvSavedCard.setAdapter(saveCardAdapter);
        binding.rvSavedCard.setHasFixedSize(true);
        binding.rvSavedCard.setLayoutManager(new LinearLayoutManager(getApplication(),
                LinearLayoutManager.VERTICAL, false));
        binding.rvSavedCard.setItemAnimator(new DefaultItemAnimator());
        binding.rvSavedCard.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedCard);


    }


    private void getSavedCard() {

        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(context);


                Api api = RequestController.createService(Api.class);
                api.getCards(userId).enqueue(new Callback<cardListBase>() {
                    @Override
                    public void onResponse(Call<cardListBase> call, Response<cardListBase> response) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {


                                String msg = response.body().getMessage();

                                binding.layoutSavedCard.setVisibility(View.VISIBLE);
                                binding.layoutNoSavedCard.setVisibility(View.GONE);

                                cardListResult result = response.body().getResult();
                                cardId = result.getData().get(0).getCardId();

                                data.clear();
                                data.addAll(result.getData());
                                saveCardAdapter.notifyDataSetChanged();


                            } else {
                                binding.layoutSavedCard.setVisibility(View.GONE);
                                binding.layoutNoSavedCard.setVisibility(View.VISIBLE);
                                data.clear();
                                saveCardAdapter.notifyDataSetChanged();
                                msgNoData = response.body().getMessage();
                                Toast.makeText(SavedCardActivity.this, msgNoData, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SavedCardActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<cardListBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(SavedCardActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SaveCardAdapter.MyViewHolder) {

            deleteCard();

        }
    }

    private void deleteCard() {

        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(getApplication());


                Api api = RequestController.createService(Api.class);
                api.deleteCards(userId, cardId).enqueue(new Callback<DeleteCard>() {
                    @Override
                    public void onResponse(Call<DeleteCard> call, Response<DeleteCard> response) {
                        binding.swipeRefreshLayout.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                Toast.makeText(SavedCardActivity.this, "Card deleted", Toast.LENGTH_SHORT).show();
                                getSavedCard();

                            } else {

                                Toast.makeText(SavedCardActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SavedCardActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<DeleteCard> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(SavedCardActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isCardChosen() {
        boolean checked = false;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                checked = true;
                break;
            } else {
                checked = false;
            }
        }
        return checked;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.iv_addcard:
                startActivity(new Intent(SavedCardActivity.this, AddCardActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_pay_addCard:
                if (isCardChosen()) {
                    bundle = getIntent().getExtras();
                    if (bundle != null) {
                        if (bundle.containsKey("payment")) {


                            submitData();
                           /* Intent intent = new Intent(SavedCardActivity.this, PaymentSuccessActivity.class);
                            intent.putExtra("TransactionId", transId);
                            startActivity(intent);*/

                        } else if (bundle.containsKey("setting")) {
                            Toast.makeText(SavedCardActivity.this, "Please make some Payment first.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Please select card.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;


        }
    }


    private void submitData() {
        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(SavedCardActivity.this);
/*

                paymentRequest.setCardId(cardId);
                paymentRequest.setUserId(userId);
*/
                paymentRequest.setPaymentId(""+System.currentTimeMillis());

                Api api = RequestController.createService(Api.class);
                api.setPaymentDetails(paymentRequest).enqueue(new Callback<DetailsBase>() {
                    @Override
                    public void onResponse(Call<DetailsBase> call, Response<DetailsBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                String id = response.body().getResult().getData().getId();

                                String msg = response.body().getMessage();
                                Intent intent = new Intent(SavedCardActivity.this, PaymentSuccessActivity.class);
                                intent.putExtra("TransactionId", id);
                                System.out.println("transaction id: "+id);

                                startActivity(intent);

                            } else {
                                String msg = response.body().getMessage();
                                 new Intent(SavedCardActivity.this, PaymentFailedActivity.class);
//                                Toast.makeText(SavedCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SavedCardActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        new Intent(SavedCardActivity.this, PaymentFailedActivity.class);
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
