package com.unleashurgeek.lcsapp.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.DataCache;
import com.unleashurgeek.lcsapp.adapters.TeamListAdapter;
import com.unleashurgeek.lcsapp.api.Team;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;

public class TeamsFragment extends LcsFragment {
	//private static final TeamListItem TEAMS[];
	ListView teamList = null;
	Context context;
	
	//private static LoadAsyncTask task;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_teams, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.context = view.getContext();
		teamList = (ListView)view.findViewById(R.id.teamList);
		/*if (task == null)
			task = new LoadAsyncTask();
		if (task.getStatus() != AsyncTask.Status.RUNNING)
			task.execute();*/
		new LoadAsyncTask().execute();
	}
	
	private class LoadAsyncTask extends AsyncTask<Void, Void, Team[]> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			// TODO: Add Loading animation.
		}
		
		@Override
		protected Team[] doInBackground(Void... params) {
			Team[] teams = DataCache.currentLeague.getTeams();
			for (Team team : teams) {
				team.getLogo();
			}
			return teams;
		}
		
		@Override
		protected void onPostExecute(Team[] result) {
			super.onPostExecute(result);
			
			final TeamListAdapter adapter = new TeamListAdapter(context, R.layout.fragment_home_teams_item, result);
			teamList.setAdapter(adapter);
			setListViewHeightBasedOnChildren(teamList);
		}
		
	}
	
	@Override
	public String getTitle() {
		return "Teams";
	}
	
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
