package com.unleashurgeek.lcsapp.adapters;

import android.graphics.Bitmap;

public class TeamListItem {
	
	private int id;
	private String teamName;
	private Bitmap teamLogo;
	
	public TeamListItem(int id, String teamName, Bitmap teamLogo) {
		this.id = id;
		this.teamName = teamName;
		this.teamLogo = teamLogo;
	}
	
	public int getID() {
		return id;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public Bitmap getTeamLogo() {
		return teamLogo;
	}
}
