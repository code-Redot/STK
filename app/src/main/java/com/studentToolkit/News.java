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

public class News extends AppCompatActivity {
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
        setContentView(R.layout.activity_news);
    title=findViewById(R.id.mynews_title);
    desc=findViewById(R.id.mynews_desc);
    circleImageView=findViewById(R.id.mynews_img);
        club=findViewById(R.id.mynews_club);
        member=findViewById(R.id.be_back);
    news_id=getIntent().getExtras().getString("news_id");
        home=findViewById(R.id.home20);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(News.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    auth=FirebaseAuth.getInstance();
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
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("News");
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
                        Glide.with(News.this).load(news.getLogo()).into(circleImageView);

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
        Intent intent = new Intent(News.this, NewsList.class);
        startActivity(intent);
        finish();
    }

}