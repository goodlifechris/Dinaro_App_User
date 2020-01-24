package com.dinaro.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dinaro.R;
import com.dinaro.activities.AmountSendActivity;
import com.dinaro.activities.DashBoardActivity;
import com.dinaro.activities.PayKPLC;
import com.dinaro.databinding.RowivPaybillSecondBinding;
import com.dinaro.listners.FavouriteListener;
import com.dinaro.listners.MyItemOncClickListner;
import com.dinaro.models.billpaymodel.CommonDataModel;
import com.dinaro.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PayBillrvUtilityAdapter extends RecyclerView.Adapter<PayBillrvUtilityAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private MyViewHolder myViewHolder;
    private List<CommonDataModel> utilityList;
    private MyItemOncClickListner listner;
    private FavouriteListener favouriteListener;
    private int size;
    private boolean isSearch;
    View view;
    public PayBillrvUtilityAdapter(Context activity, ArrayList<CommonDataModel> utilities, boolean isSearch) {
        this.context = activity;
        this.isSearch = isSearch;
        this.utilityList = utilities;
        this.listner = listner;
        this.inflater = LayoutInflater.from(context);
        size = CommonUtils.getWidth((DashBoardActivity) context) / 3;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.rowiv_paybill_second, parent, false);
        this.myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        this.myViewHolder = holder;
        myViewHolder.rowivPaybillSecondBinding.Image1.setVisibility(View.GONE);
        final CommonDataModel utility = utilityList.get(position);


        if (!isSearch) {
//            if (utilityList.get(position).getShow_on_app().equalsIgnoreCase("0")) {
//                view.setVisibility(View.GONE);
//                myViewHolder.rowivPaybillSecondBinding.llUtility.setVisibility(View.GONE);
//                myViewHolder.rowivPaybillSecondBinding.ivImageView.setVisibility(View.GONE);
//            } else {
                myViewHolder.rowivPaybillSecondBinding.llUtility.setVisibility(View.VISIBLE);
                myViewHolder.rowivPaybillSecondBinding.ivImageView.setVisibility(View.VISIBLE);
                myViewHolder.rowivPaybillSecondBinding.llUtility.setLayoutParams(new ViewGroup.LayoutParams(size, size));
                if (utility.getImage() != null && !utility.getImage().equalsIgnoreCase("")) {
                    Picasso.with(context).load(utility.getImage())
                            .error(R.drawable.placeholder)
                            .placeholder(R.drawable.placeholder)
                            .into(myViewHolder.rowivPaybillSecondBinding.ivImageView);
                }
           // }
        } else {

            myViewHolder.rowivPaybillSecondBinding.llUtility.setVisibility(View.VISIBLE);
            myViewHolder.rowivPaybillSecondBinding.ivImageView.setVisibility(View.VISIBLE);
            myViewHolder.rowivPaybillSecondBinding.llUtility.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            if (utility.getImage() != null && !utility.getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(utility.getImage())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(myViewHolder.rowivPaybillSecondBinding.ivImageView);
            }


        }

        /*if (utilityList.get(position).getShow_on_app().equalsIgnoreCase("0")) {
            myViewHolder.rowivPaybillSecondBinding.llUtility.setVisibility(View.GONE);
            myViewHolder.rowivPaybillSecondBinding.ivImageView.setVisibility(View.GONE);
        } else {
            myViewHolder.rowivPaybillSecondBinding.llUtility.setVisibility(View.VISIBLE);
            myViewHolder.rowivPaybillSecondBinding.ivImageView.setVisibility(View.VISIBLE);
            myViewHolder.rowivPaybillSecondBinding.llUtility.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            if (utility.getImage() != null && !utility.getImage().equalsIgnoreCase("")) {
                Picasso.with(context).load(utility.getImage())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(myViewHolder.rowivPaybillSecondBinding.ivImageView);
            }*/

            myViewHolder.rowivPaybillSecondBinding.ivImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accountType = utility.getAccountType();

                    if (accountType != null & accountType.equalsIgnoreCase("yes")) {
                        Intent intent = new Intent(context, PayKPLC.class);
                        intent.putExtra("Title", utility.getTitle());
                        intent.putExtra("Image", utility.getImage());
                        intent.putExtra("Id", utility.getId());
                        v.getContext().startActivity(intent);
                    } else /*if(accountType!=null &accountType.equalsIgnoreCase("no"))*/ {
                        Intent intent = new Intent(context, AmountSendActivity.class);
                        intent.putExtra("Title", utility.getTitle());
                        intent.putExtra("Image", utility.getImage());
                        intent.putExtra("Id", utility.getId());
                        v.getContext().startActivity(intent);
                    }


                }
            });
            myViewHolder.rowivPaybillSecondBinding.Image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onClick(v, position);

                }
            });
        }



    @Override
    public int getItemCount() {
        return utilityList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowivPaybillSecondBinding rowivPaybillSecondBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            rowivPaybillSecondBinding = DataBindingUtil.bind(itemView);
        }
    }
}
