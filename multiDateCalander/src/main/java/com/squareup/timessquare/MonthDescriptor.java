// Copyright 2012 Square, Inc.
package com.squareup.timessquare;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class MonthDescriptor.
 */
class MonthDescriptor
{
	
	/** The month. */
	private final int month;
	
	/** The year. */
	private final int year;
	
	/** The date. */
	private final Date date;
	
	/** The label. */
	private String label;

	/**
	 * Instantiates a new month descriptor.
	 *
	 * @param month the month
	 * @param year the year
	 * @param date the date
	 * @param label the label
	 */
	public MonthDescriptor(int month, int year, Date date, String label)
	{
		this.month = month;
		this.year = year;
		this.date = date;
		this.label = label;
	}

	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear()
	{
		return year;
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
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	void setLabel(String label)
	{
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MonthDescriptor{" + "label='" + label + '\'' + ", month="
				+ month + ", year=" + year + '}';
	}
}
