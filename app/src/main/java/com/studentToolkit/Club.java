package com.studentToolkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Club extends AppCompatActivity {

    TextView title,desc;
    CircleImageView circleImageView;
    Date date;
    Button member,addevent,addnews;
    DatabaseReference reference;
    FirebaseAuth auth;
    private List<User> users;

    String club_id,club_name;
    boolean ismember;
    ImageView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        title=findViewById(R.id.myclub_title);
        desc=findViewById(R.id.myclub_desc);
        circleImageView=findViewById(R.id.myclub_img);
        member=findViewById(R.id.be_member);
        addevent=findViewById(R.id.user_event);
        addnews=findViewById(R.id.user_news);
        club_id=getIntent().getExtras().getString("club_id");
        auth=FirebaseAuth.getInstance();
        home=findViewById(R.id.home7);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Club.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        club_name="";
        ismember=false;
        users=new ArrayList<>();
        readUsers();

        date=new Date();
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_member();
            }
        });
        addnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Club.this,AddNews.class);
                intent.putExtra("newsintent",club_name);
                startActivity(intent);
                finish();
            }
        });
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Club.this,AddEvent.class);
                intent.putExtra("eventsintent",club_name);
                startActivity(intent);
                finish();
            }
        });
    }

    private void readUsers()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if(user.getUserid().equals(auth.getCurrentUser().getUid())) {
                        users.add(user);
                    }

                }
                System.out.println("myclub size: "+users.size());
                readClubs(club_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                        Glide.with(Club.this).load(club.getLogo()).into(circleImageView);
                        if(users.size()>0) {
                            if (club.getUser_id().equals(users.get(0).getUserid())) {
                                addevent.setVisibility(View.VISIBLE);
                                addnews.setVisibility(View.VISIBLE);
                                ismember=true;
                            }
                        }

                    }
                }
                checkMemeber(club_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void checkMemeber(String club_id)
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Club_User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Club_User club = snapshot1.getValue(Club_User.class);
                    if(club.getC_id().equals(club_id))
                    {
                        if(club.getU_id().equals(auth.getCurrentUser().getUid()))
                        member.setVisibility(View.INVISIBLE);
                        if(ismember)
                            member.setVisibility(View.INVISIBLE);
                    }
                    else if(ismember)
                        member.setVisibility(View.INVISIBLE);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void add_member()
    {
        String timestamp=String.valueOf(date.getTime());
        reference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userid = firebaseUser.getUid();
        Club_User club_user=new Club_User(timestamp,club_id,userid);

        FirebaseDatabase.getInstance().getReference("Club_User")
                .child(timestamp)
                .setValue(club_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
//                            Toast.makeText(Registeration.this,"User has been registerd successfully",Toast.LENGTH_SHORT).show();
                            redirect();
                        }
                        else
                        {
//                            warning.setText("Failed to register");
//                            Toast.makeText(Registeration.this,"Failed to Register! Try again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void redirect()
    {
        Intent intent = new Intent(Club.this, ClubsList.class);
        startActivity(intent);
        finish();
    }
}