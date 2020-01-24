package com.dinaro.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;

import com.dinaro.R;

public class CalenderActivity extends AppCompatActivity  {
   public CalendarView tvCalender;
   public String date;
   public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.calender_activity_layout);
        context = CalenderActivity.this;

        tvCalender = findViewById(R.id.calendarView);
        tvCalender.setFocusedMonthDateColor(Color.MAGENTA);
        tvCalender.setUnfocusedMonthDateColor(Color.BLUE);
        tvCalender.setSelectedWeekBackgroundColor(Color.MAGENTA);
        tvCalender.setWeekSeparatorLineColor(Color.GREEN);


        tvCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                date = "" + year + "-" + month + "-" + dayOfMonth;
                System.out.println("date: "+date);



            }
        });
    }



}







