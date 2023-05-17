package com.studentToolkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studentToolkit.Notification.FCMSend;

import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubsApprove extends AppCompatActivity {
    TextView title,desc;
    CircleImageView circleImageView;
    Date date;
    Button member,decline;
    DatabaseReference reference;
    FirebaseAuth auth;

    String club_id,club_name;
    ImageView home;
    String club_owner,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_approve);
        title=findViewById(R.id.myclub_title);
        desc=findViewById(R.id.myclub_desc);
        circleImageView=findViewById(R.id.myclub_img);
        member=findViewById(R.id.approve);
        decline=findViewById(R.id.decline);
        club_id=getIntent().getExtras().getString("club_id");
        auth=FirebaseAuth.getInstance();
        club_name="";
        home=findViewById(R.id.home8);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClubsApprove.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        readClubs(club_id);

        date=new Date();
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_member();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClub();
            }
        });

    }

    private void readClubs(String club_id)
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Club");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Clubs_class club = snapshot1.getValue(Clubs_class.class);
                    if(club.getId().equals(club_id))
                    {
                        title.setText(club.getName());
                        club_name=club.getName();
                        desc.setText("\" "+club.getDescripe()+"\"");
                        Glide.with(ClubsApprove.this).load(club.getLogo()).into(circleImageView);
                        club_owner=club.getUser_id();
                        readToken(club_owner);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void add_member()
    {
        reference = FirebaseDatabase.getInstance().getReference("Club").child(club_id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", "yes");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sendnotific("Approved");
                }
            }
        });
    }
    public void deleteClub()
    {
        reference = FirebaseDatabase.getInstance().getReference("Club").child(club_id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", "declined");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sendnotific("Declined");
                }
            }
        });
    }

    public void sendnotific(String message) {
            FCMSend.pushNotification(this, token, "Club "+message, club_name+" is " +message,"club",club_id);

        showdialoge(message);
    }
    public void showdialoge(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The Club is "+message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(ClubsApprove.this,ClubsRequests.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(8,70,140));

    }

    private void readToken(String userids)
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if(user.getUserid().equals(userids))
                    {
                        token=user.getToken();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}