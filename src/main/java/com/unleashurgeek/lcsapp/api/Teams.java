package com.unleashurgeek.lcsapp.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

public class Teams implements Serializable {
	private static final long serialVersionUID = -5389690197391495286L;
	
	private SparseArray<Team> teams = null;
	
	public Teams() {
		teams = new SparseArray<Team>(); 
	}
	
	public Team[] toArray() {
		List<Team> teamsList = new ArrayList<Team>();
		for (int i = 0; i < teams.size(); i++) {
			teamsList.add(teams.valueAt(i));
		}
		return teamsList.toArray(new Team[teamsList.size()]);
	}
	
	/**
	 * @param teamId The ID of the Team
	 * @return The Team with the given ID. Null if not found
	 */
	public Team getTeam(int teamId) {
		return teams.get(teamId);
	}
	
	/**
	 * Removes the team from the memory, if he ever existed.
	 * 
	 * @param teamId The team ID of the team to get Removed
	 */
	public void removeTeam(int teamId) {
		teams.delete(teamId);
	}
	
	/**
	 * Removes the team from the memory
	 * 
	 * @param team the Team class of the team to get Removed
	 */
	public void removeTeam(Team team) {
		teams.delete(team.getID());
	}
	
	/**
	 * Gets a Team by its name.
	 * 
	 * @param name The name of the team
	 * @return The team or null if team was not found.
	 */
	public Team getTeamByName(String name) {
		name = name.trim();
		for (int i = 0; i < teams.size(); i++) {
			Team t = teams.valueAt(i);
			if (t.getName().equalsIgnoreCase(name))
				return t;
		}
		return null;
	}
	
	/**
	 * Checks to see if the specified team exists.
	 * 
	 * @param teamId The team ID of the team in question.
	 * @return True if team Exists, false otherwise.
	 */
	public boolean doesTeamExist(int teamId) {
		return teams.indexOfKey(teamId) >= 0 ? true : false;
	}
	
	/**
	 * Checks to see if the specified team exists.
	 * 
	 * @param team The team in question.
	 * @return True if team Exists, false otherwise.
	 */
	public boolean doesTeamExist(Team team) {
		return teams.indexOfKey(team.getID()) >= 0 ? true : false;
	}
	
	/**
	 * Adds team to the array, will override team if already exits.
	 *
	 * @param team The team to add to the array.
	 */
	public void addTeam(Team team) {
		teams.put(team.getID(), team);
	}
	
	/**
	 * Adds the Team to the sparse array and generates their data. if team is already in the array, this does nothing.
	 * 
	 * @param league The League this team is in.
	 * @param id The id of the team you want to add.
	 */
	public void addTeam(League league, Integer id) {
		if (!doesTeamExist(id))
			teams.put(id, new Team(league, id));
	}
}
