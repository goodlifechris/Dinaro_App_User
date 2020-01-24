package com.dinaro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinaro.R;
import com.dinaro.models.billpaymodel.CommonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyViewHolder> {

    Map<String, ArrayList<CommonDataModel>> data;
    private Context mContext;
    private CommonAdapter.MyViewHolder myViewHolder;
    private LayoutInflater inflater;
    private boolean isSearch;


    public CommonAdapter(Context context, Map<String, ArrayList<CommonDataModel>> data, boolean isSearch) {
        this.data = data;
        this.mContext = context;
        this.isSearch = isSearch;
        this.inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public CommonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.common_item, parent, false);
        this.myViewHolder = new CommonAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonAdapter.MyViewHolder holder, int position) {
        this.myViewHolder = holder;

        int i = 0;


        for (Map.Entry<String, ArrayList<CommonDataModel>> entry : data.entrySet()) {

            if (position == i) {
                String key = entry.getKey();

                List<CommonDataModel> list;
                list = entry.getValue();

                if (isSearch) {
                    holder.tv_section.setVisibility(View.VISIBLE);
                    holder.tv_section.setText("" + key);

                    LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 3);
                    holder.commonrecyclerview.setLayoutManager(linearLayoutManager);


                    PayBillrvUtilityAdapter payBillrvUtilityAdapter = new PayBillrvUtilityAdapter(mContext, entry.getValue(),true);
                    holder.commonrecyclerview.setAdapter(payBillrvUtilityAdapter);
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).getShow_on_app().equalsIgnoreCase("0")) {

                        } else {
                            holder.tv_section.setVisibility(View.VISIBLE);
                            holder.tv_section.setText("" + key);

                            ArrayList<CommonDataModel> commonDataModelArrayList=new ArrayList<CommonDataModel>();
                            for(int k=0;k<entry.getValue().size();k++)
                            {

                                if(entry.getValue().get(k).getShow_on_app().equals("0"))
                                {

                                }else {
                                    CommonDataModel commonDataModel=new CommonDataModel();
                                    commonDataModel.setAccountType(entry.getValue().get(k).getAccountType());
                                    commonDataModel.setFavstatus(entry.getValue().get(k).getFavstatus());
                                    commonDataModel.setId(entry.getValue().get(k).getId());
                                    commonDataModel.setImage(entry.getValue().get(k).getImage());
                                    commonDataModel.setTitle(entry.getValue().get(k).getTitle());
                                    commonDataModel.setType(entry.getValue().get(k).getType());
                                    commonDataModel.setShow_on_app(entry.getValue().get(k).getShow_on_app());
                                    commonDataModelArrayList.add(commonDataModel);

                                }

                            }




                            LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 3);
                            holder.commonrecyclerview.setLayoutManager(linearLayoutManager);


                            PayBillrvUtilityAdapter payBillrvUtilityAdapter = new PayBillrvUtilityAdapter(mContext, commonDataModelArrayList,false);
                            holder.commonrecyclerview.setAdapter(payBillrvUtilityAdapter);

                        }
                    }
                }

                // print your hello word here
                break;
            }
            i++;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_section;
        RecyclerView commonrecyclerview;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_section = itemView.findViewById(R.id.tv_section);
            commonrecyclerview = itemView.findViewById(R.id.commonrecyclerview);

        }
    }
}
