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

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventsAdapter userAdapter;
    private List<Events_class> clubs_classes;
    TextView add_news;

    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        recyclerView=findViewById(R.id.events_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubs_classes=new ArrayList<>();
        home=findViewById(R.id.home16);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Events.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        add_news=findViewById(R.id.admin_events);
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String tempid=prefs.getString("account_email", null).substring(0,5);
        if(!tempid.matches("[0-9]+")||tempid.equals("admin@admin.com"))
        {
            add_news.setVisibility(View.VISIBLE);
        }
        add_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Events.this,AddEvent.class);
                intent.putExtra("eventsintent","General");
                startActivity(intent);
                finish();
            }
        });
        readClubs();
    }

    private void readClubs()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubs_classes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Events_class club = snapshot1.getValue(Events_class.class);
                    clubs_classes.add(club);
                }
                userAdapter = new EventsAdapter(Events.this, clubs_classes);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}