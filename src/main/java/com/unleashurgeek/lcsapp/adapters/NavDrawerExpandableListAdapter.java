package com.unleashurgeek.lcsapp.adapters;

import java.util.HashMap;
import java.util.List;

import com.unleashurgeek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerExpandableListAdapter extends BaseExpandableListAdapter {
	
	private final LayoutInflater inflater;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	
	public NavDrawerExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		this.inflater = LayoutInflater.from(context);
		this.listDataHeader = listDataHeader;
		this.listDataChild = listChildData;
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
	}
	
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final String childText = (String)getChild(groupPosition, childPosition);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.navdrawer_elist_item, parent, false);
		}
		
		TextView txtListChild = (TextView)convertView.findViewById(R.id.lblListItem);
		
		txtListChild.setText(childText);
		return convertView;
	}
	
	public int getChildrenCount(int groupPosition) {
		return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
	}
	
	public Object getGroup(int groupPosition) {
		return this.listDataHeader.get(groupPosition);
	}
	
	public int getGroupCount() {
		return this.listDataHeader.size();
	}
	
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = (String)getGroup(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.navdrawer_elist_title, parent, false);
		}
		
		TextView lblListHeader = (TextView)convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setText(headerTitle);

		((ImageView)convertView.findViewById(R.id.title_indicator)).setImageResource(isExpanded ? R.drawable.ic_up : R.drawable.ic_down);
		
		return convertView;
	}
	
	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
