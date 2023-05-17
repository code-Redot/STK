package com.studentToolkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Event extends AppCompatActivity {

    TextView title,desc,club;
    ImageView circleImageView;
    Date date;
    Button member;
    DatabaseReference reference;
    FirebaseAuth auth;

    ImageView home;
    String news_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        title=findViewById(R.id.myevent_title);
        desc=findViewById(R.id.myevent_desc);
        circleImageView=findViewById(R.id.myevent_img);
        member=findViewById(R.id.be_back1);
        club=findViewById(R.id.myevent_club);
        news_id=getIntent().getExtras().getString("event_id");
        auth=FirebaseAuth.getInstance();
        home=findViewById(R.id.home14);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Event.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        readClubs(news_id);
        date=new Date();
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                redirect();
            }
        });
    }

    private void readClubs(String club_id)
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    News_class news = snapshot1.getValue(News_class.class);
                    if(news.getId().equals(news_id))
                    {
                        title.setText(news.getName());
                        desc.setText("\" "+news.getDescripe()+"\"");
                        club.setText("Club: " + news.getClub());
                        Glide.with(Event.this).load(news.getLogo()).into(circleImageView);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void redirect()
    {
        Intent intent = new Intent(Event.this, Events.class);
        startActivity(intent);
        finish();
    }

}