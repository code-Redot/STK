package com.studentToolkit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuildDegree extends AppCompatActivity {

    TextView code, title, hours, total_hours;
    Spinner spinner, spinner1;
    TableLayout tableLayout, hoursLayout;
    Button add_subject, calculate;
    private List<Subject> subjects;
    private List<String> titles;
    boolean clicked;
    FirebaseAuth auth;

    Date date;
    String[] semesters = {"First Semester", "Second Semester", "Summer Semester"};
    String[] years = {"Freshman Year", "Sophomore Year", "Junior Year", "Senior Year"};
    private List<Integer> selected_courses;
    int t_hours;
    Spinner sp;

    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_degree);
        tableLayout = findViewById(R.id.table);
        add_subject = findViewById(R.id.add_subject);
        calculate = findViewById(R.id.calculate_hours);
        total_hours = findViewById(R.id.total_hours);
        hoursLayout = findViewById(R.id.total_table);
        hoursLayout.setVisibility(View.INVISIBLE);
        spinner = findViewById(R.id.semester_spinner1);
        spinner1 = findViewById(R.id.year_spinner1);
        auth=FirebaseAuth.getInstance();
        home=findViewById(R.id.home6);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BuildDegree.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        clicked = false;
        t_hours = 0;
        Create_header();
        selected_courses=new ArrayList<>();
        subjects = new ArrayList<>();
        titles = new ArrayList<>();
        readSubjects();
        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.spinnerhead, years);
        adapter2.setDropDownViewResource(R.layout.spinnerhead);
        spinner.setAdapter(adapter2);
        ArrayAdapter adapter3 = new ArrayAdapter(this, R.layout.spinnerhead, semesters);
        adapter3.setDropDownViewResource(R.layout.spinnerhead);
        spinner1.setAdapter(adapter3);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner1.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayAdapter<String> adp = new ArrayAdapter<String>(BuildDegree.this,
                        android.R.layout.simple_spinner_item, titles);

                LinearLayout layout = new LinearLayout(BuildDegree.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                sp = new Spinner(BuildDegree.this);

                sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));

                sp.setAdapter(adp);
                final Button bb = new Button(BuildDegree.this);
                bb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
                bb.setText("Add");
                bb.setBackground(getDrawable(R.drawable.roundedbutton));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bb.setTextColor(getColor(R.color.white));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(BuildDegree.this);
                layout.addView(sp);
                layout.addView(bb);
                builder.setView(layout);
               // builder.create().show();
                final AlertDialog ad = builder.show();

                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Positionsssss:::::   " + sp.getSelectedItemPosition());
                        Create_table(subjects.get(sp.getSelectedItemPosition()).getCode(), subjects.get(sp.getSelectedItemPosition()).getName(),
                                subjects.get(sp.getSelectedItemPosition()).getHours());
                        t_hours = t_hours + Integer.valueOf(subjects.get(sp.getSelectedItemPosition()).getHours());
                        selected_courses.add(sp.getSelectedItemPosition());
                        ad.dismiss();
                        //   Toast.makeText(BuildDegree.this,sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                    }
                });


            }


        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    System.out.println("Positionsssss:00::"+ selected_courses.size());

                    uploadplan();
                }
                hoursLayout.setVisibility(View.VISIBLE);
                total_hours.setText(String.valueOf(t_hours));
                calculate.setText("Confirm Plan?");
                clicked = true;
            }
        });
    }

    public void Create_header() {
        Drawable drawable = getDrawable(R.drawable.borders);
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);
        code = new TextView(this);
        title = new TextView(this);
        hours = new TextView(this);
        code.setBackground(drawable);
        code.setPadding(10, 10, 10, 10);
        code.setTextColor(Color.parseColor("#000000"));
        code.setTextSize(10);
        code.setGravity(Gravity.CENTER);

        title.setBackground(drawable);
        title.setPadding(10, 10, 10, 10);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(10);
        title.setGravity(Gravity.CENTER);


        hours.setBackground(drawable);
        hours.setPadding(10, 10, 10, 10);
        hours.setTextColor(Color.parseColor("#000000"));
        hours.setTextSize(10);
        hours.setGravity(Gravity.CENTER);


        code.setText("Course Number");
        title.setText("Course Title");
        hours.setText("Credit Hours");
        tableRow.addView(code);
        tableRow.addView(title);
        tableRow.addView(hours);
        tableLayout.addView(tableRow, 0);
    }

    public void Create_table(String coding, String titling, String credits) {
        Drawable drawable = getDrawable(R.drawable.borders);
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);

        tableRow = new TableRow(this);
        lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        tableRow.setLayoutParams(lp);
        code = new TextView(this);
        title = new TextView(this);
        hours = new TextView(this);
        code.setBackground(drawable);
        code.setPadding(10, 10, 10, 10);
        code.setTextColor(Color.parseColor("#000000"));
        code.setTextSize(10);
        code.setGravity(Gravity.CENTER);

        title.setBackground(drawable);
        title.setPadding(10, 10, 10, 10);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(10);
        title.setGravity(Gravity.CENTER);

        hours.setBackground(drawable);
        hours.setPadding(10, 10, 10, 10);
        hours.setTextColor(Color.parseColor("#000000"));
        hours.setTextSize(10);
        hours.setGravity(Gravity.CENTER);

        code.setText(coding);
        title.setText(titling);
        hours.setText(credits);
        tableRow.addView(code);
        tableRow.addView(title);
        tableRow.addView(hours);
        tableLayout.addView(tableRow);

    }

    private void readSubjects() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userid = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Subject");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Subject subject = snapshot1.getValue(Subject.class);
                    subjects.add(subject);
                    titles.add(subject.getName());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void uploadplan() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userid = firebaseUser.getUid();
        System.out.println("Positionsssss:11::"+ selected_courses.size());
        for (int i = 0; i < selected_courses.size(); i++) {
            date = new Date();
            String timestamp = String.valueOf(date.getTime());
            Plans plans = new Plans(timestamp,userid, subjects.get(selected_courses.get(i)).getId(),
                    spinner1.getSelectedItem().toString(), spinner.getSelectedItem().toString());
            FirebaseDatabase.getInstance().getReference("Plans")
                    .child(timestamp)
                    .setValue(plans).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                            Toast.makeText(Registeration.this,"User has been registerd successfully",Toast.LENGTH_SHORT).show();
                                redirect();
                            } else {
//                            warning.setText("Failed to register");
//                            Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    }
    public void redirect()
    {
        Intent intent = new Intent(BuildDegree.this, StudentMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    }
