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

public class MyClubs extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyClubsAdapter userAdapter;
    private List<Clubs_class> clubs_classes;
    TextView add;
    String user_id;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clubs);
        recyclerView=findViewById(R.id.myclub_recycler);
        add=findViewById(R.id.club_add);
        home=findViewById(R.id.home19);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyClubs.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        clubs_classes=new ArrayList<>();
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        user_id=prefs.getString("account_id", null);
        readClubs();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyClubs.this,CreateClub.class);
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
                    if(club.approved.equals("yes")&& club.getUser_id().equals(user_id))
                        clubs_classes.add(club);
                }
                userAdapter = new MyClubsAdapter(MyClubs.this, clubs_classes);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(MyClubs.this,StudentMain.class);
        startActivity(intent);
    }
}