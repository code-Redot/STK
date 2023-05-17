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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

import java.io.IOException;
import java.util.Date;

public class Job extends AppCompatActivity {
    TextView title, desc;
    ImageView circleImageView, imageView;
    Date date;
    Button member, addevent, addnews;
    DatabaseReference reference;
    FirebaseAuth auth;
    StorageReference storageReference;

    String user_id;
    private Uri imageuri;
    private StorageTask Stask;
    boolean images_selected;
    ProgressDialog dialog;

    ImageView home;
    String job_id, job_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        title = findViewById(R.id.myjob_title);
        desc = findViewById(R.id.myjob_desc);
        circleImageView = findViewById(R.id.myjob_img);
        member = findViewById(R.id.apply_job);
        home=findViewById(R.id.home17);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Job.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        imageView = findViewById(R.id.upload_cv);
        job_id = getIntent().getExtras().getString("job_id");
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        user_id = prefs.getString("account_id", null);
        auth = FirebaseAuth.getInstance();
        job_name = "";
        readJob(job_id);

        date = new Date();
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadNews();
            }
        });

    }

    private void readJob(String job_id) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Job_Class club = snapshot1.getValue(Job_Class.class);
                    if (club.getId().equals(job_id)) {
                        title.setText(club.getName());
                        job_name = club.getName();
                        desc.setText("\" " + club.getDescripe() + "\"");
                        Glide.with(Job.this).load(club.getLogo()).into(circleImageView);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadNews() {
        String timestamp = String.valueOf(date.getTime());
        final ProgressDialog progressDialog = new ProgressDialog(this);

        if (images_selected) {
            progressDialog.setMessage("Uploading");
            progressDialog.show();
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
                            Job_user job_user = new Job_user(timestamp, job_id,user_id, mUri);

                            FirebaseDatabase.getInstance().getReference("Job_User")
                                    .child(timestamp)
                                    .setValue(job_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                showdialoge();
                                            }
                                        }
                                    });
                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(Job.this, "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Job.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(Job.this, "No Files Selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Job.this, "Please Select File", Toast.LENGTH_SHORT).show();

        }
    }

    public void redirect() {
        Intent intent = new Intent(Job.this, ClubsList.class);
        startActivity(intent);
        finish();
    }

    public void on_attachement(View view) {
        openpdf();
    }

    private void openpdf() {
        Intent intent = new Intent();
        // intent.setType("application/pdf");
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();


            if (Stask != null && Stask.isInProgress()) {
                images_selected = false;
                Toast.makeText(Job.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
            } else {
                Bitmap bitmap = null;
                images_selected = true;
                Log.i("Tasks::", imageuri.toString());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);
                //   Toast.makeText(Clinic_booking_details.this,imageuri.toString(),Toast.LENGTH_SHORT).show();
                //uploadImages();
            }
        }


    }
    public void showdialoge()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Application is added")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(Job.this,StudentMain.class);
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