package com.studentToolkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddSubject extends AppCompatActivity {

    Spinner spinner,spinner1,spinner2,spinner3;
    EditText name,code;
    Button adding;
    Date date;

    String [] categories = { "1","2","3","4","5","6" };
    String [] branch = { "Computer Science","English","Math" };
    String [] semesters = { "First Semester","Second Semester","Summer Semester"};
    String [] years = { "Freshman Year","Sophomore Year","Junior Year","Senior Year"};
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        spinner=findViewById(R.id.hours_spinner);
        spinner1=findViewById(R.id.branch_spinner);
        spinner2=findViewById(R.id.semester_spinner);
        spinner3=findViewById(R.id.year_spinner);
        name=findViewById(R.id.subject_name);
        code=findViewById(R.id.subject_code);
        adding=findViewById(R.id.add_subject);
        date=new Date();
        home=findViewById(R.id.home5);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddSubject.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        ArrayAdapter adapter =new ArrayAdapter(this,R.layout.spinnerhead,categories);
        adapter.setDropDownViewResource(R.layout.spinnerhead);
        spinner.setAdapter(adapter);
        ArrayAdapter adapter1 =new ArrayAdapter(this,R.layout.spinnerhead,branch);
        adapter.setDropDownViewResource(R.layout.spinnerhead);
        spinner1.setAdapter(adapter1);
        ArrayAdapter adapter2 =new ArrayAdapter(this,R.layout.spinnerhead,semesters);
        adapter.setDropDownViewResource(R.layout.spinnerhead);
        spinner2.setAdapter(adapter2);
        ArrayAdapter adapter3 =new ArrayAdapter(this,R.layout.spinnerhead,years);
        adapter.setDropDownViewResource(R.layout.spinnerhead);
        spinner3.setAdapter(adapter3);

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
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner3.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadSubject();
            }
        });
    }

    private void uploadSubject()
    {
        String timestamp=String.valueOf(date.getTime());
        Subject subject=new Subject(timestamp,name.getText().toString(),code.getText().toString(),
                spinner.getSelectedItem().toString(),spinner1.getSelectedItem().toString(),spinner3.getSelectedItem().toString(),spinner2.getSelectedItem().toString());
        FirebaseDatabase.getInstance().getReference("Subject")
                .child(timestamp)
                .setValue(subject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
//                            Toast.makeText(Registeration.this,"User has been registerd successfully",Toast.LENGTH_SHORT).show();
                            showdialoge();
                        }
                        else
                        {
//                            warning.setText("Failed to register");
//                            Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    public void showdialoge()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Subject is added")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(AddSubject.this,StudentMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(8,70,140));

    }

}