package com.dinaro.activities;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dinaro.R;
import com.dinaro.models.RequestModel.resentActivity.ResentData;
import com.dinaro.utils.AppConstant;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ActivityCalender extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView;
    private HashMap<String, ArrayList> myTransactionData = new HashMap<>();
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_calender);


        // Gets the calendar from the view
        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);
        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());
        getIntentData();
        Button markDayButton = findViewById(R.id.markDayButton);


        markDayButton.setOnClickListener(view ->
        {
            Calendar calendar = Calendar.getInstance();

            Random random = new Random(System.currentTimeMillis());

            int daySelected = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


            calendar.set(Calendar.DAY_OF_MONTH, daySelected);

            robotoCalendarView.markCircleImage1(calendar.getTime());


        });


    }

    private void getIntentData() {


        if (getIntent() != null) {

            if (getIntent().getSerializableExtra(AppConstant.CALENDER_DATA) != null) {



                myTransactionData = (HashMap<String, ArrayList>) getIntent().getSerializableExtra(AppConstant.CALENDER_DATA);


                //   for (int i = 0; i < myTransactionData.size(); i++) {
                Set set = myTransactionData.entrySet();

                Iterator iterator = set.iterator();

                while (iterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) iterator.next();

                    // Get Key
                    String keyValue = (String) mapEntry.getKey();

                    //Get Value
                    ArrayList<ResentData> value = (ArrayList) mapEntry.getValue();

                    System.out.println("Key : " + keyValue + "= Value : " + value);
                    // }


                    Set keys = myTransactionData.keySet();
                    Calendar calendar = Calendar.getInstance();



                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(value.get(0).getmDay()));

                    robotoCalendarView.markCircleImage1(calendar.getTime());


                }

            }

        }

    }


    private String formateDate(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        String dateString = formatter.format(new Date(date.getTime()));
        return dateString;

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDayClick(Date date) {

        ArrayList<ResentData> value = (ArrayList) myTransactionData.get(formateDate(date));
        if (value != null) {

            ((TextView) findViewById(R.id.tvTransaction)).setText("" + value.size());
            amount = 0;
            for (int i = 0; i < value.size(); i++) {

                amount = amount + Integer.valueOf(value.get(i).getTransactionAmount());
            }

            ((TextView) findViewById(R.id.tvAmount)).setText("" + value.size());
            ((TextView) findViewById(R.id.tvAmount)).setText("~ KES " + amount);
        } else {

            ((TextView) findViewById(R.id.tvTransaction)).setText("0");
            ((TextView) findViewById(R.id.tvAmount)).setText("0");
        }

        //   Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDayLongClick(Date date) {
        // Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick() {
        // Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick() {
        // Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }

}

