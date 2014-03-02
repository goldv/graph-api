package com.bjj.bjjdiary.activities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bjj.bjjdiary.MainActivity;
import com.bjj.bjjdiary.R;
import com.bjj.bjjdiary.activities.ExerciseViewTimer.ExerciseViewTimerListener;
import com.bjj.bjjdiary.db.BjjDiaryContentProvider;
import com.bjj.bjjdiary.db.BjjDiaryDBHelper;
import com.bjj.bjjdiary.fragments.NumberPickerDialog;
import com.bjj.bjjdiary.fragments.NumberPickerDialog.NumberPickerListener;

public class ExerciseViewActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, NumberPickerListener, ExerciseViewTimerListener{
  
  private static final String KEY_DURATION = "DURATION"; 
  private static final String KEY_RUNNING_DURATION = "RUNNING_DURATION"; 
  private static final String KEY_TIMER_RUNNING = "TIMER_RUNNING";
  private static final String KEY_SETS = "SETS";
  private static final String KEY_REPS = "REPS";
    
  private static final int DURATION_PICKER = 0;
  private static final int SETS_PICKER = 1;
  private static final int REPS_PICKER = 2;
  
  private int repetitions = 0;
  private int sets = 0;
  
  private ExerciseViewTimer mTimer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exercise_view);
    // Show the Up button in the action bar.
    setupActionBar();
    
    TextView durationView = (TextView)findViewById(R.id.exercise_view_duration);
    durationView.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0) {
        DialogFragment newFragment = NumberPickerDialog.newInstance(DURATION_PICKER);
        newFragment.show(getSupportFragmentManager(), "duration");
      }
    });
    
    TextView setsView = (TextView)findViewById(R.id.exercise_view_sets);
    setsView.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0) {
        DialogFragment newFragment = NumberPickerDialog.newInstance(SETS_PICKER);
        newFragment.show(getSupportFragmentManager(), "sets");
      }
    });
    
    TextView repsView = (TextView)findViewById(R.id.exercise_view_reps);
    repsView.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0) {
        DialogFragment newFragment = NumberPickerDialog.newInstance(REPS_PICKER);
        newFragment.show(getSupportFragmentManager(), "reps");
      }
    });
    
    sets = savedInstanceState != null ? savedInstanceState.getInt(KEY_SETS) : 0;
    repetitions = savedInstanceState != null ? savedInstanceState.getInt(KEY_REPS) : 0;
    
    int running_duration = savedInstanceState != null ? savedInstanceState.getInt(KEY_RUNNING_DURATION) : 0;
    int duration = savedInstanceState != null ? savedInstanceState.getInt(KEY_DURATION) : 0;
    boolean timer_running = savedInstanceState != null ? savedInstanceState.getBoolean(KEY_TIMER_RUNNING) : false;
        
    mTimer = new ExerciseViewTimer(duration, running_duration, this);
    if(timer_running)mTimer.start();
    
    initialiseViews();
    
    final Button start_stop_button = (Button)findViewById(R.id.duration_start_stop_button);
    final Button reset_button = (Button)findViewById(R.id.duration_reset_button);
    
    start_stop_button.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0) {
        if(mTimer.isRunning()){
          mTimer.stop();
          start_stop_button.setText(R.string.duration_start_button);
        }
        else{
          if(mTimer.start()){
            start_stop_button.setText(R.string.duration_stop_button);
          }
        }
      }
    });
    
    reset_button.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View v) {
        mTimer.reset();
        updateDurationView();
      }
    });
    
    Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
    getSupportLoaderManager().restartLoader(0, bundle, this);
  }
    
  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    Long id = getIntent().getLongExtra(BjjDiaryDBHelper.KEY_ID, -1);
    if(id >= 0){
      savedInstanceState.putLong(BjjDiaryDBHelper.KEY_ID, id);
    }
    
    savedInstanceState.putInt(KEY_SETS, sets);
    savedInstanceState.putInt(KEY_REPS, repetitions);
    savedInstanceState.putInt(KEY_DURATION, mTimer.getDuration());
    savedInstanceState.putInt(KEY_RUNNING_DURATION, mTimer.getRunningDuration());
    savedInstanceState.putBoolean(KEY_TIMER_RUNNING, mTimer.isRunning());
      
    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(savedInstanceState);
  }
  
  @Override
  public void onPause(){
    super.onPause();
    mTimer.stop();
  }

  /**
   * Set up the {@link android.app.ActionBar}.
   */
  private void setupActionBar() {
    getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.exercise_view, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case android.R.id.home:
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. Use NavUtils to allow users
      // to navigate up one level in the application structure. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //
      NavUtils.navigateUpFromSameTask(this);
      return true;
    case R.id.action_remove:
      ContentResolver cr = getContentResolver();
      Long id = getIntent().getLongExtra(BjjDiaryDBHelper.KEY_ID, -1);
      
      if(id >= 0){
        Uri rowAddress = ContentUris.withAppendedId(BjjDiaryContentProvider.EXERCISE_URI, id);
        cr.delete(rowAddress, null, null);
      }
      
      Intent intent = new Intent(this,MainActivity.class);
      startActivity(intent);
      
      return true;
    }
    
    return super.onOptionsItemSelected(item);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
    Long id = bundle.getLong(BjjDiaryDBHelper.KEY_ID);
    Uri rowAddress = ContentUris.withAppendedId(BjjDiaryContentProvider.EXERCISE_URI, id);
    return new CursorLoader(this, rowAddress, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    if(cursor.moveToNext()){
      String name = cursor.getString(cursor.getColumnIndexOrThrow(BjjDiaryDBHelper.FIELD_NAME));
      String description = cursor.getString(cursor.getColumnIndexOrThrow(BjjDiaryDBHelper.FIELD_DESCRIPTION));
      
      TextView descriptionView = (TextView)findViewById(R.id.exercise_view_description);
      
      descriptionView.setText(description);
      setTitle(name);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    setTitle("");
  }

  @Override
  public void onDialogPositiveClick(NumberPickerDialog dialog) {
    switch(dialog.getDialogId()){
      case DURATION_PICKER:
        mTimer.setTime(dialog.getNumber() * 60);
        updateDurationView();
        break;
      case SETS_PICKER:
        sets = dialog.getNumber();
        updateSetsView();
        break;
      case REPS_PICKER:
        repetitions = dialog.getNumber();
        updateRepsView();
        break;
      default:
        Log.e("EV", "Unknow dialog id " + dialog.getDialogId());
    }
  }
  
  @Override
  public void onDialogNegativeClick(NumberPickerDialog dialog) {
    // nothing to do
  }
      
  private void initialiseViews(){
    updateDurationView();
    updateSetsView();
    updateRepsView();
  }
  
  private void updateDurationView(){
    int remaining[] = mTimer.timeRemaining();
    TextView durationView = (TextView)findViewById(R.id.exercise_view_duration);
    String duration_str = formatDuration(remaining[0], remaining[1]);
    durationView.setText(duration_str);
  }
  
  private void updateSetsView(){
    TextView setsView = (TextView)findViewById(R.id.exercise_view_sets);
    setsView.setText(String.valueOf(sets));
  }
  
  private void updateRepsView(){
    TextView repsView = (TextView)findViewById(R.id.exercise_view_reps);
    repsView.setText(String.valueOf(repetitions));
  }
  
  private String formatDuration(int minutes, int seconds){
    String min = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
    String sec = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
    return min + ":" + sec;
  }
  
  @Override
  public void onFinish() {
    // TODO sound alarm
  }

  @Override
  public void onTick(long millisUntilFinished) {
    updateDurationView();
  }

}
