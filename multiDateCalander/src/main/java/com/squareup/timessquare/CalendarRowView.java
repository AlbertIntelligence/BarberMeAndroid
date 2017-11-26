// Copyright 2012 Square, Inc.
package com.squareup.timessquare;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * TableRow that draws a divider between each cell. To be used with
 * {@link CalendarGridView}.
 */
public class CalendarRowView extends ViewGroup implements View.OnClickListener
{
	
	/** The is header row. */
	private boolean isHeaderRow;
	
	/** The listener. */
	private MonthView.Listener listener;
	
	/** The cell size. */
	private int cellSize;

	/**
	 * Instantiates a new calendar row view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public CalendarRowView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		child.setOnClickListener(this);
		super.addView(child, index, params);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		long start = System.currentTimeMillis();
		final int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
		cellSize = totalWidth / 7;
		int cellWidthSpec = makeMeasureSpec(cellSize, EXACTLY);
		int cellHeightSpec = isHeaderRow ? makeMeasureSpec(cellSize, AT_MOST)
				: cellWidthSpec;
		int rowHeight = 0;
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++)
		{
			final View child = getChildAt(c);
			child.measure(cellWidthSpec, cellHeightSpec);
			// The row height is the height of the tallest cell.
			if (child.getMeasuredHeight() > rowHeight)
			{
				rowHeight = child.getMeasuredHeight();
			}
		}
		final int widthWithPadding = totalWidth + getPaddingLeft()
				+ getPaddingRight();
		final int heightWithPadding = rowHeight + getPaddingTop()
				+ getPaddingBottom();
		setMeasuredDimension(widthWithPadding, heightWithPadding);
		Logr.d("Row.onMeasure %d ms", System.currentTimeMillis() - start);
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		long start = System.currentTimeMillis();
		int cellHeight = bottom - top;
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++)
		{
			final View child = getChildAt(c);
			child.layout(c * cellSize, 0, (c + 1) * cellSize, cellHeight);
		}
		Logr.d("Row.onLayout %d ms", System.currentTimeMillis() - start);
	}

	/**
	 * Sets the checks if is header row.
	 *
	 * @param isHeaderRow the new checks if is header row
	 */
	public void setIsHeaderRow(boolean isHeaderRow)
	{
		this.isHeaderRow = isHeaderRow;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		// Header rows don't have a click listener
		if (listener != null)
		{
			listener.handleClick((MonthCellDescriptor) v.getTag());
		}
	}

	/**
	 * Sets the listener.
	 *
	 * @param listener the new listener
	 */
	public void setListener(MonthView.Listener listener)
	{
		this.listener = listener;
	}

	/**
	 * Sets the cell background.
	 *
	 * @param resId the new cell background
	 */
	public void setCellBackground(int resId)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			getChildAt(i).setBackgroundResource(resId);
		}
	}

	/**
	 * Sets the cell text color.
	 *
	 * @param resId the new cell text color
	 */
	public void setCellTextColor(int resId)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			((TextView) getChildAt(i)).setTextColor(resId);
		}
	}

	/**
	 * Sets the cell text color.
	 *
	 * @param colors the new cell text color
	 */
	public void setCellTextColor(ColorStateList colors)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			((TextView) getChildAt(i)).setTextColor(colors);
		}
	}

	/**
	 * Sets the typeface.
	 *
	 * @param typeface the new typeface
	 */
	public void setTypeface(Typeface typeface)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			((TextView) getChildAt(i)).setTypeface(typeface);
		}
	}
}
