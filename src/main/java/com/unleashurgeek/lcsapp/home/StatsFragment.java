package com.unleashurgeek.lcsapp.home;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;

public class StatsFragment extends LcsFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_stats, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		page(view);
		page(view);
		page(view);
		page(view);
		page(view);
		page(view);
	}
	
	private void page(View view) {
		view.getContext();
		LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		final LinearLayout stats = (LinearLayout)view.findViewById(R.id.statPage);
		final LinearLayout page = (LinearLayout)inflater.inflate(R.layout.fragment_home_stats_table, stats, false);
		
		TextView tv;
		ImageView iv;
		
		tv = (TextView)page.findViewById(R.id.statType);
		tv.setText("Top KDA Ratio");
		tv = (TextView)page.findViewById(R.id.statData);
		tv.setText("8.3");
		tv = (TextView)page.findViewById(R.id.statRole);
		tv.setText("Mid Laner");
		tv = (TextView)page.findViewById(R.id.statsPlayerName);
		tv.setText("Danny \"Shiphtur\" Le");
		iv = (ImageView)page.findViewById(R.id.statPlayerImage);
		iv.setImageResource(R.drawable.ic_tsm);
		iv = (ImageView)page.findViewById(R.id.statsTeamLogo);
		iv.setImageResource(R.drawable.ic_tsm);
		
		stats.addView(page);
		
		tv = new TextView(view.getContext());
		tv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		tv.setHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics()));
		stats.addView(tv);
	}
	
	@Override
	public String getTitle() {
		return "Stats";
	}

}
