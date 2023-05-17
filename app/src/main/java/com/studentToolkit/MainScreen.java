package com.studentToolkit;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.studentToolkit.Notification.PushNotificationService;

public class MainScreen extends AppCompatActivity {

    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        signin=findViewById(R.id.sigin);
        signup=findViewById(R.id.sigup);
// button to sing in
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainScreen.this,LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        // button to signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainScreen.this,Registeration.class);
                startActivity(intent);
                finish();
            }
        });

        NotificationManager manager= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                boolean x = manager.areNotificationsEnabled();
                if(!x)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                            requestPermissions(new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);

                        }
                        else {
                            // repeat the permission or open app details
                        }
                    }
                }
            }
        }
    }


}