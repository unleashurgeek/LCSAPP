package com.unleashurgeek.lcsapp.home;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LiveFragment extends LcsFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_home_live, container, false);
	}

	@Override
	public String getTitle() {
		return "Live";
	}
}
