package com.studentToolkit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Registeration extends AppCompatActivity {

    EditText name,email,pass,major,confirm;
    TextView warning;
    Button reg;
    CheckBox checkBox;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        reg=findViewById(R.id.register);

        name=findViewById(R.id.register_name);
        email=findViewById(R.id.register_email);
        pass=findViewById(R.id.register_password);
        major=findViewById(R.id.register_major);
        confirm=findViewById(R.id.register_confirm);
        checkBox=findViewById(R.id.agree);
        warning=findViewById(R.id.warning_register);
        reg.setEnabled(false);
        auth=FirebaseAuth.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfields()) {
                    //reg.setEnabled(false);
                    request_signup();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    reg.setEnabled(true);
                }
                else
                {
                    reg.setEnabled(false);
                }
            }
        });

    }
    public void request_signup() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        reg.setEnabled(true);
        auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String userid=firebaseUser.getUid();
                            User user=new User(name.getText().toString(),userid,"default",email.getText().toString(),"",major.getText().toString(),"","yes","yes","yes");
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                warning.setText("Registered Correctly");
                                                Toast.makeText(Registeration.this,"User has been registerd successfully",Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();

                                                redirect();
                                            }
                                            else
                                            {
                                                warning.setText("Failed to register");
                                                Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();

                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        progressDialog.dismiss();

    }
    public void redirect()
    {
        Intent intent = new Intent(Registeration.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean checkfields()
    {
        boolean checked=true;
        if(name.getText().toString().equals("")||email.getText().toString().equals("")||pass.getText().toString().equals("")|| major.getText().toString().equals(""))
        {
            checked=false;
            warning.setText("Please fill the missing fields");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            checked=false;
            warning.setText("Email Format not correct ");
        }
        else if(pass.getText().length()<6)
        {
            checked=false;
            warning.setText("Password must be more than 5 characters ");
        }
        else if(!email.getText().toString().contains("@pmu.edu.sa"))
        {
            checked=false;
            warning.setText("Follow the email format xxxxx@pmu.edu.sa ");

        }

        return checked;
    }

}

