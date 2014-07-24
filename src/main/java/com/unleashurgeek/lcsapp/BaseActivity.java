package com.unleashurgeek.lcsapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.adapters.NavDrawerExpandableListAdapter;
import com.unleashurgeek.lcsapp.adapters.NavDrawerList;
import com.unleashurgeek.lcsapp.adapters.NavDrawerListAdapter;
import com.unleashurgeek.lcsapp.adapters.NavDrawerListItem;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class BaseActivity extends FragmentActivity {
	
	// TODO: Reorganize this file
	
	protected DrawerLayout drawer;
	protected LayoutInflater inflater;
	protected   ActionBarDrawerToggle drawerToggle;
	private   ExpandableListView myTeamsList;
	private   ListView leagueList;
	
	private static final HashMap<String, List<String>> listDataChild;
	private static final ArrayList<String> listDataHeader;
	
	private NavDrawerList NAVITEMLIST[];	
	
	protected boolean isLeagueChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/** Set the Action bar to Transparent and allow a background to be overlayed. */
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
		getActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
		
		// TODO: Rewrite LeagueList Adapter
		List<NavDrawerList> items = new ArrayList<NavDrawerList>();
		items.add(NavDrawerListItem.create(100, "North America"));
		items.add(NavDrawerListItem.create(200, "Europe"));
		NAVITEMLIST = items.toArray(new NavDrawerList[items.size()]);
		items.clear();
		
		
		// USE TO WIPE PREFRENCES INCASE I FUCKED UP
		/** File sharedPreferenceFile = new File("/data/data/"+ getPackageName()+ "/shared_prefs/");
	    File[] listFiles = sharedPreferenceFile.listFiles();
	    for (File file : listFiles) {
	        file.delete();
	    }*/
		
		/** Set the BaseActivity Layout for the Navigation Drawer */
		setContentView(R.layout.navdrawer);
		this.inflater = LayoutInflater.from(getApplicationContext());
		
		drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
		
		leagueList = (ListView)findViewById(R.id.drawerList);
		NavDrawerListAdapter listAdapter = new NavDrawerListAdapter(getApplicationContext(), R.layout.navdrawer_list_item, NAVITEMLIST);
		leagueList.setAdapter(listAdapter);
		setListViewHeightBasedOnChildren(leagueList);
		int defaultLeague = getIntent().getIntExtra("league", Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(BaseActivity.this).getString("defLeague", getResources().getString(R.string.defaultLeague_default))));
		switch (defaultLeague) {
			case 0:
				leagueList.setItemChecked(0, true);
				break;
			case 1:
				leagueList.setItemChecked(1, true);
				break;
			default:
				leagueList.setItemChecked(0, true);
				break;
		}
		
		// TODO: Replace with a real TeamList.
		myTeamsList = (ExpandableListView)findViewById(R.id.myTeamsList);
		final NavDrawerExpandableListAdapter expandableListAdapter = new NavDrawerExpandableListAdapter(this, listDataHeader, listDataChild);
		myTeamsList.setAdapter(expandableListAdapter);
		if (savedInstanceState != null)
			for (int i = 0; i < expandableListAdapter.getGroupCount(); i++)
				myTeamsList.collapseGroup(i);
			
		setListViewHeightBasedOnChildren(myTeamsList);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		leagueList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(BaseActivity.this, LoadingActivity.class);
				switch (position) {
					case 0:
						if (DataCache.currentLeague != DataCache.leagueNA) {
							i.putExtra("league", 0);
							isLeagueChanged = true;
							startActivity(i);
							finish();
						}
						break;
					case 1:
						if (DataCache.currentLeague != DataCache.leagueEU) {
							i.putExtra("league", 1);
							isLeagueChanged = true;
							startActivity(i);
							finish();
						}
						break;
				}
			}
		});
		
		// TODO: Add teamList Child Click Listener
		
		myTeamsList.setOnGroupClickListener(new OnGroupClickListener() {
			
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				
				boolean isExpanded = myTeamsList.isGroupExpanded(groupPosition);
				if (isExpanded) {
					myTeamsList.collapseGroup(groupPosition);
					
					int totalHeight = 0;
					int dividerCount = 0;
		            for (int y = 0; y < expandableListAdapter.getChildrenCount(groupPosition); y++) {
		            	View childItem;
		            	if (y == (expandableListAdapter.getChildrenCount(groupPosition) - 1))
		            		childItem = expandableListAdapter.getChildView(groupPosition, y, true, null, myTeamsList);
		            	else
		            		childItem = expandableListAdapter.getChildView(groupPosition, y, false, null, myTeamsList);
		            	childItem.measure(0, 0);
		            	totalHeight -= childItem.getMeasuredHeight();
		            	dividerCount++;
		            }
	
			        ViewGroup.LayoutParams params = myTeamsList.getLayoutParams();
			        params.height += totalHeight + (myTeamsList.getDividerHeight() * (dividerCount - 1));
			        myTeamsList.setLayoutParams(params);
			        myTeamsList.requestLayout();
				} else {	
					myTeamsList.expandGroup(groupPosition);
					int totalHeight = 0;
					int dividerCount = 0;
		            for (int y = 0; y < expandableListAdapter.getChildrenCount(groupPosition); y++) {
		            	View childItem;
		            	if (y == (expandableListAdapter.getChildrenCount(groupPosition) - 1))
		            		childItem = expandableListAdapter.getChildView(groupPosition, y, true, null, myTeamsList);
		            	else
		            		childItem = expandableListAdapter.getChildView(groupPosition, y, false, null, myTeamsList);
		            	childItem.measure(0, 0);
		            	totalHeight += childItem.getMeasuredHeight();
		            	dividerCount++;
		            }
	
			        ViewGroup.LayoutParams params = myTeamsList.getLayoutParams();
			        params.height += totalHeight + (myTeamsList.getDividerHeight() * (dividerCount - 1));
			        myTeamsList.setLayoutParams(params);
			        myTeamsList.requestLayout();
				}
				
				return true;
			}
		});
		
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				onDrawerClose();
				invalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				onDrawerOpen();
				isLeagueChanged = false;
				invalidateOptionsMenu();
			}
		};
		drawer.setDrawerListener(drawerToggle);
	}
	
	public abstract void onDrawerClose();
	public abstract void onDrawerOpen();
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Toggle nav drawer on selecting action bar app icon/title
    	if (drawerToggle.onOptionsItemSelected(item))
    		return true;
    	
    	switch (item.getItemId()) {
    		case R.id.action_settings:
    			Intent i = new Intent(this, SettingsActivity.class);
    			startActivity(i);
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = drawer.isDrawerOpen(findViewById(R.id.drawerLinearLayout));
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
    	super.onPostCreate(savedInstanceState);
    	drawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	drawerToggle.onConfigurationChanged(newConfig);
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
    
    static {
    	listDataHeader = new ArrayList<String>();
    	listDataChild = new HashMap<String, List<String>>();

    	listDataHeader.add("My Teams");
    	//TODO: Replace with Dynamic data.
    	List<String> teams = new ArrayList<String>();
    	teams.add("Team Solo-Mid");
    	teams.add("Cloud9");
    	teams.add("Fnatic");
    	listDataChild.put(listDataHeader.get(0), teams);
    }
}
