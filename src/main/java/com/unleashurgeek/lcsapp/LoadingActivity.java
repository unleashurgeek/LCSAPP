package com.unleashurgeek.lcsapp;

import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.api.ImageCache;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LoadingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int league = getIntent().getIntExtra("league", Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(LoadingActivity.this).getString("defLeague", getResources().getString(R.string.defaultLeague_default))));
		// TODO: Add Loading Layout.
		
		new AsyncLoader().execute(league);
	}
	
	private class AsyncLoader extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {	
			if (DataCache.imageCache == null)
				DataCache.imageCache = new ImageCache();
			int defLeague = params[0];
			switch (defLeague) {
				case 0:
					DataCache.setLeague("na");
					break;
				case 1:
					DataCache.setLeague("eu");
					break;
				default:
					DataCache.setLeague("na");
					break;
			}			
			return defLeague;
		}
		
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
			intent.putExtra("league", result);
			startActivity(intent);
			finish();
		}

	}
}
