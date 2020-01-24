package com.dinaro.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinaro.R;
import com.dinaro.adapters.RecyclerAdapter;
import com.dinaro.models.Transaction;
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel;
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarListener;
import com.view.calender.horizontal.umar.horizontalcalendarview.HorizontalCalendarView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCalendarFragment extends Fragment implements HorizontalCalendarListener {
    HorizontalCalendarView  hcv;
    TextView currentMonthTextView,year;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<Transaction> transactions = new ArrayList<>();
    public SearchCalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search_calendar, container, false);
        hcv = view.findViewById(R.id.horizontalcalendarview);
        currentMonthTextView = view.findViewById(R.id.month);
        year = view.findViewById(R.id.year);

        hcv.setBackgroundColor(getActivity().getResources().getColor(R.color.colorTransparent));
        hcv.setControlTint(R.color.grey);
        hcv.changeAccent(R.color.grey);
        hcv.setContext(this);


        recyclerView = view.findViewById(R.id.recycler);
        adapter = new RecyclerAdapter();
//        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        loadTransactionTestData();

        return view;
    }

    @Override
    public void updateMonthOnScroll(DayDateMonthYearModel selectedDate) {
        currentMonthTextView.setText(selectedDate.month );
        year.setText(selectedDate.year);
    }

    @Override
    public void newDateSelected(DayDateMonthYearModel selectedDate) {




    }


    public void loadTransactionTestData() {
//           loadTransactionTestData Transaction(String id, String type, String icon, String amount) {

//        add transactions in an arrayList
//to convert enum of transaction tyoe later and amount to double
        transactions = new ArrayList<>();

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


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.update(transactions);

    }

//    public void loadTransactionTestData() {
////           loadTransactionTestData Transaction(String id, String type, String icon, String amount) {
//
////        add transactions in an arrayList
////to convert enum of transaction tyoe later and amount to double
//        transactions = new ArrayList<>();
//
//        transactions.add(new Transaction("JO31012019", "mpesa", String.valueOf(R.drawable.zuku), 2345.00, "23/8/19 12:00", "Remy Ngatia"));
//        transactions.add(new Transaction("JO31032019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 4005.0, "22/8/19 11:00", "James Bond"));
//
//        transactions.add(new Transaction("JO31042019", "visa", String.valueOf(R.drawable.ic_visa), 2345.0, "21/8/19 17:00", "Ques n Ques"));
//        transactions.add(new Transaction("JO31062019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 5005.0, "21/8/19 12:00", "Christopher P."));
//        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "18/8/19 12:00", "Gaucho"));
//
//        transactions.add(new Transaction("JO31052019", "visa", String.valueOf(R.drawable.ic_visa), 4005.0, "18/8/19 9:00", "Sandlers Ltd"));
//
//
//        transactions.add(new Transaction("JO31062019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 5005.0, "18/8/19 8:30", "Kimani Marley"));
//        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "18/8/19 6:50", "Remy Ngatia"));
//
//        transactions.add(new Transaction("JO31072019", "mastercard", String.valueOf(R.drawable.ic_mastercard), 4075.0, "16/8/19 12:00", "Remy Ngatia"));
//        transactions.add(new Transaction("JO31082019", "paypal", String.valueOf(R.drawable.paypal), 2905.0, "16/8/19 12:00", "Remy Ngatia"));
//        transactions.add(new Transaction("JO31092019", "paypal", String.valueOf(R.drawable.paypal), 6876.0, "15/8/19 9:00", "James John"));
//
//        transactions.add(new Transaction("JO31102019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 2345.0, "13/8/19 8:00", "Jack Jones"));
//        transactions.add(new Transaction("JO31222019", "mpesa", String.valueOf(R.drawable.ic_mpesa), 4005.0, "1/8/19 4:00", "Pius Ndugo"));
//
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter.update(transactions);
//
//    }

}
