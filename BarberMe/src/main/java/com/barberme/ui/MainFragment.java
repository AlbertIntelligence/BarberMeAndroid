package com.barberme.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.barberme.ChartDetail;
import com.barberme.R;
import com.barberme.custom.CustomActivity;
import com.barberme.custom.CustomFragment;
import com.barberme.model.Data;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class MainFragment is the base fragment that shows the ListView of
 * various charts. You can add your code to do whatever you want related to
 * charts for your app.
 */
@SuppressLint("InflateParams")
public class MainFragment extends CustomFragment
{

	/** The chart list. */
	private ArrayList<Data> iList;

	/** The tab. */
	private LinearLayout tab;

	/** The horizontal tab container. */
	private LinearLayout vTabh;

	/** The vertical tab container. */
	private View vTabv;

	/** The open and close animations. */
	private Animation open, close;

	/** The Chart Adapter. */
	private ChartAdapter adp;

	/** The calendar picker view. */
	private CalendarPickerView calendar;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.main_container, null);

		open = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_in);
		close = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.fade_out);
		close.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation)
			{
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}

			@Override
			public void onAnimationEnd(Animation animation)
			{
				vTabv.setVisibility(View.GONE);

				int w = (getResources().getDisplayMetrics().widthPixels - tab
						.getWidth()) / 2;
				((HorizontalScrollView) getView().findViewById(R.id.hsv))
						.scrollTo((int) (tab.getX() - w), 0);

			}
		});

		setupCalendar(v);
		setupTabView(v);
		setupView(v);
		return v;
	}

	/* (non-Javadoc)
	 * @see com.whatshere.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
	}

	/**
	 * Set up the calendar picker view.
	 * 
	 * @param v
	 *            the root view
	 */
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

	/**
	 * Set up tab view.
	 * 
	 * @param v
	 *            the root view
	 */
	private void setupTabView(View v)
	{
		String arr[] = getResources().getStringArray(R.array.arr_filter);
		vTabv = v.findViewById(R.id.vTabv);
		final ListView l = (ListView) vTabv;
		l.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.tab_v,
				arr));
		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				onTabSelected(position);
				vTabv.startAnimation(close);
			}
		});

		vTabh = (LinearLayout) v.findViewById(R.id.vTabh);
		for (String s : arr)
		{
			final LinearLayout ll = (LinearLayout) getLayoutInflater(null)
					.inflate(R.layout.tab_h, null);
			((TextView) ll.getChildAt(0)).setText(s);

			ll.setOnTouchListener(CustomActivity.TOUCH);
			ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					// vTabh.setTag(v);
					if (calendar.getVisibility() == View.VISIBLE)
					{
						calendar.setVisibility(View.GONE);
						return;
					}
					if (vTabv.getVisibility() == View.VISIBLE)
					{
						vTabv.startAnimation(close);
					}
					else
					{
						vTabv.startAnimation(open);
						vTabv.setVisibility(View.VISIBLE);
					}

				}
			});
			vTabh.addView(ll);
			if (tab == null && vTabh.getChildCount() == 2)
			{
				onTabSelected(1);
			}
		}
	}

	/**
	 * Called On tab selected.
	 * 
	 * @param pos
	 *            the position of selected tab
	 */
	private void onTabSelected(int pos)
	{
		if (tab != null)
		{
			tab.getChildAt(1).setVisibility(View.GONE);
			((TextView) tab.getChildAt(0))
					.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			((TextView) tab.getChildAt(0)).setTextColor(getResources()
					.getColor(R.color.main_blue_lt));
		}
		tab = (LinearLayout) vTabh.getChildAt(pos);
		tab.getChildAt(1).setVisibility(View.VISIBLE);
		((TextView) tab.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(
				0, 0, R.drawable.icon_arrow_down, 0);
		((TextView) tab.getChildAt(0)).setTextColor(getResources().getColor(
				R.color.main_blue));

		calendar.setVisibility(pos == 0 ? View.VISIBLE : View.GONE);
		if (pos == 0)
			((TextView) tab.getChildAt(1)).setText("Select range");
		else if (pos == 1)
			((TextView) tab.getChildAt(1)).setText("10-Nov");
		else if (pos == 2)
			((TextView) tab.getChildAt(1)).setText("09-Nov");
		else if (pos == 3)
			((TextView) tab.getChildAt(1)).setText("Week-2");
		else if (pos == 4)
			((TextView) tab.getChildAt(1)).setText("November");
		else if (pos == 5)
			((TextView) tab.getChildAt(1)).setText("October");
		else if (pos == 6)
			((TextView) tab.getChildAt(1)).setText("2014");
		else if (pos == 7)
			((TextView) tab.getChildAt(1)).setText("All");
		if (adp != null)
			adp.notifyDataSetChanged();

	}

	/**
	 * Setup the view components for this fragment. You write your code for
	 * initializing the views, setting the adapters, touch and click listeners
	 * etc.
	 * 
	 * @param v
	 *            the base view of fragment
	 */
	private void setupView(View v)
	{
		loadDummyData();
		final ListView list = (ListView) v.findViewById(R.id.list);
		adp = new ChartAdapter();
		list.setAdapter(adp);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				startActivity(new Intent(getActivity(), ChartDetail.class));
			}
		});
		list.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				calendar.setVisibility(View.GONE);
				return false;
			}
		});
	}

	/**
	 * Load dummy charts data for displaying on the ListView. You need to write
	 * your own code for loading real data from Web-service or API and
	 * displaying them on GridView.
	 */
	private void loadDummyData()
	{
		ArrayList<Data> al = new ArrayList<Data>();
		al.add(new Data(new String[] { "$325", "+15.2%", "Daily Revenue" },
				new int[] { R.drawable.chart1, R.color.main_green }));
		al.add(new Data(new String[] { "8", "-6.12%", "Daily Sales" },
				new int[] { R.drawable.chart2, R.color.main_red }));
		al.add(new Data(new String[] { "82%", "+34.2%", "Payment Gateways" },
				new int[] { R.drawable.chart3, R.color.main_green }));

		iList = new ArrayList<Data>(al);
		iList.addAll(al);
		iList.addAll(al);
		iList.addAll(al);
	}

	/**
	 * The Class ChartAdapter is the adapter for list view used in this
	 * fragment. You must provide valid values for adapter count and must write
	 * your code for binding the data to each item in adapter as per your need.
	 */
	private class ChartAdapter extends BaseAdapter
	{
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return iList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Data getItem(int arg0)
		{
			return iList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@SuppressLint("InflateParams")
		@Override
		public View getView(int arg0, View v, ViewGroup arg2)
		{
			if (v == null)
				v = getActivity().getLayoutInflater().inflate(
						R.layout.chart_item, null);

			Data d = getItem(arg0);
			v.setBackgroundResource(d.getResources()[0]);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			lbl.setText(d.getTexts()[0]);

			lbl = (TextView) v.findViewById(R.id.lbl2);
			lbl.setText(d.getTexts()[1]);
			lbl.setTextColor(getResources().getColor(d.getResources()[1]));

			lbl = (TextView) v.findViewById(R.id.lbl4);
			lbl.setText(d.getTexts()[2]);

			lbl = (TextView) v.findViewById(R.id.lbl3);
			lbl.setText(((TextView) tab.getChildAt(1)).getText());

			return v;
		}

	}
}
