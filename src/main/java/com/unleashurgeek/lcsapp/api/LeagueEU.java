package com.unleashurgeek.lcsapp.api;

public class LeagueEU extends League {
	private static final long serialVersionUID = -2733364400114000014L;

	public LeagueEU() {
		super();
	}
	
	@Override
	public int getID() {
		return 2;
	}

	@Override
	public String getLeagueName() {
		return "Europe";
	}

	@Override
	public String getLeagueNameShort() {
		return "EU";
	}

}
