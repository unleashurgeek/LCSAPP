package com.unleashurgeek.lcsapp.adapters;

public class NavDrawerListTitle implements NavDrawerList {
	public static final int TITLE_TYPE = 0;
	private int id;
	private String label;
	
	private NavDrawerListTitle() {}
	
	public static NavDrawerListTitle create( int id, String label) {
		NavDrawerListTitle item = new NavDrawerListTitle();
		item.setId(id);
		item.setLabel(label);
		return item;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public int getType() {
		return TITLE_TYPE;
	}

	public boolean isEnabled() {
		return false;
	}
}
