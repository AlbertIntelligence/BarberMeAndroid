package com.squareup.timessquare;

import android.util.Log;

// TODO: Auto-generated Javadoc
/** Log utility class to handle the log tag and DEBUG-only logging. */
final class Logr
{
	
	/**
	 * D.
	 *
	 * @param message the message
	 */
	public static void d(String message)
	{
		if (BuildConfig.DEBUG)
		{
			Log.d("TimesSquare", message);
		}
	}

	/**
	 * D.
	 *
	 * @param message the message
	 * @param args the args
	 */
	public static void d(String message, Object... args)
	{
		if (BuildConfig.DEBUG)
		{
			d(String.format(message, args));
		}
	}
}
