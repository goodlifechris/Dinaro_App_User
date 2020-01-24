package com.dinaro.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinaro.R;
import com.dinaro.activities.ViewReceiptActivity;
import com.dinaro.models.Transaction;
import com.dinaro.utils.AppConstant;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayList<Transaction> transactionsFiltered = new ArrayList<>();
    Context context;
    NewFilter mfilter;


    public RecyclerAdapter() {


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_storename_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);
        holder.bind(transaction);


    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    public void update(ArrayList<Transaction> transactionsNew) {

        transactions = transactionsNew;

        notifyDataSetChanged();
    }
    public void updateFiltered(ArrayList<Transaction> transactionsNew) {

        transactions = transactionsNew;

        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

//        ImageView image;
        TextView amount, code,txt_name;
        RelativeLayout relatiVelayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            image = itemView.findViewById(R.id.imageview_paypal);
            amount = itemView.findViewById(R.id.txt_Ksh);
            txt_name = itemView.findViewById(R.id.txt_name);
            code = itemView.findViewById(R.id.txt_code);
            relatiVelayout = itemView.findViewById(R.id.relatiVelayout);

        }

        String amountksh;

        private void bind(Transaction transaction) {

//            image.setImageResource(Integer.parseInt(transaction.getIcon()));
            code.setText("Steers Ngong Rd");

            amountksh=("- KES " + NumberFormat.getNumberInstance(Locale.US).format( transaction.getAmount()));
            amount.setText(amountksh);

            txt_name.setText(transaction.getDateMonth());
            relatiVelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent i = new Intent(v.getContext(), ViewReceiptActivity.class);
                    i.putExtra(AppConstant.TRANSACTION_ID,transaction.getId());
                    i.putExtra(AppConstant.TRANSACTION_TIME,transaction.getTime());
                    i.putExtra(AppConstant.TRANSACTION_NAME,transaction.getName());
                    i.putExtra(AppConstant.TRANSACTION_AMOUNT,amountksh);
                    i.putExtra(AppConstant.TRANSACTION_ICON,transaction.getIcon());

                    v.getContext().startActivity(i);


                }
            });
        }
    }

    @Override
    public Filter getFilter(){
        if (mfilter == null){
            transactionsFiltered.clear();
//            we introduce another list called the original list that will hold the data while we do the filtering
            transactionsFiltered.addAll(this.transactions);
            mfilter = new NewFilter(this,transactionsFiltered);
        }
        return mfilter;
    }

    public class NewFilter extends Filter {

        private final RecyclerAdapter myAdapter;
        private final List<Transaction> originalList;
        private final List<Transaction> filteredList;

        private NewFilter(RecyclerAdapter myAdapter, List<Transaction> originalList) {
            this.myAdapter = myAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<Transaction>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            Log.e("REC_SEQUENCE",charString);
            filteredList.clear();


            if (charString.length()==0) {
                filteredList.addAll(originalList);
            } else {
                for (Transaction transaction : originalList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (transaction.getName().toLowerCase().contains(charString.toLowerCase()) ||
                            transaction.getId().toLowerCase().contains(charString.toLowerCase())
                    ) {
                        filteredList.add(transaction);
                    }
                }

                Log.e("REC_SEQUENCE","size is process "+filteredList.size());

//                transactionsFiltered.addAll(filteredList);
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            filterResults.count=filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.e("REC_SEQUENCE","size is "+transactionsFiltered.size());

            myAdapter.transactions.clear();
            myAdapter.transactions.addAll((ArrayList<Transaction>)filterResults.values);
            myAdapter.notifyDataSetChanged();
        }
    }

}
