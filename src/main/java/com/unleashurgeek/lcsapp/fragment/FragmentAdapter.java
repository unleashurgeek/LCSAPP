package com.unleashurgeek.lcsapp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
	
	private final LcsFragment FRAGMENTS[];
	
	public FragmentAdapter(FragmentManager fm, LcsFragment[] fragList) {
		super(fm);
		FRAGMENTS = fragList;
	}
	
	@Override
	public Fragment getItem(int position) {
		return FRAGMENTS[position];
	}
	
	@Override
	public int getCount() {
		return FRAGMENTS.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return FRAGMENTS[position].getTitle();
	}
}