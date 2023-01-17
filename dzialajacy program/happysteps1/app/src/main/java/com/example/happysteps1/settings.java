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

public class settings extends AppCompatActivity {
    ImageView back;
    Animation scaleup,scaledown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        back = findViewById(R.id.back);
        scaleup = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaledown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        back.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    back.startAnimation(scaleup);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    back.startAnimation(scaledown);
                }
                Intent intent = new Intent(settings.this, MainActivity.class);
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