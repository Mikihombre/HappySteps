package com.example.happysteps1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class stats extends AppCompatActivity {

    Animation ScaleUp,ScaleDown;
    ImageView previous;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        previous = findViewById(R.id.back);

        ScaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        ScaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        previous.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    previous.startAnimation(ScaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    previous.startAnimation(ScaleDown);
                }
                Intent intent = new Intent(stats.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });

    }
    @Override
    public void onBackPressed() {
        // nic nie rób - przycisk "wstecz" jest ignorowany
    }
}