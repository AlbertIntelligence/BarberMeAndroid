// Copyright 2012 Square, Inc.

package com.squareup.timessquare;

import java.util.Date;

// TODO: Auto-generated Javadoc
/** Describes the state of a particular date cell in a {@link MonthView}. */
class MonthCellDescriptor
{
	
	/**
	 * The Enum RangeState.
	 */
	public enum RangeState {
		
		/** The none. */
		NONE, 
 /** The first. */
 FIRST, 
 /** The middle. */
 MIDDLE, 
 /** The last. */
 LAST
	}

	/** The date. */
	private final Date date;
	
	/** The value. */
	private final int value;
	
	/** The is current month. */
	private final boolean isCurrentMonth;
	
	/** The is selected. */
	private boolean isSelected;
	
	/** The is today. */
	private final boolean isToday;
	
	/** The is selectable. */
	private final boolean isSelectable;
	
	/** The is highlighted. */
	private boolean isHighlighted;
	
	/** The range state. */
	private RangeState rangeState;

	/**
	 * Instantiates a new month cell descriptor.
	 *
	 * @param date the date
	 * @param currentMonth the current month
	 * @param selectable the selectable
	 * @param selected the selected
	 * @param today the today
	 * @param highlighted the highlighted
	 * @param value the value
	 * @param rangeState the range state
	 */
	MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable,
			boolean selected, boolean today, boolean highlighted, int value,
			RangeState rangeState)
	{
		this.date = date;
		isCurrentMonth = currentMonth;
		isSelectable = selectable;
		isHighlighted = highlighted;
		isSelected = selected;
		isToday = today;
		this.value = value;
		this.rangeState = rangeState;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Checks if is current month.
	 *
	 * @return true, if is current month
	 */
	public boolean isCurrentMonth()
	{
		return isCurrentMonth;
	}

	/**
	 * Checks if is selectable.
	 *
	 * @return true, if is selectable
	 */
	public boolean isSelectable()
	{
		return isSelectable;
	}

	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected()
	{
		return isSelected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(boolean selected)
	{
		isSelected = selected;
	}

	/**
	 * Checks if is highlighted.
	 *
	 * @return true, if is highlighted
	 */
	boolean isHighlighted()
	{
		return isHighlighted;
	}

	/**
	 * Sets the highlighted.
	 *
	 * @param highlighted the new highlighted
	 */
	void setHighlighted(boolean highlighted)
	{
		isHighlighted = highlighted;
	}

	/**
	 * Checks if is today.
	 *
	 * @return true, if is today
	 */
	public boolean isToday()
	{
		return isToday;
	}

	/**
	 * Gets the range state.
	 *
	 * @return the range state
	 */
	public RangeState getRangeState()
	{
		return rangeState;
	}

	/**
	 * Sets the range state.
	 *
	 * @param rangeState the new range state
	 */
	public void setRangeState(RangeState rangeState)
	{
		this.rangeState = rangeState;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MonthCellDescriptor{" + "date=" + date + ", value=" + value
				+ ", isCurrentMonth=" + isCurrentMonth + ", isSelected="
				+ isSelected + ", isToday=" + isToday + ", isSelectable="
				+ isSelectable + ", isHighlighted=" + isHighlighted
				+ ", rangeState=" + rangeState + '}';
	}
}
