package com.unleashurgeek.lcsapp.api;

public class LeagueNA extends League {
	private static final long serialVersionUID = 4535054197266525545L;

	public LeagueNA() {
		super();
	}
	
	@Override
	public int getID() {
		return 1;
	}

	@Override
	public String getLeagueName() {
		return "North America";
	}

	@Override
	public String getLeagueNameShort() {
		return "NA";
	}

}
