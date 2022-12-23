package com.example.happysteps1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int currentSteps;
    TextView tv_licznik;
    SensorManager sensorManager;
    CircularProgressBar circularProgressBar;
    private float previousTotalSteps = 0f;
    private float totalSteps;
    boolean running = false;
    int i=0;
    ImageView reset,chat,settings,stats,dystans;
    Animation ScaleUp,ScaleDown;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = findViewById(R.id.progress_circular);

        tv_licznik = findViewById(R.id.tv_licznik);
        reset = findViewById(R.id.reset);
        chat = findViewById(R.id.chat);
        settings = findViewById(R.id.settings);
        stats = findViewById(R.id.stats);
        dystans = findViewById(R.id.dystans);

        ScaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        ScaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        loadData();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    reset.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    reset.startAnimation(ScaleDown);
                }
                resetSteps();
                resetPreviousTotalSteps();
                saveData();
                return true;
            }
        });

        chat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    chat.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    chat.startAnimation(ScaleDown);
                }
                Intent intent = new Intent(MainActivity.this, chat.class);
                startActivity(intent);
                return true;
            }
        });

        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    settings.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    settings.startAnimation(ScaleDown);
                }
                Intent intent = new Intent(MainActivity.this, settings.class);
                startActivity(intent);
                return true;
            }
        });

        dystans.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    dystans.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    dystans.startAnimation(ScaleDown);
                }
                return true;

            }
        });

        stats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    stats.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    stats.startAnimation(ScaleDown);
                }
                Intent intent = new Intent(MainActivity.this, stats.class);
                startActivity(intent);
                return true;
            }
        });

    }
    private void resetSteps() {
        totalSteps = 0f;
        updateStepCounterView();
    }

    private void resetPreviousTotalSteps() {
        previousTotalSteps = totalSteps;
    }

    private void updateStepCounterView() {
        tv_licznik.setText(String.format(Locale.getDefault(), "%.0f", totalSteps));
    }



    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("total steps", totalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        totalSteps = sharedPreferences.getFloat("total steps", 0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        running=true;
        loadData();
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);
        } else{
            Toast.makeText(this,"Sensor not found!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }


    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            float currentSteps = event.values[0];
            totalSteps = totalSteps + currentSteps - previousTotalSteps;
            previousTotalSteps = currentSteps;
            tv_licznik.setText(String.valueOf(totalSteps));

            CircularProgressBar progressCircular = this.circularProgressBar;
            if (progressCircular != null) {
                progressCircular.setProgressWithAnimation(totalSteps);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}