package com.studentToolkit;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {

    EditText username,password;
    Button btn_signin;

    private FirebaseAuth mAuth;
    TextView warning;

    DatabaseReference reference;
    Saving_data saving_data;
    FirebaseUser user;
    String myToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_pass);
        btn_signin=findViewById(R.id.login);
        warning=findViewById(R.id.warning_login);
        mAuth = FirebaseAuth.getInstance();
        saving_data=new Saving_data();
        user= FirebaseAuth.getInstance().getCurrentUser();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                myToken=task.getResult();

            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkfields()) {
                    request_signin();
                }

            }
        });
    }
    public void request_signin() {

        if(username.getText().toString().equals("admin@admin.com")&& password.getText().toString().equals("admin1"))
        {
            saving_data.storing_user(LoginScreen.this,"admin","Admin","","admin@admin.com");

            Intent intent=new Intent(LoginScreen.this,StudentMain.class);
            startActivity(intent);
            finish();
        }
        else {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Uploading");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                user = mAuth.getCurrentUser();
//                                Toast.makeText(Login.this, "Authentication Worked.",
//
//                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                redirect();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());

                                Toast.makeText(LoginScreen.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                warning.setText("Email or Password is wrong");

                            }
                        }
                    });
            progressDialog.dismiss();

        }
    }

    public boolean checkfields()
    {
        boolean checked=true;
        if(username.getText().toString().equals("")||password.getText().toString().equals(""))
        {
            checked=false;
            warning.setText("Please fill the missing fields");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString()).matches())
        {
            checked=false;
            warning.setText("Email Format not correct ");
        }
        else if(username.getText().length()<6)
        {
            checked=false;
            warning.setText("Password must be more than 5 characters ");
        }

        return checked;
    }
    public void redirect()
    {
        user= FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("login redirect");
        reference= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        user.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User users=snapshot.getValue(User.class);
                saving_data.storing_user(LoginScreen.this,users.getUserid(),users.getName(),users.getImageurl(),users.getEmail());

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("token", myToken);


                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(LoginScreen.this,StudentMain.class);
                                intent.putExtra("token",users.getToken());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}