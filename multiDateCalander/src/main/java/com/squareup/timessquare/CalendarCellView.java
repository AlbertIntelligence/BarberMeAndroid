// Copyright 2013 Square, Inc.

package com.squareup.timessquare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.squareup.timessquare.MonthCellDescriptor.RangeState;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarCellView.
 */
public class CalendarCellView extends TextView
{
	
	/** The Constant STATE_SELECTABLE. */
	private static final int[] STATE_SELECTABLE = { R.attr.state_selectable };
	
	/** The Constant STATE_CURRENT_MONTH. */
	private static final int[] STATE_CURRENT_MONTH = { R.attr.state_current_month };
	
	/** The Constant STATE_TODAY. */
	private static final int[] STATE_TODAY = { R.attr.state_today };
	
	/** The Constant STATE_HIGHLIGHTED. */
	private static final int[] STATE_HIGHLIGHTED = { R.attr.state_highlighted };
	
	/** The Constant STATE_RANGE_FIRST. */
	private static final int[] STATE_RANGE_FIRST = { R.attr.state_range_first };
	
	/** The Constant STATE_RANGE_MIDDLE. */
	private static final int[] STATE_RANGE_MIDDLE = { R.attr.state_range_middle };
	
	/** The Constant STATE_RANGE_LAST. */
	private static final int[] STATE_RANGE_LAST = { R.attr.state_range_last };

	/** The is selectable. */
	private boolean isSelectable = false;
	
	/** The is current month. */
	private boolean isCurrentMonth = false;
	
	/** The is today. */
	private boolean isToday = false;
	
	/** The is highlighted. */
	private boolean isHighlighted = false;
	
	/** The range state. */
	private RangeState rangeState = RangeState.NONE;

	/**
	 * Instantiates a new calendar cell view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	@SuppressWarnings("UnusedDeclaration")
	public CalendarCellView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * Sets the selectable.
	 *
	 * @param isSelectable the new selectable
	 */
	public void setSelectable(boolean isSelectable)
	{
		this.isSelectable = isSelectable;
		refreshDrawableState();
	}

	/**
	 * Sets the current month.
	 *
	 * @param isCurrentMonth the new current month
	 */
	public void setCurrentMonth(boolean isCurrentMonth)
	{
		this.isCurrentMonth = isCurrentMonth;
		refreshDrawableState();
	}

	/**
	 * Sets the today.
	 *
	 * @param isToday the new today
	 */
	public void setToday(boolean isToday)
	{
		this.isToday = isToday;
		refreshDrawableState();
	}

	/**
	 * Sets the range state.
	 *
	 * @param rangeState the new range state
	 */
	public void setRangeState(MonthCellDescriptor.RangeState rangeState)
	{
		this.rangeState = rangeState;
		refreshDrawableState();
	}

	/**
	 * Sets the highlighted.
	 *
	 * @param highlighted the new highlighted
	 */
	public void setHighlighted(boolean highlighted)
	{
		isHighlighted = highlighted;
		refreshDrawableState();
	}

	/* (non-Javadoc)
	 * @see android.widget.TextView#onCreateDrawableState(int)
	 */
	@Override
	protected int[] onCreateDrawableState(int extraSpace)
	{
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 5);

		if (isSelectable)
		{
			mergeDrawableStates(drawableState, STATE_SELECTABLE);
		}

		if (isCurrentMonth)
		{
			mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
		}

		if (isToday)
		{
			mergeDrawableStates(drawableState, STATE_TODAY);
		}

		if (isHighlighted)
		{
			mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
		}

		if (rangeState == MonthCellDescriptor.RangeState.FIRST)
		{
			mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
		}
		else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE)
		{
			mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
		}
		else if (rangeState == RangeState.LAST)
		{
			mergeDrawableStates(drawableState, STATE_RANGE_LAST);
		}

		return drawableState;
	}
}
