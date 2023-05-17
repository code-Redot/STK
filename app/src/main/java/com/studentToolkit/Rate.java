package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Rate extends AppCompatActivity {
    Date date;
    Button add;
    TextView no_add;
    String user_id;
    RatingBar ratingBar;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        add=findViewById(R.id.add_rating);
        no_add=findViewById(R.id.No_rating);
        ratingBar=findViewById(R.id.rating);
        date=new Date();
        home=findViewById(R.id.home24);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Rate.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        user_id=prefs.getString("account_id", null);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadRate();

            }
        });
        no_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirect();

            }
        });


    }

    private void uploadRate()
    {
        String timestamp=String.valueOf(date.getTime());
        Rating_Class subject=new Rating_Class(timestamp,user_id,String.valueOf(ratingBar.getRating()));
        FirebaseDatabase.getInstance().getReference("Rating")
                .child(timestamp)
                .setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
//                            Toast.makeText(Registeration.this,"User has been registerd successfully",Toast.LENGTH_SHORT).show();
                            redirect();
                        }
                        else
                        {
//                            warning.setText("Failed to register");
//                            Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    public void redirect()
    {
        Intent intent = new Intent(Rate.this, StudentMain.class);
        startActivity(intent);
        finish();
    }
}