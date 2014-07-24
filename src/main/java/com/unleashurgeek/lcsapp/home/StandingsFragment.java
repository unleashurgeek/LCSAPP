package com.unleashurgeek.lcsapp.home;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.DataCache;
import com.unleashurgeek.lcsapp.api.Team;
import com.unleashurgeek.lcsapp.api.TeamStandings;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;

public class StandingsFragment extends LcsFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.fragment_home_standings, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		new LoadAsyncTask().execute(view);
	}
	
	private class LoadAsyncTask extends AsyncTask<View, Void, View> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// TODO: Add loading animation.
		}
		
		@Override
		protected View doInBackground(View... params) {
			TeamStandings.pollStandings(DataCache.currentLeague, DataCache.currentLeague.getTeamsArray());
			Team[] teams = DataCache.currentLeague.getTeams();
			for (Team team : teams) {
				team.getLogo();
			}
			return params[0];
		}
		
		@Override
		protected void onPostExecute(View result) {
			super.onPostExecute(result);
			Team[] teams = DataCache.currentLeague.getTeams();
			Arrays.sort(teams);
			for (Team team : teams) {
				addTableRow(result, team);
			}
		}
	}
	
	
	private void addTableRow(View view, Team team) {
		view.getContext();
		LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		final TableLayout table = (TableLayout)view.findViewById(R.id.standingsTable);
		final TableRow tr = (TableRow)inflater.inflate(R.layout.fragment_home_standings_row, table, false);
		
		TextView tv;
		tv = (TextView)tr.findViewById(R.id.standingsRowRank);
		tv.setText("" + team.getRank());
		tv = (TextView)tr.findViewById(R.id.standingsRowRankChange);
		tv.setText(team.getRankChange());
		// TODO: Fix to show correct Color
		if (team.getRankChange().contains("+"))
			tv.setTextColor(getResources().getColor(R.color.green));
		else if (team.getRankChange().contains("-"))
			tv.setTextColor(getResources().getColor(R.color.red));
		tv = (TextView)tr.findViewById(R.id.standingsRowTeamName);
		tv.setText(team.getName());
		tv = (TextView)tr.findViewById(R.id.standingsRowWins);
		tv.setText("" + team.getWins());
		tv = (TextView)tr.findViewById(R.id.standingsRowLosses);
		tv.setText("" + team.getLosses());
		
		ImageView image = (ImageView)tr.findViewById(R.id.standingsRowTeamLogo);
		image.setImageBitmap(team.getLogo());
		table.addView(tr);
		
		tv = new TextView(view.getContext());
		tv.setBackgroundColor(Color.parseColor("#6F6F6F"));
		tv.setHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
		table.addView(tv);
	}

	@Override
	public String getTitle() {
		return "Standings";
	}
}
