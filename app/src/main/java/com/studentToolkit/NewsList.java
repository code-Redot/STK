package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter userAdapter;
    private List<News_class> clubs_classes;
    TextView add_news;
    Saving_data saving_data;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        recyclerView=findViewById(R.id.news_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubs_classes=new ArrayList<>();
        add_news=findViewById(R.id.admin_news);
        home=findViewById(R.id.home21);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewsList.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String tempid=prefs.getString("account_email", null).substring(0,5);
        if(!tempid.matches("[0-9]+")||tempid.equals("admin@admin.com"))
        {
            add_news.setVisibility(View.VISIBLE);
        }
        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewsList.this,AddNews.class);
                intent.putExtra("newsintent","  General");
                startActivity(intent);
                finish();
            }
        });
        readClubs();
    }

    private void readClubs()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("News");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubs_classes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    News_class club = snapshot1.getValue(News_class.class);
                    clubs_classes.add(club);
                }
                userAdapter = new NewsAdapter(NewsList.this, clubs_classes);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}