package com.dinaro;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dinaro.models.PieTransaction;
import com.dinaro.models.Transaction;
import com.dinaro.utils.CustomMarkerView;
import com.dinaro.utils.PieChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {


    @BindView(R.id.PieChart)
    PieChart chart;

//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

    ArrayList<Transaction> transactions;
//    TotalRecyclerAdapter totalRecyclerAdapter;
    ArrayList<PieTransaction> pieTransactions=new ArrayList<>();
    @BindView(R.id.buttonDay)
    Button buttonDay;
    @BindView(R.id.buttonWeek)
    Button buttonWeek;
    @BindView(R.id.buttonMonth)
    Button buttonMonth;

    @BindView(R.id.textViewKshAmount)
    TextView textViewKshAmount;
    @BindView(R.id.textViewName)
    TextView textViewName;


    int sum=0;
    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this,view);
        loadTransactionTestData();
        setPieChart();

//        totalRecyclerAdapter = new TotalRecyclerAdapter(newPieTransactions,sum);
//
//        recyclerView.setAdapter(totalRecyclerAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;

    }


    public void setPieChart(){
        chart.getDescription().setEnabled(false);
//        chart.useGradient(true);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(80f);

        chart.setTransparentCircleRadius(20f);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        chart.setData(generatePieData());
        chart.animateXY(2000, 2000);
        chart.setDescription(null);
        //to change the entries nellow to white
        chart.getLegend().setEnabled(false);
//        chart.getLegend().setDrawInside(false);

//        Legend legend = chart.getLegend();
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);

        chart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
        chart.setDrawingCacheBackgroundColor(Color.TRANSPARENT);// this is
        chart.setDrawHoleEnabled(true);
        chart.setDrawEntryLabels(false);

        chart.setTransparentCircleAlpha(110);
        chart.setHoleColor(Color.WHITE);

        chart.setTouchEnabled(true);
        chart.setRotationEnabled(true);
        IMarker marker = new CustomMarkerView(getContext(), R.layout.custom_marker_view_layout);
        chart.setMarker(null);
        chart.invalidate();
    }
    
    public void loadTransactionTestData(){
//           loadTransactionTestData Transaction(String id, String type, String icon, String amount) {

//        add transactions in an arrayList
//to convert enum of transaction tyoe later and amount to double
        transactions=new ArrayList<>();

        transactions.add(new Transaction("JO31012019", "mpesa", String.valueOf(R.drawable.zuku), 2345.00, "23/8/19 12:00", "Remy Ngatia","January 9"));
        transactions.add(new Transaction("JO31032019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 4005.0, "22/8/19 11:00", "James Bond","January 15"));

        transactions.add(new Transaction("JO31042019", "visa", String.valueOf(R.drawable.ic_visa), 2345.0, "21/8/19 17:00", "Ques n Ques","January 16"));
        transactions.add(new Transaction("JO31062019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 5005.0, "21/8/19 12:00", "Christopher P.","January 16"));
        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "18/8/19 12:00", "Gaucho","January 17"));

        transactions.add(new Transaction("JO31052019", "visa", String.valueOf(R.drawable.ic_visa), 4005.0, "18/8/19 9:00", "Sandlers Ltd","January 18"));


        transactions.add(new Transaction("JO31062019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 5005.0, "18/8/19 8:30", "Kimani Marley","January 19"));
        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "18/8/19 6:50", "Remy Ngatia","January 20"));

        transactions.add(new Transaction("JO31072019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 4075.0, "16/8/19 12:00", "Remy Ngatia","January 21"));
        transactions.add(new Transaction("JO31082019", "paypal", String.valueOf(R.drawable.paypal), 2905.0, "16/8/19 12:00", "Remy Ngatia","January 21"));
        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "15/8/19 9:00", "James John","January 22"));

        transactions.add(new Transaction("JO31102019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 2345.0, "13/8/19 8:00", "Jack Jones","January 23"));
        transactions.add(new Transaction("JO31222019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 4005.0, "1/8/19 4:00", "Pius Ndugo","January 24"));



        List<String> stringsTypes=new ArrayList<>();

        for (Transaction transaction:transactions){

            stringsTypes.add(transaction.getType());
            System.out.println(transaction.getType());

        }

//        List<String> gasList = // create list with duplicates...


        Set<String> listDistinct =  new HashSet<String>(stringsTypes);


        for (String transaction:listDistinct){

            System.out.println("unique "+transaction);

        }

        pieTransactions=new ArrayList<>();



        for (Transaction transaction:transactions){

            for (String s:listDistinct){

                if (transaction.getType().equals(s)){
                    pieTransactions.add(new PieTransaction(transaction.getType(),transaction.getAmount(),transaction.getIcon()));

                }
            }

        }


        Log.e("pie data ",pieTransactions.toString());
        PieTransaction item;
        for (int i = 0; i < pieTransactions.size(); i++) {
            item = pieTransactions.get(i);
            maps.put(item.getType(), (int) (maps.containsKey(item.getType()) ? item.getTotal()+ + maps.get(item.getType()) : item.getTotal()));
        }

        System.out.println("new data "+maps.toString());

        setPieTransaction();


    }
    ArrayList<PieTransaction> newPieTransactions=new ArrayList<>();
    Map<String, Integer> maps = new HashMap<String, Integer>();


    public void setPieTransaction(){



        for (Map.Entry<String, Integer> entry : maps.entrySet()) {

            String icon="";

            for (Transaction transaction :transactions) {

                if (transaction.getType().equals(entry.getKey())) {

                    icon=transaction.getIcon();
                }
            }

            sum+=entry.getValue();

            PieTransaction pieTransaction=new PieTransaction(entry.getKey(),(double)(entry.getValue()),icon);
            newPieTransactions.add(pieTransaction);
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        System.out.println("new data "+newPieTransactions.toString());



    }




    private int returnColor(String type){

        int r=R.color.colorPrimary;

        switch (type)
        {
            case "mpesa":

                r= R.color.colorGreen;

                break;

            case "visa":

                r= R.color.colorDarkBlue;

                break;

            case "mastercard":

                r= R.color.colorOrange;

                break;


        }

        return r;

    }

    private PieData generatePieData(){

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> arrayListColorInt = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : maps.entrySet()) {
            Log.e("value",entry.getKey() + "/" + entry.getValue());
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            arrayListColorInt.add(getResources().getColor(returnColor(entry.getKey())));


        }


//        entries.add(new PieEntry(2000, "Expenses"));
//        arrayListColorInt.add(new Integer(getResources().getColor(R.color.colorGreyPrimary)));





        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(arrayListColorInt);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setValueLineColor(Color.TRANSPARENT);
        PieData data = new PieData(dataset);
        data.setDrawValues(false);
        return data;

    }


    @OnClick({R.id.buttonDay, R.id.buttonWeek, R.id.buttonMonth})
    public void onViewClicked(View view) {
        setAllUnselected();

        switch (view.getId()) {
            case R.id.buttonDay:
                buttonDay.setTextColor(getResources().getColor(R.color.colorWhite));
                buttonDay.setBackground(getResources().getDrawable(R.drawable.button_day_clicked));
                textViewKshAmount.setText("4,000");
                textViewName.setText("THUR");

                break;
            case R.id.buttonWeek:
                buttonWeek.setTextColor(getResources().getColor(R.color.colorWhite));
                buttonWeek.setBackground(getResources().getDrawable(R.drawable.button_week_clicked));
                textViewKshAmount.setText("38,000");
                textViewName.setText("JAN-W3");
                break;
            case R.id.buttonMonth:
                buttonMonth.setTextColor(getResources().getColor(R.color.colorWhite));
                buttonMonth.setBackground(getResources().getDrawable(R.drawable.button_month_clicked));
                textViewKshAmount.setText("54,000");
                textViewName.setText("JAN");
                break;
        }
    }




    public void setAllUnselected(){

        buttonDay.setTextColor(getResources().getColor(R.color.colorBlack));
        buttonDay.setBackground(getResources().getDrawable(R.drawable.button_day));

        buttonWeek.setTextColor(getResources().getColor(R.color.colorBlack));
        buttonWeek.setBackground(getResources().getDrawable(R.drawable.button_week));

        buttonMonth.setTextColor(getResources().getColor(R.color.colorBlack));
        buttonMonth.setBackground(getResources().getDrawable(R.drawable.button_month));


    }

}
