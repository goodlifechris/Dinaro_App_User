package com.dinaro.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import com.dinaro.R;
import com.dinaro.models.RequestModel.fAQ.FaqData;

import java.util.ArrayList;
public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {

    public ArrayList<FaqData> faqData;
    private  Context mContext;
    private MyViewHolder myViewHolder;
    private LayoutInflater inflater;


    public FAQAdapter(Context context, ArrayList<FaqData> data) {
        this.faqData = data;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public FAQAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.card_faq, parent, false);
        this.myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.MyViewHolder holder, int position) {
        this.myViewHolder = holder;
        final FaqData data = faqData.get(position);


       holder.question.loadData("<b>Question: </b>"+data.getQuestion(),"text/html", null);
       holder.answer.loadData("<b>Answer: </b>"+data.getAnswer(),"text/html", null);
       holder.answer.setBackgroundColor(Color.TRANSPARENT);
       holder.question.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return faqData.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layput_faq;
        WebView question,answer;

        public MyViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            layput_faq = itemView.findViewById(R.id.faq_layout);
        }
    }
}
