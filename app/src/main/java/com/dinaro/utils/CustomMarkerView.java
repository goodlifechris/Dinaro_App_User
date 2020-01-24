package com.dinaro.utils;

import android.content.Context;
import android.widget.TextView;

import com.dinaro.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.util.Locale;



/**
 * Created by goodlife on 23,August,2019
 */
public class CustomMarkerView extends MarkerView {

    private TextView tvLabel;
    private TextView tvContent;


    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        // find your layout components
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvLabel = (TextView) findViewById(R.id.tvLabel);

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        PieEntry pe = (PieEntry) e;

        tvLabel.setText(pe.getLabel());



        tvContent.setText("Kes " + NumberFormat.getNumberInstance(Locale.US).format(e.getY()));

        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }
}
