package com.unleashurgeek.lcsapp.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.unleashurgeek.lcsapp.DataCache;

public abstract class League implements Serializable {
	private static final long serialVersionUID = 6140609506287381877L;
	
	private int tournamentID;
	private int seriesID;
	private List<Integer> contestantIDs;
	private Teams teams = null;
	private Players players = null;

	public League() {
		teams = new Teams();
		players = new Players();
		contestantIDs = new ArrayList<Integer>();

		final String league = EsportsRequestHandler.makeRequest("/league/" + getID() + ".json");
		if (league != null) {
			try {
				JSONObject leagueObj = new JSONObject(league);
				tournamentID = leagueObj.getInt("defaultTournamentId");
				seriesID = leagueObj.getInt("defaultSeriesId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		String tournament = EsportsRequestHandler.makeRequest("/tournament/" + tournamentID + ".json");
		if (tournament != null) {
			try {
				JSONObject tournamentObj = new JSONObject(tournament);
				JSONObject contestants = tournamentObj
						.getJSONObject("contestants");
				JSONArray names = contestants.names();
				for (int i = 0; i < names.length(); i++) {
					String name = (String) names.get(i);
					JSONObject contestant = contestants.getJSONObject(name);
					contestantIDs.add(contestant.getInt("id"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public ImageCache getCache() {
		return DataCache.imageCache;
	}

	/**
	 * @return The SparseArray of Player entities.
	 */
	public Players getPlayersArray() {
		return players;
	}

	/**
	 * @return The SparseArray of Team entities.
	 */
	public Teams getTeamsArray() {
		return teams;
	}

	/**
	 * Gets the teams in this league. Will load any that have yet to be loaded.
	 * 
	 * @return An array of teams in this league.
	 */
	public Team[] getTeams() {
		for (Integer contestant : contestantIDs)
			teams.addTeam(this, contestant);
		return teams.toArray();
	}

	public List<Integer> getContestantIDs() {
		return contestantIDs;
	}

	/**
	 * The current Tournament ID for this league
	 * 
	 * @return The ID of the Tournament
	 */
	public int getTournamentID() {
		return tournamentID;
	}

	/**
	 * The current Series ID for this league
	 * 
	 * @return The ID of the Series
	 */
	public int getSeriesID() {
		return seriesID;
	}

	/**
	 * @return The ID of the League
	 */
	public abstract int getID();

	/**
	 * The Long-Hand name of the league
	 */
	public abstract String getLeagueName();

	/**
	 * The Short-Hand name of the League
	 */
	public abstract String getLeagueNameShort();
}
