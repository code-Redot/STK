package com.studentToolkit;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.studentToolkit.Notification.FCMSend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CreateClub extends AppCompatActivity {
    Spinner spinner;
    String[] semesters = {"1", "2", "3","4", "5", "6","7", "8", "9","10", "11", "12","13"};
    Date date;
    ImageView imageView;
    EditText name,email,desc;
    String user_id;
    TextView logos;
    DatabaseReference reference;
    FirebaseUser user;
    boolean images_selected;
    StorageReference storageReference;
    private Uri imageuri;
    private StorageTask Stask;
    FirebaseAuth auth;
    Button add_club;
    ArrayList<User> users;
    String timestamp;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        spinner=findViewById(R.id.club_members);
        imageView=findViewById(R.id.club_img);
        name=findViewById(R.id.club_name);
        email=findViewById(R.id.club_email);
        desc=findViewById(R.id.club_describe);
        logos=findViewById(R.id.club_logo);
        add_club=findViewById(R.id.add_club);
        home=findViewById(R.id.home11);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CreateClub.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        user_id=prefs.getString("account_id", null);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinnerhead, semesters);
        adapter.setDropDownViewResource(R.layout.spinnerhead);
        spinner.setAdapter(adapter);
        users=new ArrayList<>();
        readnotification();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        auth= FirebaseAuth.getInstance();
        date=new Date();
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        logos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        add_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClub();
            }
        });



    }
    private void openImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=this.getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadClub()
    {
        String timestamp=String.valueOf(date.getTime());
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();
        if(images_selected) {
            if (imageuri != null) {
                final StorageReference freference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));
                Log.i("Tasks1::", getFileExtension(imageuri).toString());
                Stask = freference.putFile(imageuri);
                Stask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return freference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            Uri downloadUri = task.getResult();
                            String mUri = downloadUri.toString();

                            reference = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                           // String userid = firebaseUser.getUid();
                            Clubs_class clubsClass=new Clubs_class(timestamp,user_id,name.getText().toString(),spinner.getSelectedItem().toString(),
                                    email.getText().toString(),mUri,desc.getText().toString(),"no");

                            FirebaseDatabase.getInstance().getReference("Club")
                                    .child(timestamp)
                                    .setValue(clubsClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                sendnotific();
                                            }
                                        }
                                    });
                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(CreateClub.this, "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateClub.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(CreateClub.this, "No Images Selected", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            reference = FirebaseDatabase.getInstance().getReference();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            String userid = firebaseUser.getUid();

            Clubs_class clubsClass=new Clubs_class(timestamp,user_id,name.getText().toString(),spinner.getSelectedItem().toString(),
                    email.getText().toString(),"default",desc.getText().toString(),"no");

            FirebaseDatabase.getInstance().getReference("Club")
                    .child(timestamp)
                    .setValue(clubsClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                sendnotific();
                            }
                        }
                    });

            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK && data!=null&&data.getData() !=null)
        {
            imageuri=data.getData();

            if(Stask != null && Stask.isInProgress())
            {
                images_selected=false;
                Toast.makeText(CreateClub.this,"Upload in Progress",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Bitmap bitmap=null;
                images_selected=true;
                Log.i("Tasks::",imageuri.toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);

                //uploadImages();
            }
        }
    }
    public void showdialoge()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Club is added")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(CreateClub.this,StudentMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(8,70,140));

    }
    public void readnotification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if (user.getClub_notifi().equals("true")) {
                        users.add(user);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void sendnotific() {
        for (int i=0;i<users.size();i++) {
            FCMSend.pushNotification(this, users.get(i).getToken(), "Club Added", name.getText().toString(),"club",timestamp);
        }
        showdialoge();
    }

}