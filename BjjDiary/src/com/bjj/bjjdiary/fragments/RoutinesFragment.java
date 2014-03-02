package com.bjj.bjjdiary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bjj.bjjdiary.R;

public class RoutinesFragment extends Fragment{
  
  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
      // The last two arguments ensure LayoutParams are inflated
      // properly.
      View rootView = inflater.inflate(R.layout.routine_fragment, container, false);
      
      setHasOptionsMenu(true);
      
      return rootView;
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.layout.routine_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
     // handle item selection
     switch (item.getItemId()) {
        case R.id.action_settings:
           // do s.th.
           return true;
        default:
           return super.onOptionsItemSelected(item);
     }
  }
  
}