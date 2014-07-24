package com.unleashurgeek.lcsapp;

import com.nineoldandroids.view.animation.AnimatorProxy;
import com.unleashurgeek.R;
import com.unleashurgeek.lcsapp.fragment.FragmentAdapter;
import com.unleashurgeek.lcsapp.fragment.LcsFragment;
import com.unleashurgeek.lcsapp.home.*;
import com.unleashurgeek.lcsapp.widgets.SlidingUpPanelLayout;
import com.unleashurgeek.lcsapp.widgets.SlidingUpPanelLayout.PanelSlideListener;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class HomeActivity extends BaseActivity {
	
	HomeViewPager homePager = null;
	private final LcsFragment homeFragList[] = {
		new LiveFragment(),
		// TODO: re-add news when I feel like news is important
		new StandingsFragment(),
		new TeamsFragment(),
		new StatsFragment()
	};
	private SlidingUpPanelLayout slidingLayout;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = inflater.inflate(R.layout.activity_home, drawer, false);
		drawer.addView(contentView, 0);
				
		homePager = (HomeViewPager)findViewById(R.id.homePager);
		FragmentManager fragmentManager = getSupportFragmentManager();
		homePager.setAdapter(new FragmentAdapter(fragmentManager, homeFragList));
		
		slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
		slidingLayout.setPanelSlideListener(new PanelSlideListener() {
			
			public void onPanelSlide(View panel, float slideOffset) {
				if (slideOffset > 0.8) {
					if (getActionBar().isShowing()) {
						getActionBar().hide();
					}
				} else {
					if (!getActionBar().isShowing()) {
						getActionBar().show();
					}
				}
				//TODO: Fix it so it wont move content - setActionBarTranslation(slidingLayout.getCurrentParalaxOffset());
			}
			
			public void onPanelHidden(View panel) {	}
			
			public void onPanelExpanded(View panel) { }
			
			public void onPanelCollapsed(View panel) { }
			
			public void onPanelAnchored(View panel) { }
		});
	}
	
	@Override
	public void onBackPressed() {
		if (slidingLayout != null && slidingLayout.isPanelExpanded() || slidingLayout.isPanelAnchored()) {
			slidingLayout.collapsePanel();
		} else {
			super.onBackPressed();
		}
	}
	
	private int getActionBarHeight() {
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
	}
	
	public void setActionBarTranslation(float y) {
        // Figure out the actionbar height
        int actionBarHeight = getActionBarHeight();
        // A hack to add the translation to the action bar
        ViewGroup content = ((ViewGroup) findViewById(android.R.id.content).getParent());
        int children = content.getChildCount();
        for (int i = 0; i < children; i++) {
            View child = content.getChildAt(i);
            if (child.getId() != android.R.id.content) {
                if (y <= -actionBarHeight) {
                    child.setVisibility(View.GONE);
                } else {
                    child.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        child.setTranslationY(y);
                    } else {
                        AnimatorProxy.wrap(child).setTranslationY(y);
                    }
                }
            }
        }
    }

	@Override
	public void onDrawerClose() {
		if (this.isLeagueChanged) {
			for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
				Fragment frg = getSupportFragmentManager().getFragments().get(i);
				if (frg != null && frg.isVisible()) {
					final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.detach(frg);
					ft.attach(frg);
					ft.commit();
				}
			}
		}
	}

	@Override
	public void onDrawerOpen() {
	}
}