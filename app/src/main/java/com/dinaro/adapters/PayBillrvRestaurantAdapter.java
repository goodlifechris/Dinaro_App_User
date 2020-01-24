package com.dinaro.adapters;

import android.app.Activity;
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
import com.dinaro.databinding.RowivPaybillSecondBinding;
import com.dinaro.listners.FavouriteListener;
import com.dinaro.listners.MyItemOncClickListner;
import com.dinaro.models.RequestModel.payBill.Restaurant;
import com.dinaro.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PayBillrvRestaurantAdapter extends RecyclerView.Adapter<PayBillrvRestaurantAdapter.MyViewHolder> {

    public List<Restaurant> restaurantList;
    private Context context;
    private LayoutInflater inflater;
    private PayBillrvRestaurantAdapter.MyViewHolder myViewHolder;
    private MyItemOncClickListner listner;
    private FavouriteListener favouriteListener;
    private int size;

    public PayBillrvRestaurantAdapter(Activity activity, List<Restaurant> restaurants, MyItemOncClickListner listner) {
        this.context = activity;
        this.restaurantList = restaurants;
        this.inflater = LayoutInflater.from(context);
        this.listner = listner;
        size = CommonUtils.getWidth(activity) / 3;
    }

    @NonNull
    @Override
    public PayBillrvRestaurantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rowiv_paybill_second, parent, false);
        this.myViewHolder = new PayBillrvRestaurantAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayBillrvRestaurantAdapter.MyViewHolder holder, final int position) {
        this.myViewHolder = holder;
        final Restaurant restaurant = restaurantList.get(position);
        myViewHolder.rowivPaybillSecondBinding.llUtility.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        if (restaurantList.get(position).isSelected()) {
            myViewHolder.rowivPaybillSecondBinding.Image1.setImageResource(R.drawable.path_267);
        } else {
            myViewHolder.rowivPaybillSecondBinding.Image1.setImageResource(R.drawable.path_267_copy);

        }


            if (restaurant.getImage() != null && !restaurant.getImage().equalsIgnoreCase("")) {

                Picasso.with(context).load(restaurant.getImage())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder).
                        into(myViewHolder.rowivPaybillSecondBinding.ivImageView);
            }



        myViewHolder.rowivPaybillSecondBinding.Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(v, position);
            }
        });
        myViewHolder.rowivPaybillSecondBinding.ivImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AmountSendActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                intent.putExtra("restaurantTitle", restaurant.getTitle());
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return restaurantList.size();
    }

    public void updatedList(List<Restaurant> filteresNames) {
        restaurantList = filteresNames;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowivPaybillSecondBinding rowivPaybillSecondBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            rowivPaybillSecondBinding = DataBindingUtil.bind(itemView);
        }
    }
}
