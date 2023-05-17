package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

public class JobList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private JobssAdapter userAdapter;
    private List<Job_Class> job_classes;
    TextView add_job,cvs;
    Saving_data saving_data;
    ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        recyclerView=findViewById(R.id.job_recycler);
        add_job=findViewById(R.id.admin_jobs);
        cvs=findViewById(R.id.admin_cvs);
        home=findViewById(R.id.home18);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(JobList.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        job_classes=new ArrayList<>();

        readClubs();
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String tempid=prefs.getString("account_email", null).substring(0,5);
        if(!tempid.matches("[0-9]+")||tempid.equals("admin@admin.com"))
        {
            add_job.setVisibility(View.VISIBLE);
            cvs.setVisibility(View.VISIBLE);
        }
        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobList.this,Add_Job.class);
                intent.putExtra("jobsintent","General");
                startActivity(intent);
                finish();
            }
        });
        cvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobList.this,CV_List.class);
                intent.putExtra("jobsintent","General");
                startActivity(intent);
                finish();
            }
        });
    }

    private void readClubs()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Jobs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                job_classes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Job_Class club = snapshot1.getValue(Job_Class.class);
                    job_classes.add(club);
                }
                userAdapter = new JobssAdapter(JobList.this, job_classes);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}