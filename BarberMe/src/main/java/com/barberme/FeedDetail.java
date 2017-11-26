package com.barberme;

import android.os.Bundle;

import com.barberme.custom.CustomActivity;

/**
 * The FeedDetail is the activity class that shows details about a selected
 * Sales Feed item. This activity only shows dummy detail text, you need to load
 * and display actual contents.
 */
public class FeedDetail extends CustomActivity
{

	/* (non-Javadoc)
	 * @see com.food.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_detail);

		getActionBar().setTitle("Sales Detail");

	}

}
