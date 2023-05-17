package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class UserPlan extends AppCompatActivity {

    RecyclerView recycler1,recycler2,recycler3,recycler4,recycler5,recycler6,recycler7,recycler8,recycler9,recycler10,recycler11,recycler12;
    private PlansAdapter plansAdapter;
    private List<Plans> plans1,plans2,plans3,plans4,plans5,plans6,plans7,plans8,plans9,plans10,plans11,plans12;
    private List<Subject>subjects;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plan);
        defineRecycler();
        plans1=new ArrayList<>();
        plans2=new ArrayList<>();
        plans3=new ArrayList<>();
        plans4=new ArrayList<>();
        plans5=new ArrayList<>();
        plans6=new ArrayList<>();
        plans7=new ArrayList<>();
        plans8=new ArrayList<>();
        plans9=new ArrayList<>();
        plans10=new ArrayList<>();
        plans11=new ArrayList<>();
        plans12=new ArrayList<>();


        subjects=new ArrayList<>();
        home=findViewById(R.id.home25);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserPlan.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        readCourses();
        //readPlans();
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        String tempid=prefs.getString("account_email", null).substring(0,5);
    }

    private void readPlans()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Plans");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plans1.clear();
                plans2.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Plans plan = snapshot1.getValue(Plans.class);
                    if(plan.getUser_id().equals(getIntent().getExtras().getString("user_id"))) {
                        if(plan.getYear().equals("Freshman Year")&&plan.getSemester().equals("First Semester")) {

                            plans1.add(plan);
                        }
                        else if(plan.getYear().equals("Freshman Year")&&plan.getSemester().equals("Second Semester"))
                        {
                            plans2.add(plan);

                        }
                        else if(plan.getYear().equals("Freshman Year")&&plan.getSemester().equals("Summer Semester"))
                        {
                            plans3.add(plan);

                        }
                        else if(plan.getYear().equals("Sophomore Year")&&plan.getSemester().equals("First Semester")) {

                            plans4.add(plan);
                        }
                        else if(plan.getYear().equals("Sophomore Year")&&plan.getSemester().equals("Second Semester"))
                        {
                            plans5.add(plan);

                        }
                        else if(plan.getYear().equals("Sophomore Year")&&plan.getSemester().equals("Summer Semester"))
                        {
                            plans6.add(plan);

                        }
                        else if(plan.getYear().equals("Junior Year")&&plan.getSemester().equals("First Semester")) {

                            plans7.add(plan);
                        }
                        else if(plan.getYear().equals("Junior Year")&&plan.getSemester().equals("Second Semester"))
                        {
                            plans8.add(plan);

                        }
                        else if(plan.getYear().equals("Junior Year")&&plan.getSemester().equals("Summer Semester"))
                        {
                            plans9.add(plan);

                        }
                        else if(plan.getYear().equals("Senior Year")&&plan.getSemester().equals("First Semester")) {

                            plans10.add(plan);
                        }
                        else if(plan.getYear().equals("Senior Year")&&plan.getSemester().equals("Second Semester"))
                        {
                            plans11.add(plan);

                        }
                        else if(plan.getYear().equals("Senior Year")&&plan.getSemester().equals("Summer Semester"))
                        {
                            plans12.add(plan);

                        }
                    }
                }
                plansAdapter = new PlansAdapter(UserPlan.this, plans1,subjects);
                recycler1.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans2,subjects);
                recycler2.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans3,subjects);
                recycler3.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans4,subjects);
                recycler4.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans5,subjects);
                recycler5.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans6,subjects);
                recycler6.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans7,subjects);
                recycler7.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans8,subjects);
                recycler8.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans9,subjects);
                recycler9.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans10,subjects);
                recycler10.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans11,subjects);
                recycler11.setAdapter(plansAdapter);
                plansAdapter = new PlansAdapter(UserPlan.this, plans12,subjects);
                recycler12.setAdapter(plansAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void readCourses()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Subject");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Subject subject = snapshot1.getValue(Subject.class);
               //     System.out.println("subjects are14: "+subject.getName());

                    subjects.add(subject);
                }
                readPlans();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void defineRecycler()
    {
        recycler1=findViewById(R.id.first_fresh);
        recycler2=findViewById(R.id.second_fresh);
        recycler3=findViewById(R.id.summer_fresh);
        recycler4=findViewById(R.id.first_Sophomore);
        recycler5=findViewById(R.id.second_Sophomore);
        recycler6=findViewById(R.id.summer_Sophomore);
        recycler7=findViewById(R.id.first_Junior);
        recycler8=findViewById(R.id.second_Junior);
        recycler9=findViewById(R.id.summer_Junior);
        recycler10=findViewById(R.id.first_Senior);
        recycler11=findViewById(R.id.second_Senior);
        recycler12=findViewById(R.id.summer_Senior);
        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(new LinearLayoutManager(this));
        recycler2.setHasFixedSize(true);
        recycler2.setLayoutManager(new LinearLayoutManager(this));
        recycler3.setHasFixedSize(true);
        recycler3.setLayoutManager(new LinearLayoutManager(this));
        recycler4.setHasFixedSize(true);
        recycler4.setLayoutManager(new LinearLayoutManager(this));
        recycler5.setHasFixedSize(true);
        recycler5.setLayoutManager(new LinearLayoutManager(this));
        recycler6.setHasFixedSize(true);
        recycler6.setLayoutManager(new LinearLayoutManager(this));
        recycler7.setHasFixedSize(true);
        recycler7.setLayoutManager(new LinearLayoutManager(this));
        recycler8.setHasFixedSize(true);
        recycler8.setLayoutManager(new LinearLayoutManager(this));
        recycler9.setHasFixedSize(true);
        recycler9.setLayoutManager(new LinearLayoutManager(this));
        recycler10.setHasFixedSize(true);
        recycler10.setLayoutManager(new LinearLayoutManager(this));
        recycler11.setHasFixedSize(true);
        recycler11.setLayoutManager(new LinearLayoutManager(this));
        recycler12.setHasFixedSize(true);
        recycler12.setLayoutManager(new LinearLayoutManager(this));


    }
}