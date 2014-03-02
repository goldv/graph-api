package com.bjj.bjjdiary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bjj.bjjdiary.fragments.ExercisesFragment;
import com.bjj.bjjdiary.fragments.RoutinesFragment;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter{
  
  public CollectionPagerAdapter(FragmentManager fm) {
    super(fm);
  }
  
  @Override
  public Fragment getItem(int i) {
    switch(i){
      case 0: return new ExercisesFragment();
      case 1: return new RoutinesFragment();
      case 2: return new RoutinesFragment();
      default: throw new IllegalArgumentException();
    }
  }
  
  @Override
  public int getCount() {
      return 3;
  }
  
  @Override
  public CharSequence getPageTitle(int position) {
    switch(position){
      case 0:return "Tags";
      case 1:return "Exercises";
      case 2:return "Routines";
    }
      return "OBJECT " + (position + 1);
  }


}
