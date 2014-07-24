package com.unleashurgeek.lcsapp.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class Team implements Serializable, Comparable<Team> {
	private static final long serialVersionUID = -6070104817017235019L;
	
	private int id;
	private League league;
	private String name;
	private String acronym;
	private String bio;
	private String logoURL;
	private String teamPhotoURL;
	private int wins;
	private String rankChange;
	private int rank;
	private int losses;
	private List<Integer> playerIDs;

	public Team(League league, final int id) {
		this.id = id;
		this.league = league;
		playerIDs = new ArrayList<Integer>();
		final String team = EsportsRequestHandler.makeRequest("/team/" + id + ".json");
		if (team != null) {
			try {
				JSONObject teamObj = new JSONObject(team);
				name = teamObj.getString("name").trim();
				acronym = teamObj.getString("acronym");
				bio = teamObj.getString("bio");
				logoURL = teamObj.getString("logoUrl");
				teamPhotoURL = teamObj.getString("teamPhotoUrl");

				JSONObject roster = teamObj.getJSONObject("roster");
				for (int i = 0; i < roster.length(); i++) {
					JSONObject player = roster.getJSONObject("players" + i);
					playerIDs.add(player.getInt("playerId"));
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public int getID() {
		return id;
	}

	public League getLeague() {
		return league;
	}

	public String getName() {
		return name;
	}

	public String getAcronym() {
		return acronym;
	}

	public String getBio() {
		return bio;
	}

	public Bitmap getLogo() {
		return league.getCache().getImage(logoURL);
	}

	public Bitmap getTeamPhoto() {
		return league.getCache().getImage(teamPhotoURL);
	}
	
	public void setStats(int rank, String rankChange, int wins, int losses) {
		this.rank = rank;
		this.rankChange = rankChange;
		this.wins = wins;
		this.losses = losses;
	}

	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public String getRankChange() {
		return rankChange;
	}
	
	public void setRankChange(String rankChange) {
		this.rankChange = rankChange;
	}

	public int getLosses() {
		return losses;
	}
	
	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (Integer pID : playerIDs)
			players.add(new Player(this, pID));
		return players;
	}

	public int compareTo(Team another) {
		return this.getRank() - another.getRank();
	}

}
