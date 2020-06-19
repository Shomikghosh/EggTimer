package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterActive =false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft){
        int minutes=(int)secondsLeft/60;
        int seconds= secondsLeft - minutes*60;

        String secondsString =Integer.toString(seconds);

        if(seconds <= 9){
            secondsString= "0" +secondsString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString );
    }
    public void controlTimer(View view){

        if(counterActive == false){

            counterActive =true;
            timerSeekbar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer =new CountDownTimer(timerSeekbar.getProgress() * 1000+100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();

                }
            }.start();
        }else{

            timerTextView.setText("0:00");
            timerSeekbar.setProgress(0);
            countDownTimer.cancel();
            controllerButton.setText("Go!");
            timerSeekbar.setEnabled(true);
            counterActive =false;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar =(SeekBar)findViewById(R.id.timeSeekBar);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        controllerButton =(Button)findViewById(R.id.controllerButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}