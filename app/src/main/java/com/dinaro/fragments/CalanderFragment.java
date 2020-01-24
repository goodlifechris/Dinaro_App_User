package com.dinaro.fragments;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.activities.RobotoCalenderView;
import com.dinaro.databinding.FragmentCalanderBinding;
import com.dinaro.models.RequestModel.resentActivity.RecentBase;
import com.dinaro.models.RequestModel.resentActivity.RecentResult;
import com.dinaro.models.RequestModel.resentActivity.ResentData;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.ProgressDialogUtils;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalanderFragment extends Fragment implements View.OnClickListener, RobotoCalenderView.RobotoCalendarListener {

    private HashMap<String, ArrayList> myTransactionData = new HashMap<>();
    private int amount = 0;
    private Context context;
    private FragmentCalanderBinding binding;
    private Calendar calendar;
    private String month;
    private List<String> lDay = new ArrayList<>();
    private List<String> lMonth;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userId;

    private List<ResentData> recent = new ArrayList<>();

    public CalanderFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calander, container, false);
        context = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        binding.ivBack.setOnClickListener(this);
        binding.robotoCalendarPicker.setRobotoCalendarListener(CalanderFragment.this);

        binding.robotoCalendarPicker.setShortWeekDays(false);
        binding.robotoCalendarPicker.showDateTitle(true);
        binding.robotoCalendarPicker.setDate(new Date());

        // Toast.makeText(context, "value get"+AppConstant.CURRENTMONTH, Toast.LENGTH_SHORT).show();
        // getIntentData();
        setResentActivity();


       // getIntentData();
//        binding.markDayButton.setOnClickListener(view -> {
//            Calendar calendar = Calendar.getInstance();
//
//            Random random = new Random(System.currentTimeMillis());
//
//            int daySelected = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//            calendar.set(Calendar.DAY_OF_MONTH, daySelected);
//
//            binding.robotoCalendarPicker.markCircleImage1(calendar.getTime());
//        });

        return binding.getRoot();

    }

    private String formateDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        String dateString = formatter.format(new Date(date.getTime()));
        return dateString;
        /*SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("MMddyyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format1.parse(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        //return format2.format(c.getTime());
    }

    private void getIntentData() {







        // if (b.getSerializable(AppConstant.CALENDER_DATA) != null) {

//
//            myTransactionData = (HashMap<String, ArrayList>) b.getSerializable(AppConstant.CALENDER_DATA);
//
//            Toast.makeText(context, "the size--" + myTransactionData.size(), Toast.LENGTH_SHORT).show();
//            //   for (int i = 0; i < myTransactionData.size(); i++) {
//            Set set = (Set) myTransactionData.entrySet();
//
//            Iterator iterator = set.iterator();
//
//            while (iterator.hasNext()) {
//                Map.Entry mapEntry = (Map.Entry) iterator.next();
//
//                // Get Key
//                String keyValue = (String) mapEntry.getKey();
//
//                //Get Value
//                ArrayList<ResentData> value = (ArrayList) mapEntry.getValue();
//
//                System.out.println("Key : " + keyValue + "= Value : " + value);
//                // }


        //Set keys = myTransactionData.keySet();
        calendar = Calendar.getInstance();

        //calendar.set(Calendar.DAY_OF_MONTH, 17);

        // robotoCalendarView.markCircleImage1(calendar.getTime());
        //  Calendar calendar = Calendar.getInstance();

        //Random random = new Random(System.currentTimeMillis());

        //  int daySelected = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
/*
                    String input_date="01/08/2012";
                    SimpleDateFormat format1=new SimpleDateFormat("MM/dd/yyyy");

                    Date dt1= null;
                    try {
                        dt1 = format1.parse(value.get(0).getmDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    DateFormat format2=new SimpleDateFormat("EEEE");
                    String finalDay=format2.format(dt1);
*/

        binding.tvTransaction.setText("0");
        binding.tvAmount.setText("0");
        lMonth = new ArrayList<>();
        for (int j = 0; j < recent.size(); j++) {

            if (AppConstant.CURRENTMONTH.equals(recent.get(j).getCurrentMonth())) {

               /* lMonth.add(month);
                Toast.makeText(context,"the date-"+month,Toast.LENGTH_SHORT).show();*/
                //calendar.set(Calendar.MONTH,Integer.parseInt(recent.get(j).getmMonth()));
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(recent.get(j).getmDay()));
                calendar.set(Calendar.MONTH,Integer.parseInt(recent.get(j).getmMonth())-1);

                binding.robotoCalendarPicker.markCircleImage1(calendar.getTime());
               // DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(),R.color.roboto_calendar_circle_1));
            }


        }

    }


    private void setResentActivity() {
        if (CommonUtils.isOnline(getActivity())) {
            try {
                ProgressDialogUtils.show(getActivity());
                Api api = RequestController.createService(Api.class);
                api.getPaymentList(userId).enqueue(new Callback<RecentBase>() {
                    @Override
                    public void onResponse(Call<RecentBase> call, Response<RecentBase> response) {
                        // binding.swipeRefresh.setRefreshing(false);
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {

                                RecentResult result = response.body().getResult();
                                recent.clear();
                                recent.addAll(result.getData());
                                for (int i = 0; i < recent.size(); i++) {

                                    try {

                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM yyyy");

                                        recent.get(i).setCurrentMonth(sdf2.format(sdf.parse(recent.get(i).getCreatedAt())));

                                        SimpleDateFormat format = new SimpleDateFormat("dd");
                                        Calendar c = Calendar.getInstance();
                                        try {
                                            c.setTime(sdf.parse(recent.get(i).getCreatedAt()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        recent.get(i).setmDay(format.format(c.getTime()));

                                        SimpleDateFormat format1 = new SimpleDateFormat("MM");
                                        Calendar c1 = Calendar.getInstance();
                                        try {
                                            c1.setTime(sdf.parse(recent.get(i).getCreatedAt()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        recent.get(i).setmMonth(format1.format(c1.getTime()));


                                        SimpleDateFormat format2 = new SimpleDateFormat("MMddyyyy");
                                        Calendar c2 = Calendar.getInstance();
                                        try {
                                            c2.setTime(sdf.parse(recent.get(i).getCreatedAt()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        recent.get(i).setmDate(format2.format(c2.getTime()));


                                        //Toast.makeText(context, ""+format.format(sdf.parse(recent.get(i).getCreatedAt())), Toast.LENGTH_SHORT).show();

                                        //getIntentData();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
//                                    SimpleDateFormat df = new SimpleDateFormat("MM yyyy");
//                                    String testDateString = df.format(recent.get(i).getCreatedAt());
//                                    Toast.makeText(context, ""+testDateString, Toast.LENGTH_SHORT).show();
//                                    String newstr = date;
//                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
//                                    Calendar c = Calendar.getInstance();
//                                    try {
//                                        c.setTime(format1.parse(newstr));
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }


                                }
                                getIntentData();
                                //Toast.makeText(context, "the size is -" + recent.size(), Toast.LENGTH_SHORT).show();


                            } else {

                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

//                    private String formateDate(String date) {
//                        String newstr = date;
//                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
////                        Calendar c = Calendar.getInstance();
////                        try {
////                            c.setTime(format1.parse(newstr));
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
//                        return format.format(c.getTime());
//                    }

//                    private String formateDate1(String date) {
//                        String newstr = date;
//                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        SimpleDateFormat format = new SimpleDateFormat("dd");
//                        Calendar c = Calendar.getInstance();
//                        try {
//                            c.setTime(format1.parse(newstr));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        return format.format(c.getTime());
//                    }
//
//                    private String formateDate2(String date){ //Added on 19August
//                        String newstr = date;
//                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        SimpleDateFormat format = new SimpleDateFormat("MM");
//                        Calendar c = Calendar.getInstance();
//                        try {
//                            c.setTime(format1.parse(newstr));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        return format.format(c.getTime());
//                    }
//


                    @Override
                    public void onFailure(Call<RecentBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                loadFragment(new FragmentRecent());
                break;
            default:
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onDayClick(Date date) {

       // Toast.makeText(context,""+date,Toast.LENGTH_SHORT).show();
        ArrayList<String> stringArrayList=new ArrayList<String>();
        for(int k=0;k<recent.size();k++)
        {
            if(recent.get(k).getmDate().equalsIgnoreCase(formateDate(date)))
            {
                stringArrayList.add(recent.get(k).getTransactionAmount());
            }

        }
       // ArrayList<ResentData> value = (ArrayList) myTransactionData.get(formateDate(date));


        if (stringArrayList.size()>0) {
            binding.tvTransaction.setText("" + stringArrayList.size());
            amount = 0;
            for (int i = 0; i < stringArrayList.size(); i++) {

                amount = amount + Integer.valueOf(stringArrayList.get(i));
            }

            binding.tvTransaction.setText("" + stringArrayList.size());
            binding.tvAmount.setText("~ KES " + amount);

        } else {
            binding.tvTransaction.setText("0");
            binding.tvAmount.setText("0");
        }
    }

    @Override
    public void onDayLongClick(Date date) {

    }

    @Override
    public void onRightButtonClick() {
        /*month=calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        if (!month.equalsIgnoreCase("August")){
            return;
         }else {
            getIntentData();
        }*/
        //getIntentData();
        getIntentData();
    }

    @Override
    public void onLeftButtonClick() {
        /*month=calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        if (!month.equalsIgnoreCase("August")){
            return;
        }else {
            getIntentData();
        }*/
        //getIntentData();
        getIntentData();
    }

}
