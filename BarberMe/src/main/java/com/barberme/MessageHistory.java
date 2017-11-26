package com.barberme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.barberme.custom.CustomActivity;
import com.barberme.model.Data;

import java.util.ArrayList;

/**
 * The SalesFeed is the activity class that shows a list of Sales feeds. This
 * activity only shows dummy feed listing, you need to load and display actual
 * contents.
 */
public class MessageHistory extends CustomActivity
{

	/** The feed list. */
	private ArrayList<Data> dList;

	/** The keys for dates. */
	private ArrayList<String> keys;

	/* (non-Javadoc)
	 * @see com.food.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sale_feed);

		getActionBar().setTitle("Historique des messages en direct");

		loadDummyData();

		ExpandableListView exp = (ExpandableListView) findViewById(R.id.list);
		FeedAdapter adp = new FeedAdapter();
		exp.setAdapter(adp);
		for (int i = 0; i < adp.getGroupCount(); i++)
			exp.expandGroup(i);
		exp.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id)
			{
				startActivity(new Intent(MessageHistory.this, FeedDetail.class));
				return true;
			}
		});
	}

	/**
	 * Load dummy feed data for displaying on the Listview. You need to write
	 * your own code for loading real data from Web-service or API and
	 * displaying them on ListView.
	 */
	private void loadDummyData()
	{
		dList = new ArrayList<Data>();
		dList.add(new Data(new String[] { "Paypal", "10:10pm", "John Carlos",
				"Order #123456", "+$649.88" }, new int[] {
				R.drawable.icon_sale, R.drawable.icon_new }));
		dList.add(new Data(new String[] { "Stripe", "02:33pm", "David Chan",
				"Order #345436", "+$1573.12" }, new int[] {
				R.drawable.icon_sale, R.drawable.icon_new }));
		dList.add(new Data(new String[] { "Paytm", "07:42pm",
				"Michael  Whereat", "Order #898464", "+$66.34" }, new int[] {
				R.drawable.icon_refund, 0 }));

		keys = new ArrayList<String>();
		keys.add("Today 11 Nov 2014");
		keys.add("10 Nov 2014");
		keys.add("09 Nov 2014");
		keys.add("08 Nov 2014");
		keys.add("07 Nov 2014");
		keys.add("06 Nov 2014");

	}

	/**
	 * The Class FeedAdapter is the adapter for list view used in this activity.
	 * You must provide valid values for adapter count and must write your code
	 * for binding the data to each item in adapter as per your need.
	 */
	@SuppressLint("InflateParams")
	private class FeedAdapter extends BaseExpandableListAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupCount()
		 */
		@Override
		public int getGroupCount()
		{
			return keys.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
		 */
		@Override
		public int getChildrenCount(int groupPosition)
		{
			return dList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroup(int)
		 */
		@Override
		public String getGroup(int groupPosition)
		{
			return keys.get(groupPosition);
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChild(int, int)
		 */
		@Override
		public Data getChild(int groupPosition, int childPosition)
		{
			return dList.get(childPosition);
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupId(int)
		 */
		@Override
		public long getGroupId(int groupPosition)
		{
			return 0;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
		 */
		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return 0;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#hasStableIds()
		 */
		@Override
		public boolean hasStableIds()
		{
			return false;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getLayoutInflater().inflate(
						R.layout.feed_grp_item, null);
			TextView lbl = (TextView) convertView;
			lbl.setText(getGroup(groupPosition));
			lbl.setClickable(true);
			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent)
		{
			if (convertView == null)
				convertView = getLayoutInflater().inflate(
						R.layout.feed_child_item, null);

			Data d = getChild(groupPosition, childPosition);
			TextView lbl = (TextView) convertView.findViewById(R.id.lbl1);
			lbl.setCompoundDrawablesWithIntrinsicBounds(0, d.getResources()[0],
					0, 0);
			lbl.setText(d.getTexts()[0]);

			lbl = (TextView) convertView.findViewById(R.id.lbl2);
			lbl.setText(d.getTexts()[1]);

			lbl = (TextView) convertView.findViewById(R.id.lbl3);
			lbl.setText(d.getTexts()[2]);

			lbl = (TextView) convertView.findViewById(R.id.lbl4);
			lbl.setText(d.getTexts()[3]);

			lbl = (TextView) convertView.findViewById(R.id.lbl5);
			lbl.setCompoundDrawablesWithIntrinsicBounds(0, d.getResources()[1],
					0, 0);
			lbl.setText(d.getTexts()[4]);
			return convertView;
		}

		/* (non-Javadoc)
		 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return true;
		}

	}

}
