package com.barberme.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barberme.R;
import com.barberme.custom.CustomFragment;

/**
 * Created by monsieurpetion on 2017-11-26.
 */

public class TakeTicketFragment extends CustomFragment {

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.take_ticket, null);


        return view;
    }

    private void setupView()
    {

        //etTouchNClick(R.id.imageView4);


    }
}
