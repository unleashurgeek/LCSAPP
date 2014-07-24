package com.unleashurgeek.lcsapp.adapters;

public class NavDrawerListItem implements NavDrawerList {
	public static final int ITEM_TYPE = 1;
	private int id;
	private String label;
	
	private NavDrawerListItem() {}
	
	public static NavDrawerListItem create( int id, String label) {
		NavDrawerListItem item = new NavDrawerListItem();
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
		return ITEM_TYPE;
	}

	public boolean isEnabled() {
		return true;
	}
}
