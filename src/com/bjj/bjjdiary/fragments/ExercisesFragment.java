package com.bjj.bjjdiary.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bjj.bjjdiary.R;
import com.bjj.bjjdiary.activities.ExerciseNewActivity;
import com.bjj.bjjdiary.activities.ExerciseViewActivity;
import com.bjj.bjjdiary.db.BjjDiaryContentProvider;
import com.bjj.bjjdiary.db.BjjDiaryDBHelper;

public class ExercisesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
  
  private SimpleCursorAdapter adapter;
  
  @Override
  public View onCreateView(LayoutInflater inflater,
          ViewGroup container, Bundle savedInstanceState) {
      // The last two arguments ensure LayoutParams are inflated
      // properly.
      View rootView = inflater.inflate(R.layout.exercise_fragment, container, false);
      
      adapter = new SimpleCursorAdapter(this.getActivity(),R.layout.exercise_row,null, new String[] { BjjDiaryDBHelper.FIELD_NAME }, new int[]{ R.id.row_name},0);
      this.setListAdapter(adapter);
      
      setHasOptionsMenu(true);
      
      this.getLoaderManager().initLoader(0, null, this);
      
      return rootView;
  }
  
  @Override
  public void onListItemClick (ListView l, View v, int position, long id){
    super.onListItemClick(l, v, position, id);
    Intent i = new Intent(this.getActivity(), ExerciseViewActivity.class);
    i.putExtra(BjjDiaryDBHelper.KEY_ID, id);
    startActivity(i);
  }
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      // TODO Auto-generated method stub
      super.onCreateOptionsMenu(menu, inflater);
      inflater.inflate(R.layout.exercise_menu, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
     // handle item selection
     switch (item.getItemId()) {
        case R.id.action_settings:
           // do s.th.
           return true;
        case R.id.action_new:
          Intent newIncome = new Intent(this.getActivity(), ExerciseNewActivity.class);
          this.startActivity(newIncome);
          return true;
            
        default:
           return super.onOptionsItemSelected(item);
     }
  }

  @Override
  public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
    return new CursorLoader(this.getActivity(), BjjDiaryContentProvider.EXERCISE_URI, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
    adapter.swapCursor(cursor);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> arg0) {
    adapter.swapCursor(null);
  } 
}
