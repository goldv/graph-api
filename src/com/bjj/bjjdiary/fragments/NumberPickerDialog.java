package com.bjj.bjjdiary.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.bjj.bjjdiary.R;

public class NumberPickerDialog extends DialogFragment{
 
  public interface NumberPickerListener {
    public void onDialogPositiveClick(NumberPickerDialog dialog);
    public void onDialogNegativeClick(NumberPickerDialog dialog);
  }
  
  private int dialogId;
  
  private NumberPicker np;
  
  NumberPickerListener mListener;
    
  public static NumberPickerDialog newInstance(int id) {
    NumberPickerDialog frag = new NumberPickerDialog();
    Bundle args = new Bundle();
    args.putInt("id", id);
    frag.setArguments(args);
    return frag;
  }
   
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    
      dialogId = getArguments().getInt("id");
      
      // Use the Builder class for convenient dialog construction
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      
      LayoutInflater inflater = getActivity().getLayoutInflater();
      
      View view = inflater.inflate(R.layout.exercise_view_number_picker_dialog, null);
      
      np = (NumberPicker) view.findViewById(R.id.numberPicker);
      np.setMinValue(0);
      np.setMaxValue(60);
      
      builder.setView(view)
             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                   NumberPickerDialog.this.mListener.onDialogPositiveClick(NumberPickerDialog.this);
                 }
             })
             .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                   NumberPickerDialog.this.mListener.onDialogNegativeClick(NumberPickerDialog.this);
                 }
             });
      // Create the AlertDialog object and return it
      return builder.create();
  }
  
  @Override
  public void onAttach(Activity activity){
    super.onAttach(activity);
    // Verify that the host activity implements the callback interface
    try {
        // Instantiate the NoticeDialogListener so we can send events to the host
        mListener = (NumberPickerListener) activity;
    } catch (ClassCastException e) {
        // The activity doesn't implement the interface, throw exception
        throw new ClassCastException(activity.toString()
                + " must implement NoticeDialogListener");
    }
  }
  
  public int getNumber(){
    return np != null ? np.getValue() : 0;
  }
  
  public int getDialogId(){ return dialogId; }

}
