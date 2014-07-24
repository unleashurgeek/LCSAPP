package com.unleashurgeek.lcsapp.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HomeViewPager extends ViewPager {
	
	private boolean isSwipeEnabled;
	
	public HomeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.isSwipeEnabled = true;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (this.isSwipeEnabled)
			return super.onTouchEvent(ev);
		else
			return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (this.isSwipeEnabled)
			return super.onInterceptTouchEvent(ev);
		else
			return false;
	}
	
	/**
	 * Custom method to enable or disable swipe
	 * 
	 * @param isSwipeEnabled true to enable swipe, false otherwise
	 */
	public void setPagingEnabled(boolean isSwipeEnabled) {
		this.isSwipeEnabled = isSwipeEnabled;
	}
}
