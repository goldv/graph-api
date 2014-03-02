package com.bjj.bjjdiary.activities;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.bjj.bjjdiary.MainActivity;
import com.bjj.bjjdiary.R;
import com.bjj.bjjdiary.db.BjjDiaryContentProvider;
import com.bjj.bjjdiary.db.BjjDiaryDBHelper;

public class ExerciseNewActivity extends Activity{
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
  
      setContentView(R.layout.exercise_new);
  }
  
  private void saveExercise(){
    ContentResolver cr = getContentResolver();
    
    ContentValues cv = new ContentValues();
    EditText name = (EditText)findViewById(R.id.name);
    EditText description = (EditText)findViewById(R.id.description);
    
    cv.put(BjjDiaryDBHelper.FIELD_NAME, name.getText().toString());
    cv.put(BjjDiaryDBHelper.FIELD_DESCRIPTION, description.getText().toString());
    
    cr.insert(BjjDiaryContentProvider.EXERCISE_URI, cv);
    
    Intent mainAct = new Intent(ExerciseNewActivity.this, MainActivity.class);
    ExerciseNewActivity.this.startActivity(mainAct);
    
    setResult(RESULT_OK);
    finish();
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle item selection
      switch (item.getItemId()) {
          case R.id.action_save:
            saveExercise();
            return true;
          default:
            return super.onOptionsItemSelected(item);
      }
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.layout.exercise_new_menu, menu);
      return true;
  }
}
