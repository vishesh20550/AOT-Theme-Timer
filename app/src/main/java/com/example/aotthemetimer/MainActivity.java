package com.example.aotthemetimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    long time_millis=0;
    String hour_entered, minute_entered,seconds_entered;
    String[] time;
    MediaPlayer mediaPlayer;
    Button button;
    TextView textView;
    EditText editText,editText1,editText2;
    CountDownTimer countDownTimer;
    boolean counter_active=false;

    @SuppressLint("SetTextI18n")
    public void startTimer(View view) {
        if (!counter_active) {
            setTime_millis();
            updateTimer((int)time_millis/1000);
            editText.setVisibility(View.INVISIBLE);
            editText1.setVisibility(View.INVISIBLE);
            editText2.setVisibility(View.INVISIBLE);
            button.setText("Restart");
            counter_active = true;
            countDownTimer = new CountDownTimer(time_millis + 300, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.song);
                    mediaPlayer.start();
                    textView.setText("Times Up");
                    button.setText("Restart");
                }
            }.start();
        }
        else{
            stop();
        }
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
        inputMethodManager.hideSoftInputFromWindow(editText1.getWindowToken(),0);
        inputMethodManager.hideSoftInputFromWindow(editText2.getWindowToken(),0);
    }



    public void setTime_millis(){
        hour_entered = editText.getText().toString();
        minute_entered = editText1.getText().toString();
        seconds_entered = editText2.getText().toString();
        time = new String[]{"0", "0", "0"};
        if(!hour_entered.equals(""))
            time[0]=hour_entered;
        if(!minute_entered.equals(""))
            time[1]=minute_entered;
        if(!seconds_entered.equals(""))
            time[2]=seconds_entered;
        time_millis = ((((Long.parseLong(time[0]) * 60) + Long.parseLong(time[1])) * 60) + Long.parseLong(time[2])) * 1000;
    }



    @SuppressLint("SetTextI18n")
    public void stop(){
        textView.setText("00:00:00");
        editText.setText(null);
        editText1.setText(null);
        editText2.setText(null);
        editText.setHint("hh");
        editText1.setHint("mm");
        editText2.setHint("ss");
        editText.setVisibility(View.VISIBLE);
        editText1.setVisibility(View.VISIBLE);
        editText2.setVisibility(View.VISIBLE);
        countDownTimer.cancel();
        if(mediaPlayer!=null)
            mediaPlayer.stop();
        time_millis = 0;
        button.setText("Start");
        counter_active = false;
    }



    @SuppressLint("SetTextI18n")
    public void updateTimer(int seconds_left) {
        int hours = seconds_left / 3600;
        int minutes = (seconds_left / 60) - (hours * 60);
        int seconds = seconds_left - (((hours * 60) + minutes) * 60);
        String first = String.valueOf(hours);
        String middle = String.valueOf(minutes);
        String last = String.valueOf(seconds);
        if (hours < 10)
            first = "0" + hours;
        if (minutes < 10)
            middle = "0" + minutes;
        if (seconds < 10)
            last = "0" + seconds;
        textView.setText(first + ":" + middle + ":" + last);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
    }
}

