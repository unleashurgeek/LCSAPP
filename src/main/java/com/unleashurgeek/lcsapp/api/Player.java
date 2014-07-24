package com.unleashurgeek.lcsapp.api;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class Player implements Serializable {
	private static final long serialVersionUID = 3248110782384754780L;
	
	private int id;
	private Team team;
	private String ign;
	private String bio;
	private String firstName;
	private String lastName;
	private String hometown;
	private String role;
	private int roleID;
	private String photoURL; 
	
	
	public Player(Team team, final int id) {
		this.id = id;
		this.team = team;
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				final String query = EsportsRequestHandler.makeRequest("/player/" + id + ".json");
				try {
					JSONObject player = new JSONObject(query);
					ign = player.getString("name");
					bio = player.getString("bio");
					firstName = player.getString("firstname");
					lastName = player.getString("lastName");
					hometown = player.getString("hometown");
					role = player.getString("role");
					roleID = player.getInt("roleId");
					
					photoURL = player.getString("photoUrl");
					
					// TODO: ADD STATS PROCESSING
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				return null;
			}	
		}.execute();
	}
	
	public int getID() {
		return id;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public String getIGN() {
		return ign;
	}
	
	public String getBio() {
		return bio;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getHometown() {
		return hometown;
	}
	
	public String getRole() {
		return role;
	}
	
	public int getRoleID() {
		return roleID;
	}
	
	public Bitmap getPhoto() {
		return team.getLeague().getCache().getImage(photoURL);
	}
}
