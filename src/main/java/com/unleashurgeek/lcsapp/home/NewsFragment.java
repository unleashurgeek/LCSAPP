package com.unleashurgeek.lcsapp.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;

public class NewsFragment extends LcsFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_news, container, false);
	}

	@Override
	public String getTitle() {
		return "News";
	}
}
