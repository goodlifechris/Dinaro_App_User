package com.dinaro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinaro.R;
import com.dinaro.activities.AmountSendActivity;
import com.dinaro.activities.PayKPLC;
import com.dinaro.databinding.CardFavouriteBinding;
import com.dinaro.models.RequestModel.recipt.ReciptData;
import com.dinaro.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {

    public String type;
    private  Context mContext;
    private  FavouriteAdapter.MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    public List<ReciptData> data;
    private int size;

    public FavouriteAdapter(Activity activity, List<ReciptData> data) {

        this.data = data;
        this.mContext = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.card_favourite, parent, false);
        this.myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MyViewHolder holder, int position) {

        this.myViewHolder = holder;
        ReciptData getData = data.get(position);
        type = getData.getType();
        if(getData.getCardImage()!=null&&!getData.getCardImage().equalsIgnoreCase("")) {
            Picasso.with(mContext).load(getData.getCardImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(myViewHolder.cardFavouriteBinding.ivFavouriteCard);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardFavouriteBinding cardFavouriteBinding;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardFavouriteBinding = DataBindingUtil.bind(itemView);


            cardFavouriteBinding.layoutFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (data.get(getAdapterPosition()).getType().equals(AppConstant.TYPE_UTILITY)) {
                        Intent i = new Intent(mContext, PayKPLC.class);
                        i.putExtra("UtilityTitle", data.get(getAdapterPosition()).getCardTitle());
                        i.putExtra("UtilityImage", data.get(getAdapterPosition()).getCardImage());
                        i.putExtra("utilityId", data.get(getAdapterPosition()).getCardId());
                        mContext.startActivity(i);
                    } else if (data.get(getAdapterPosition()).getType().equals(AppConstant.TYPE_RESTAURANT)) {
                        Intent intent = new Intent(mContext, AmountSendActivity.class);
                        intent.putExtra("restaurantId", data.get(getAdapterPosition()).getCardId());
                        intent.putExtra("restaurantTitle", data.get(getAdapterPosition()).getCardTitle());
                        mContext.startActivity(intent);
                    }

                }
            });
        }
    }
}
