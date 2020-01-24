package com.dinaro.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dinaro.R;
import com.dinaro.activities.ReceiptActivity;
import com.dinaro.activities.RecieptActivityRestaurant;

import com.dinaro.models.RequestModel.resentActivity.ResentData;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResentAdapter extends RecyclerView.Adapter<ResentAdapter.MyViewHolder> {
    private Context context;
    private  LayoutInflater inflater;
    private  ResentAdapter.MyViewHolder myViewHolder;
    private List<ResentData> list;
    private String lastDate;


    public ResentAdapter(Activity activity, List<ResentData> utilities) {
        this.context = activity;
        this.list = utilities;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ResentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recent_linear_layout_activity, viewGroup, false);
        this.myViewHolder = new ResentAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResentAdapter.MyViewHolder myViewHolder, int i) {
        this.myViewHolder = myViewHolder;
        final ResentData resentData = list.get(i);
        final String type=resentData.getAccountType();

        myViewHolder.tv_viewreciept.setText("View receipt");
        myViewHolder.tv_kes.setText("~ KES "+resentData.getTransactionAmount());
        myViewHolder.tv_payment.setText("Completed");
        myViewHolder.tv_zuku.setText(resentData.getCardTitle());
        if(resentData.getCardImage()!=null&&!resentData.getCardImage().equalsIgnoreCase("")) {
            Picasso.with(context).load(resentData.getCardImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(myViewHolder.iv_zuku);
            myViewHolder.setHeader();
        }


        myViewHolder.tv_viewreciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type != null && type.equals(AppConstant.ACCOUNT_TYPE_NO)) {

                    Intent intent = new Intent(context, RecieptActivityRestaurant.class);
                    intent.putExtra("title_res", resentData.getCardTitle());
                    intent.putExtra("date_res", resentData.getCreatedAt());
                    intent.putExtra("amount_res", resentData.getTransactionAmount());
                    intent.putExtra("image_res", resentData.getCardImage());
                    intent.putExtra("transaction_id_res", resentData.getTransactionId());
                    intent.putExtra("rating_res", resentData.getRating());
                    intent.putExtra("payment_type", resentData.getPaymentType());
                    view.getContext().startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, ReceiptActivity.class);
                    intent.putExtra("title_utility", resentData.getCardTitle());
                    intent.putExtra("date_utility", resentData.getCreatedAt());
                    intent.putExtra("name_utility", resentData.getConsumerName());
                    intent.putExtra("amount_utility", resentData.getTransactionAmount());
                    intent.putExtra("image_utility", resentData.getCardImage());
                    intent.putExtra("bill_number_utility", resentData.getBillNumber());
                    intent.putExtra("transaction_id_utility", resentData.getTransactionId());
                    intent.putExtra("rating_utility", resentData.getRating());
                    intent.putExtra("payment_type", resentData.getPaymentType());
                    view.getContext().startActivity(intent);

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainll;

        ImageView iv_zuku;

        LinearLayout llfield;
        TextView tv_zuku;
        TextView tv_payment;
        LinearLayout llfield1;
        TextView tv_kes;
        TextView tv_viewreciept;
        TextView tvDate;
        ResentData resentData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainll = itemView.findViewById(R.id.mainll);
            iv_zuku = itemView.findViewById(R.id.iv_zuku);
            llfield = itemView.findViewById(R.id.llfield);
            tv_zuku = itemView.findViewById(R.id.tv_zuku);
            tv_payment = itemView.findViewById(R.id.tv_payment);
            llfield1 = itemView.findViewById(R.id.llfield1);
            tv_kes = itemView.findViewById(R.id.tv_kes);
            tv_viewreciept = itemView.findViewById(R.id.tv_viewreciept);
            tvDate = itemView.findViewById(R.id.tvDate);

        }


        public void setHeader(){

            if(list.get(getAdapterPosition()).isHeader()){
                myViewHolder.tvDate.setText(CommonUtils.getFormattedDate(list.get(getAdapterPosition()).getCreatedAt().split(" ")[0]));
                myViewHolder.tvDate.setVisibility(View.VISIBLE);
            }else{
                myViewHolder.tvDate.setVisibility(View.GONE);
            }
        }
    }
}
