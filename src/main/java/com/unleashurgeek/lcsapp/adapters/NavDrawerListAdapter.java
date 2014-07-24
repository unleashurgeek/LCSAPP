package com.unleashurgeek.lcsapp.adapters;

import com.unleashurgeek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NavDrawerListAdapter extends ArrayAdapter<NavDrawerList> {
	
	private LayoutInflater inflater;
	
	public NavDrawerListAdapter(Context context, int textViewResourceId, NavDrawerList[] navDrawerItems) {
		super(context, textViewResourceId, navDrawerItems);
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		NavDrawerList menuItem = this.getItem(position);
		
		switch (menuItem.getType()) {
			case NavDrawerListTitle.TITLE_TYPE:
				view = getTitleView(convertView, parent, menuItem);
				break;
			case NavDrawerListItem.ITEM_TYPE:
				view = getItemView(convertView, parent, menuItem);
				break;
			default:
				view = getTitleView(convertView, parent, menuItem);
				break;
		}
		
		return view;
	}

	public View getTitleView(View convertView, ViewGroup parent, NavDrawerList navDrawerItem) {
		NavDrawerListTitle menuItem = (NavDrawerListTitle)navDrawerItem;
		NavMenuTitleHolder navMenuTitleHolder = null;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.navdrawer_list_title, parent, false);
			TextView labelView = (TextView)convertView.findViewById(R.id.navmenutitle_label);
			
			navMenuTitleHolder = new NavMenuTitleHolder();
			navMenuTitleHolder.labelView = labelView;
			convertView.setTag(navMenuTitleHolder);
		}
		
		if (navMenuTitleHolder == null) {
			navMenuTitleHolder = (NavMenuTitleHolder)convertView.getTag();
		}
		navMenuTitleHolder.labelView.setText(menuItem.getLabel());
		return convertView;
	}

	public View getItemView(View convertView, ViewGroup parent, NavDrawerList navDrawerItem) {
		NavDrawerListItem menuItem = (NavDrawerListItem)navDrawerItem;
		NavMenuItemHolder navMenuItemHolder = null;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.navdrawer_list_item, parent, false);
			TextView labelView = (TextView)convertView.findViewById(R.id.navmenuitem_label);
			
			navMenuItemHolder = new NavMenuItemHolder();
			navMenuItemHolder.labelView = labelView;
			
			convertView.setTag(navMenuItemHolder);
		}
		
		if (navMenuItemHolder == null) {
			navMenuItemHolder = (NavMenuItemHolder)convertView.getTag();
		}
		navMenuItemHolder.labelView.setText(menuItem.getLabel());
		
		return convertView;
	}
	
	private static class NavMenuItemHolder {
		private TextView labelView;
	}
	
	private static class NavMenuTitleHolder {
		private TextView labelView;
	}
}
