package com.studentToolkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    LinearLayout imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.logo);

//        Intent intent=new Intent(MainActivity.this,Notification_Settings.class);
//                startActivity(intent);
        YoYo.with(Techniques.SlideInRight)
                .duration(700)
                .repeat(2)
                .playOn(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,MainScreen.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
}