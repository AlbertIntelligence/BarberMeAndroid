// Copyright 2012 Square, Inc.
package com.squareup.timessquare;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class MonthView.
 */
public class MonthView extends LinearLayout
{
	
	/** The title. */
	TextView title;
	
	/** The grid. */
	CalendarGridView grid;
	
	/** The listener. */
	private Listener listener;

	/**
	 * Creates the.
	 *
	 * @param parent the parent
	 * @param inflater the inflater
	 * @param weekdayNameFormat the weekday name format
	 * @param listener the listener
	 * @param today the today
	 * @param dividerColor the divider color
	 * @param dayBackgroundResId the day background res id
	 * @param dayTextColorResId the day text color res id
	 * @param titleTextColor the title text color
	 * @param displayHeader the display header
	 * @param headerTextColor the header text color
	 * @return the month view
	 */
	public static MonthView create(ViewGroup parent, LayoutInflater inflater,
			DateFormat weekdayNameFormat, Listener listener, Calendar today,
			int dividerColor, int dayBackgroundResId, int dayTextColorResId,
			int titleTextColor, boolean displayHeader, int headerTextColor)
	{
		final MonthView view = (MonthView) inflater.inflate(R.layout.month,
				parent, false);
		view.setDividerColor(dividerColor);
		view.setDayTextColor(dayTextColorResId);
		view.setTitleTextColor(titleTextColor);
		view.setDisplayHeader(displayHeader);
		view.setHeaderTextColor(headerTextColor);

		if (dayBackgroundResId != 0)
		{
			view.setDayBackground(dayBackgroundResId);
		}

		final int originalDayOfWeek = today.get(Calendar.DAY_OF_WEEK);

		int firstDayOfWeek = today.getFirstDayOfWeek();
		final CalendarRowView headerRow = (CalendarRowView) view.grid
				.getChildAt(0);
		for (int offset = 0; offset < 7; offset++)
		{
			today.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + offset);
			final TextView textView = (TextView) headerRow.getChildAt(offset);
			textView.setText(weekdayNameFormat.format(today.getTime())
					.charAt(0) + "");
		}
		today.set(Calendar.DAY_OF_WEEK, originalDayOfWeek);
		view.listener = listener;
		return view;
	}

	/**
	 * Instantiates a new month view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public MonthView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		title = (TextView) findViewById(R.id.title);
		grid = (CalendarGridView) findViewById(R.id.calendar_grid);
	}

	/**
	 * Inits the.
	 *
	 * @param month the month
	 * @param cells the cells
	 * @param displayOnly the display only
	 * @param titleTypeface the title typeface
	 * @param dateTypeface the date typeface
	 */
	public void init(MonthDescriptor month,
			List<List<MonthCellDescriptor>> cells, boolean displayOnly,
			Typeface titleTypeface, Typeface dateTypeface)
	{
		Logr.d("Initializing MonthView (%d) for %s",
				System.identityHashCode(this), month);
		long start = System.currentTimeMillis();
		title.setText(month.getLabel());

		final int numRows = cells.size();
		grid.setNumRows(numRows);
		for (int i = 0; i < 6; i++)
		{
			CalendarRowView weekRow = (CalendarRowView) grid.getChildAt(i + 1);
			weekRow.setListener(listener);
			if (i < numRows)
			{
				weekRow.setVisibility(VISIBLE);
				List<MonthCellDescriptor> week = cells.get(i);
				for (int c = 0; c < week.size(); c++)
				{
					MonthCellDescriptor cell = week.get(c);
					CalendarCellView cellView = (CalendarCellView) weekRow
							.getChildAt(c);

					String cellDate = Integer.toString(cell.getValue());
					if (!cellView.getText().equals(cellDate))
					{
						cellView.setText(cellDate);
					}
					cellView.setEnabled(cell.isCurrentMonth());
					cellView.setClickable(!displayOnly);

					cellView.setSelectable(cell.isSelectable());
					cellView.setSelected(cell.isSelected());
					cellView.setCurrentMonth(cell.isCurrentMonth());
					cellView.setToday(cell.isToday());
					cellView.setRangeState(cell.getRangeState());
					cellView.setHighlighted(cell.isHighlighted());
					cellView.setTag(cell);
				}
			}
			else
			{
				weekRow.setVisibility(GONE);
			}
		}

		if (titleTypeface != null)
		{
			title.setTypeface(titleTypeface);
		}
		if (dateTypeface != null)
		{
			grid.setTypeface(dateTypeface);
		}

		Logr.d("MonthView.init took %d ms", System.currentTimeMillis() - start);
	}

	/**
	 * Sets the divider color.
	 *
	 * @param color the new divider color
	 */
	public void setDividerColor(int color)
	{
		grid.setDividerColor(color);
	}

	/**
	 * Sets the day background.
	 *
	 * @param resId the new day background
	 */
	public void setDayBackground(int resId)
	{
		grid.setDayBackground(resId);
	}

	/**
	 * Sets the day text color.
	 *
	 * @param resId the new day text color
	 */
	public void setDayTextColor(int resId)
	{
		grid.setDayTextColor(resId);
	}

	/**
	 * Sets the title text color.
	 *
	 * @param color the new title text color
	 */
	public void setTitleTextColor(int color)
	{
		title.setTextColor(color);
	}

	/**
	 * Sets the display header.
	 *
	 * @param displayHeader the new display header
	 */
	public void setDisplayHeader(boolean displayHeader)
	{
		grid.setDisplayHeader(displayHeader);
	}

	/**
	 * Sets the header text color.
	 *
	 * @param color the new header text color
	 */
	public void setHeaderTextColor(int color)
	{
		grid.setHeaderTextColor(color);
	}

	/**
	 * The Interface Listener.
	 */
	public interface Listener
	{
		
		/**
		 * Handle click.
		 *
		 * @param cell the cell
		 */
		void handleClick(MonthCellDescriptor cell);
	}
}
