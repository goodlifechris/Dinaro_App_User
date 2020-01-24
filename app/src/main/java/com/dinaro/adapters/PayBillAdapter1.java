package com.dinaro.adapters;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinaro.R;
import com.dinaro.databinding.RowPaybillFirstBinding;
import com.dinaro.models.PayBillModel;

import java.util.List;

public class PayBillAdapter1 extends RecyclerView.Adapter<PayBillAdapter1.MyViewHolder> {

    private Context context;
    private List<PayBillModel> payBillModelList;

    public PayBillAdapter1(Context context, List<PayBillModel> payBillModelList) {
        this.context = context;
        this.payBillModelList = payBillModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_paybill_first, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (payBillModelList != null) {
            myViewHolder.binding.ivImage.setImageResource(payBillModelList.get(i).getImage());
        }

    }

    @Override
    public int getItemCount() {
        return payBillModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RowPaybillFirstBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
