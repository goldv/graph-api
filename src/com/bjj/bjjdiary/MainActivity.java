package com.bjj.bjjdiary;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.bjj.bjjdiary.adapter.CollectionPagerAdapter;

public class MainActivity extends FragmentActivity  implements ActionBar.TabListener {
	
  CollectionPagerAdapter mDemoCollectionPagerAdapter;
  ViewPager mViewPager;
  
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
  
      // ViewPager and its adapters use support library
      // fragments, so use getSupportFragmentManager.
      mDemoCollectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager());
      
      // Set up action bar.
      final ActionBar actionBar = getActionBar();

      // Specify that the Home button should show an "Up" caret, indicating that touching the
      // button will take the user one step up in the application's hierarchy.
      actionBar.setDisplayHomeAsUpEnabled(false);
      
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      
      
      
      mViewPager = (ViewPager)findViewById(R.id.view_pager);
      
      Log.i("MAIN_ACT","mViewPager: " + mViewPager);
      
      mViewPager.setAdapter(mDemoCollectionPagerAdapter);
      
      mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            // When swiping between different app sections, select the corresponding tab.
            // We can also use ActionBar.Tab#select() to do this if we have a reference to the
            // Tab.
            actionBar.setSelectedNavigationItem(position);
        }
      });
      
      // For each of the sections in the app, add a tab to the action bar.
      for (int i = 0; i < mDemoCollectionPagerAdapter.getCount(); i++) {
          // Create a tab with text corresponding to the page title defined by the adapter.
          // Also specify this Activity object, which implements the TabListener interface, as the
          // listener for when this tab is selected.
          actionBar.addTab(actionBar.newTab().setText(mDemoCollectionPagerAdapter.getPageTitle(i)).setTabListener(this));
      }
      
  }

  @Override
  public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction arg1) {
    // When the given tab is selected, switch to the corresponding page in the ViewPager.
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    // TODO Auto-generated method stub
    
  }
}
