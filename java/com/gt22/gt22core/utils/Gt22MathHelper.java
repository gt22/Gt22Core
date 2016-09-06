package com.gt22.gt22core.utils;

import java.util.List;

/**
 * Some functions that i didn'd fount in {@link Math} or {@link MathHelper}
 * @author gt22
 */
public class Gt22MathHelper
{
	/**
	 * Return difference between a and b;
	 */
	public static long diff(long a, long b)
	{
		return Math.abs(a - b);
	}
	/**
	 * swaps a and b, java doesn't allow changin int value passed into fucnction so usage is b = swap(a, a=b);
	 * @return the a value, before b value assigned to it
	 */
	public static long swap(long a, long b)
	{
		return a;
	}
	
	/**
	 	Can be used to get position of closest int in list to passed value
	 */
	public static long getPositionOfClosest(List<Integer> searchin, long value)
	{
		if(searchin.size() == 0)
		{
			return -1;
		}
		long closestdiff = diff(0, value);
		long closestpos = 0;
		for(int pos = 0; pos < searchin.size(); pos++)
		{
			long i = searchin.get(pos);
			long diff = diff(i, value);
			if(diff < closestdiff)
			{
				closestdiff = diff;
				closestpos = pos;
			}
		}
		return closestpos;
	}
	
	public static long bound(long value, long min, long max)
	{
		return Math.min(max, Math.max(value, min));
	}
}
