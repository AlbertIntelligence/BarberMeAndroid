package com.barberme.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.barberme.R;
import com.barberme.custom.CustomFragment;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by officemobile on 2017-11-26.
 */

public class TakeAppointmentFragment extends CustomFragment {

    /** The calendar picker view. */
    private CalendarPickerView calendar;

    /** The tab. */
    private LinearLayout tab;

    /** The horizontal tab container. */
    private LinearLayout vTabh;

    /** The vertical tab container. */
    private View vTabv;

    /** The open and close animations. */
    private Animation open, close;


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.take_appoinment, null);



        
        setupCalendar(view);
        return view;
    }

    private void setupCalendar(View v)
    {
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) v.findViewById(R.id.calendar_view);
        Calendar today = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<Date>();
        today.add(Calendar.DATE, 3);
        dates.add(today.getTime());
        today.add(Calendar.DATE, 5);
        dates.add(today.getTime());
        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(SelectionMode.RANGE) //
                .withSelectedDates(dates);
    }

}
