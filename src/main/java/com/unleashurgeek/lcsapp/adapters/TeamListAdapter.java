package com.unleashurgeek.lcsapp.adapters;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.api.Team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamListAdapter extends ArrayAdapter<Team> {
	
	private LayoutInflater inflater;
	
	public TeamListAdapter(Context context, int textViewResourceId, Team[] teams) {
		super(context, textViewResourceId, teams);
		this.inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Team team = (Team)this.getItem(position);
		TeamListItemHolder menuItemHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_home_teams_item, parent, false);
			TextView teamName = (TextView)convertView.findViewById(R.id.teamName);
			ImageView teamLogo = (ImageView)convertView.findViewById(R.id.teamLogo);
			
			menuItemHolder = new TeamListItemHolder();
			menuItemHolder.teamName = teamName;
			menuItemHolder.teamLogo = teamLogo;
			convertView.setTag(menuItemHolder);
		}
		if (menuItemHolder == null) {
			menuItemHolder = (TeamListItemHolder)convertView.getTag();
		}
		
		menuItemHolder.teamName.setText(team.getName());
		menuItemHolder.teamLogo.setImageBitmap(team.getLogo());
		return convertView;
	}
	
	private static class TeamListItemHolder {
		private TextView teamName;
		private ImageView teamLogo;
	}
}
