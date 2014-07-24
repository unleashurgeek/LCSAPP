package com.unleashurgeek.lcsapp.api;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TeamStandings {
			
	public static void pollStandings(League league, Teams teams) {
		// TODO: Replace with a server-side API that polls this data to remove need for updating the app when this link changes.
		try {
			Document doc = Jsoup.connect("http://na.lolesports.com/" + (league.getID() == 1 ? "na" : "eu") + "-lcs/2014/split2/standings").get();
			Elements rows = doc.select("table.stats-container").first().select("tbody").select("tr");
			Element row = rows.get(0);
			String rowString = row.text();
			String[] data = rowString.split(" ");
			int rank = Integer.parseInt(data[0]);
			String rankChange = data[1];
			int wins = Integer.parseInt(data[data.length - 2]);
			int losses = Integer.parseInt(data[data.length - 1]);
			String teamName;
			if (data.length > 5) {
				int newL = data.length - 5;
				StringBuilder s = new StringBuilder();
				for (int i = 0; i <= newL; i++) {
					s.append(data[i + 2]);
					if (i != newL)
						s.append(" ");
				}
				teamName = s.toString();
			} else {
				teamName = data[2];
			}
			teams.getTeamByName(teamName).setStats(rank, rankChange, wins, losses);
			
			for (Element e : row.siblingElements()) {
				rowString = e.text();
				data = rowString.split(" ");
				rank = Integer.parseInt(data[0]);
				rankChange = data[1];
				wins = Integer.parseInt(data[data.length - 2]);
				losses = Integer.parseInt(data[data.length - 1]);
				if (data.length > 5) {
					int newL = data.length - 5;
					StringBuilder s = new StringBuilder();
					for (int i = 0; i <= newL; i++) {
						s.append(data[i + 2]);
						if (i != newL)
							s.append(" ");
					}
					teamName = s.toString();
				} else {
					teamName = data[2];
				}
				teams.getTeamByName(teamName).setStats(rank, rankChange, wins, losses);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*for (Team team : teams) {
			TeamStatData data = teamData.get(team.getName());
			team.setRank(data.rank);
			team.setRankChange(data.rankChange);
			team.setLosses(data.losses);
			team.setWins(data.wins);
		}*/
	}
}