// Copyright 2012 Square, Inc.
package com.squareup.timessquare;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * ViewGroup that draws a grid of calendar cells. All children must be
 * {@link CalendarRowView}s. The first row is assumed to be a header and no
 * divider is drawn above it.
 */
public class CalendarGridView extends ViewGroup
{
	/**
	 * The grid lines don't exactly line up on certain devices (Nexus 7, Nexus
	 * 5). Fudging the co-ordinates by half a point seems to fix this without
	 * breaking other devices.
	 * */
	private static final float FLOAT_FUDGE = 0.5f;

	/** The divider paint. */
	private final Paint dividerPaint = new Paint();
	
	/** The old width measure size. */
	private int oldWidthMeasureSize;
	
	/** The old num rows. */
	private int oldNumRows;

	/**
	 * Instantiates a new calendar grid view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public CalendarGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		dividerPaint
				.setColor(getResources().getColor(R.color.calendar_divider));
	}

	/**
	 * Sets the divider color.
	 *
	 * @param color the new divider color
	 */
	public void setDividerColor(int color)
	{
		dividerPaint.setColor(color);
	}

	/**
	 * Sets the day background.
	 *
	 * @param resId the new day background
	 */
	public void setDayBackground(int resId)
	{
		for (int i = 1; i < getChildCount(); i++)
		{
			((CalendarRowView) getChildAt(i)).setCellBackground(resId);
		}
	}

	/**
	 * Sets the day text color.
	 *
	 * @param resId the new day text color
	 */
	public void setDayTextColor(int resId)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			ColorStateList colors = getResources().getColorStateList(resId);
			((CalendarRowView) getChildAt(i)).setCellTextColor(colors);
		}
	}

	/**
	 * Sets the display header.
	 *
	 * @param displayHeader the new display header
	 */
	public void setDisplayHeader(boolean displayHeader)
	{
		getChildAt(0).setVisibility(displayHeader ? VISIBLE : GONE);
	}

	/**
	 * Sets the header text color.
	 *
	 * @param color the new header text color
	 */
	public void setHeaderTextColor(int color)
	{
		((CalendarRowView) getChildAt(0)).setCellTextColor(color);
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
			((CalendarRowView) getChildAt(i)).setTypeface(typeface);
		}
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
	 */
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		if (getChildCount() == 0)
		{
			((CalendarRowView) child).setIsHeaderRow(true);
		}
		super.addView(child, index, params);
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#dispatchDraw(android.graphics.Canvas)
	 */
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		final ViewGroup row = (ViewGroup) getChildAt(1);
		int top = row.getTop();
		int bottom = getBottom();
		// Left side border.
		final int left = row.getChildAt(0).getLeft() + getLeft();
		canvas.drawLine(left + FLOAT_FUDGE, top, left + FLOAT_FUDGE, bottom,
				dividerPaint);

		// Each cell's right-side border.
		for (int c = 0; c < 7; c++)
		{
			float x = left + row.getChildAt(c).getRight() - FLOAT_FUDGE;
			canvas.drawLine(x, top, x, bottom, dividerPaint);
		}
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#drawChild(android.graphics.Canvas, android.view.View, long)
	 */
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime)
	{
		final boolean retVal = super.drawChild(canvas, child, drawingTime);
		// Draw a bottom border.
		final int bottom = child.getBottom() - 1;
		canvas.drawLine(child.getLeft(), bottom, child.getRight() - 2, bottom,
				dividerPaint);
		return retVal;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		Logr.d("Grid.onMeasure w=%s h=%s",
				MeasureSpec.toString(widthMeasureSpec),
				MeasureSpec.toString(heightMeasureSpec));
		int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
		if (oldWidthMeasureSize == widthMeasureSize)
		{
			Logr.d("SKIP Grid.onMeasure");
			setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
			return;
		}
		long start = System.currentTimeMillis();
		oldWidthMeasureSize = widthMeasureSize;
		int cellSize = widthMeasureSize / 7;
		// Remove any extra pixels since /7 is unlikely to give whole nums.
		widthMeasureSize = cellSize * 7;
		int totalHeight = 0;
		final int rowWidthSpec = makeMeasureSpec(widthMeasureSize, EXACTLY);
		final int rowHeightSpec = makeMeasureSpec(cellSize, EXACTLY);
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++)
		{
			final View child = getChildAt(c);
			if (child.getVisibility() == View.VISIBLE)
			{
				if (c == 0)
				{ // It's the header: height should be wrap_content.
					measureChild(child, rowWidthSpec,
							makeMeasureSpec(cellSize, AT_MOST));
				}
				else
				{
					measureChild(child, rowWidthSpec, rowHeightSpec);
				}
				totalHeight += child.getMeasuredHeight();
			}
		}
		final int measuredWidth = widthMeasureSize + 2; // Fudge factor to make
														// the borders show up.
		setMeasuredDimension(measuredWidth, totalHeight);
		Logr.d("Grid.onMeasure %d ms", System.currentTimeMillis() - start);
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom)
	{
		long start = System.currentTimeMillis();
		top = 0;
		for (int c = 0, numChildren = getChildCount(); c < numChildren; c++)
		{
			final View child = getChildAt(c);
			final int rowHeight = child.getMeasuredHeight();
			child.layout(left, top, right, top + rowHeight);
			top += rowHeight;
		}
		Logr.d("Grid.onLayout %d ms", System.currentTimeMillis() - start);
	}

	/**
	 * Sets the num rows.
	 *
	 * @param numRows the new num rows
	 */
	public void setNumRows(int numRows)
	{
		if (oldNumRows != numRows)
		{
			// If the number of rows changes, make sure we do a re-measure next
			// time around.
			oldWidthMeasureSize = 0;
		}
		oldNumRows = numRows;
	}
}
