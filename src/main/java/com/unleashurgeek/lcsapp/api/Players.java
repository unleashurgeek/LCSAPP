package com.unleashurgeek.lcsapp.api;

import java.io.Serializable;

import android.util.SparseArray;

public class Players implements Serializable {
	private static final long serialVersionUID = 2124281908920269719L;
	
	private SparseArray<Player> players = null;
	
	public Players() {
		players = new SparseArray<Player>(); 
	}
	
	
	/**
	 * @param playerId The ID of the Player
	 * @return The Player with the given ID. Null if not found
	 */
	public Player getPlayer(int playerId) {
		return players.get(playerId);
	}
	
	/**
	 * Removes the player from the memory, if he ever existed.
	 * 
	 * @param playerId The player ID of the player to get Removed
	 */
	public void removePlayer(int playerId) {
		players.delete(playerId);
	}
	
	/**
	 * Removes the player from the memory
	 * 
	 * @param player the Player class of the player to get Removed
	 */
	public void removePlayer(Player player) {
		players.delete(player.getID());
	}
	
	/**
	 * Checks to see if the specified player exists.
	 * 
	 * @param playerId The player ID of the player in question.
	 * @return True if player Exists, false otherwise.
	 */
	public boolean doesPlayerExist(int playerId) {
		return players.indexOfKey(playerId) >= 0 ? true : false;
	}
	
	/**
	 * Checks to see if the specified player exists.
	 * 
	 * @param player The player in question.
	 * @return True if player Exists, false otherwise.
	 */
	public boolean doesPlayerExist(Player player) {
		return players.indexOfKey(player.getID()) >= 0 ? true : false;
	}
	
	/**
	 * Adds player to the array, will override player if already exits.
	 *
	 * @param player The player to add to the array.
	 */
	public void addPlayer(Player player) {
		players.put(player.getID(), player);
	}
	
	
	/**
	 * Overrides old player with new player. Will add player if he does not exist.
	 * 
	 * @param player The player to be refreshed.
	 */
	public void refreshPlayer(Player player) {
		players.put(player.getID(), player);
	}
}
