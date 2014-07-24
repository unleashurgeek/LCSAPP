package com.unleashurgeek.lcsapp;

import java.io.Serializable;

import com.unleashurgeek.lcsapp.api.ImageCache;
import com.unleashurgeek.lcsapp.api.League;
import com.unleashurgeek.lcsapp.api.LeagueEU;
import com.unleashurgeek.lcsapp.api.LeagueNA;
import com.unleashurgeek.lcsapp.api.TeamStandings;

public class DataCache implements Serializable {
	private static final long serialVersionUID = 2868458939429050435L;
	
	// Data to be Retained
	public static ImageCache imageCache;
	public static LeagueNA leagueNA;
	public static LeagueEU leagueEU;
	public static League currentLeague;
	
	private DataCache() {}
	
	public static void setLeague(String league) {
		if (league.equals("na")) {
			if (leagueNA == null) {
				leagueNA = new LeagueNA();
				leagueNA.getTeams();
				TeamStandings.pollStandings(leagueNA, leagueNA.getTeamsArray());
			}
			currentLeague = leagueNA;
		} else if (league.equals("eu")) {
			if (leagueEU == null) {
				leagueEU = new LeagueEU();
				leagueEU.getTeams();
				TeamStandings.pollStandings(leagueEU, leagueEU.getTeamsArray());
			}
			currentLeague = leagueEU;
		}
	}
}
