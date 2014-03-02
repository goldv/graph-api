package com.bjj.bjjdiary.activities;

import android.os.CountDownTimer;
import android.util.Log;

public class ExerciseViewTimer {
    
  public interface ExerciseViewTimerListener{
    public void onFinish();
    public void onTick(long millisUntilFinished);
  }
  
  private static final int INTERVAL = 1000;
  
  private boolean isRunning;
  private int duration;
  private int running_duration;
  
  private final ExerciseViewTimerListener mListener;
  
  private CountDownTimer timer;
  
  public ExerciseViewTimer(int duration, int running_duration, ExerciseViewTimerListener mListener){
    this.duration = duration;
    this.running_duration = running_duration;
    this.mListener = mListener;
  }
      
  public boolean start(){
    if(duration > 0){
      setTime(duration);
      isRunning = true;
      timer.start();
      return true;
    }
    
    return false;
  }
  
  public void stop(){
    cancel();
  }
  
  public void reset(){
    if(!isRunning){
      running_duration = 0;
      setTime(duration);
    }
  }
  
  public void setTime(int duration){
    if(!isRunning && duration > 0){
      this.duration = duration;
      timer = new CountDownTimer( (duration * 1000) - (running_duration * 1000), INTERVAL){
        @Override
        public void onFinish() {
          running_duration += 1;
          mListener.onFinish(); 
        }
        @Override
        public void onTick(long millisUntilFinished) {
          running_duration += 1;
          mListener.onTick(millisUntilFinished); 
        }
      };
    }
  }
  
  private void cancel(){
    if(isRunning && timer != null){
      timer.cancel();
      isRunning = false;
    }
  }
  
  public boolean isRunning() {
    return isRunning;
  }
  
  public int[] timeRemaining(){
    int[] result = new int[2];
    
    int secondsRemaining = duration - running_duration;
    
    result[0] = secondsRemaining / 60;
    result[1] = secondsRemaining % 60;
    
    return result;
  }

  public int getDuration() {
    return duration;
  }

  public int getRunningDuration() {
    return running_duration;
  }

}
