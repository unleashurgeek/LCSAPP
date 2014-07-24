package com.unleashurgeek.lcsapp.api;

public class TeamStatData {
	public int rank;
	public String rankChange;
	public int wins;
	public int losses;
	
	public TeamStatData(int rank, String rankChange, int wins,  int losses) {
		this.rank = rank;
		this.rankChange = rankChange;
		this.wins = wins;
		this.losses = losses;
	}
}
