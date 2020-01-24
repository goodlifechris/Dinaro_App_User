package com.dinaro.adapters;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinaro.R;
import com.dinaro.databinding.CardSavedBinding;
import com.dinaro.listners.OnCheckClickListener;
import com.dinaro.models.RequestModel.cardList.cardListData;
import com.dinaro.utils.CardType;

import java.util.ArrayList;
import java.util.List;

public class SaveCardAdapter extends RecyclerView.Adapter<SaveCardAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private  SaveCardAdapter.MyViewHolder myViewHolder;
    private List<cardListData> data;
    private OnCheckClickListener listener;


    public SaveCardAdapter(Context context, ArrayList<cardListData> data, OnCheckClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public SaveCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_saved, parent, false);
        this.myViewHolder = new SaveCardAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SaveCardAdapter.MyViewHolder holder, final int position) {
        this.myViewHolder = holder;
        final cardListData data1 = data.get(position);
        String no = data1.getCardNumber();


        if (no != null) {
            String lastKey = data1.getCardNumber().substring((no.length() - 4), no.length());

            myViewHolder.cardSavedBinding.tvNumber.setText("************" + lastKey);
        }


        if (data1.isSelected()) {
            holder.cardSavedBinding.checkPay.setChecked(true);
        } else {
            holder.cardSavedBinding.checkPay.setChecked(false);

        }

        holder.cardSavedBinding.imageCard.setImageResource(CardType.getDrawable(data1.getCardType()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(cardListData item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardSavedBinding cardSavedBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardSavedBinding = DataBindingUtil.bind(itemView);
            cardSavedBinding.checkPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition(), cardSavedBinding.checkPay.isChecked());

                }
            });

        }
    }
}
