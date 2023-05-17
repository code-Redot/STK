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

public class ClubsList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClubsAdapter userAdapter;
    private List<Clubs_class> clubs_classes;
    TextView addClub,requests;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_list);
        recyclerView=findViewById(R.id.club_recycler);
        addClub=findViewById(R.id.admin_club);
        requests=findViewById(R.id.admin_requests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubs_classes=new ArrayList<>();
        home=findViewById(R.id.home9);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClubsList.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        readClubs();
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String tempid=prefs.getString("account_email", null).substring(0,5);
        if(!tempid.matches("[0-9]+")||tempid.equals("admin@admin.com"))
        {
            requests.setVisibility(View.VISIBLE);
        }
        addClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClubsList.this,CreateClub.class);
                intent.putExtra("clubsintent","General");
                startActivity(intent);
                finish();
            }
        });
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClubsList.this,ClubsRequests.class);
                intent.putExtra("clubsintent","General");
                startActivity(intent);
                finish();
            }
        });
    }

    private void readClubs()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Club");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubs_classes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Clubs_class club = snapshot1.getValue(Clubs_class.class);
                    if(club.approved.equals("yes"))
                    clubs_classes.add(club);
                }
                userAdapter = new ClubsAdapter(ClubsList.this, clubs_classes,"list");
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}