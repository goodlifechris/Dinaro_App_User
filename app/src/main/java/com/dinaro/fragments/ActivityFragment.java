package com.dinaro.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinaro.R;
import com.dinaro.adapters.RecyclerAdapter;
import com.dinaro.models.Transaction;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<Transaction> transactions = new ArrayList<>();

    NavController navController;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this,view);

        recyclerView = view.findViewById(R.id.recycler);
        adapter = new RecyclerAdapter();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        loadTransactionTestData();
        return view;
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

//    @OnClick(R.id.imageViewCalendar)
//    public void onViewClicked() {
//
//
//
//        navController.navigate(R.id.action_activityFragment_to_searchCalendarFragment);
//    }
}
