package com.barberme;

import android.os.Bundle;

import com.barberme.custom.CustomActivity;

/**
 * The ChartDetail is the activity class that shows details about a selected
 * Chart. This activity only shows dummy detail text and chart Image, you need
 * to load and display actual contents.
 */
public class ChartDetail extends CustomActivity
{

	/* (non-Javadoc)
	 * @see com.food.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_detail);

		getActionBar().setTitle("Daily Revenue");

	}

}
