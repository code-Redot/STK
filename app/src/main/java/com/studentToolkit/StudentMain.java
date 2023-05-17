package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentMain extends AppCompatActivity {
    String url1 = "https://www.pmu.edu.sa/images/2020/PMU%20Overview.jpg";
    String url2 = "https://www.pmu.edu.sa/images/2020/University%20Publications.JPG";
    String url3 = "https://www.pmu.edu.sa/images/2017/PMU%20at%20Exhibitions.JPG";
    SliderView sliderView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView username,morning;
    DatabaseReference reference;
    CircleImageView imageView,navigation_image;

    String email;

    Toolbar toolbar;
    FirebaseUser user;


    ArrayList<Slider> sliderDataArrayList;
    LinearLayout news,events,notify,rating,degree,calend,jobs,clubs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        sliderView = findViewById(R.id.slider);
        navigationView=findViewById(R.id.navigation_view1);
        drawerLayout=findViewById(R.id.drawer_layout);
        View view=this.navigationView.getHeaderView(0);
        username=view.findViewById(R.id.navigation_name);
        navigation_image=view.findViewById(R.id.navigation_image);
        morning=findViewById(R.id.morning);
        imageView=findViewById(R.id.profile_img);
     //   toolbar=view.findViewById(R.id.toolbar);
        news=findViewById(R.id.layout_news);
        clubs=findViewById(R.id.layout_clubs);
        events=findViewById(R.id.layout_events);
        jobs=findViewById(R.id.layout_jobs);
        notify=findViewById(R.id.layout_notify);
        rating=findViewById(R.id.layout_rate);
        degree=findViewById(R.id.layout_degree);
        calend=findViewById(R.id.layout_calendar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
       // setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Menu menu = navigationView.getMenu();
        readprofile();



        // do the same for other MenuItems
        MenuItem nav_gallery = menu.findItem(R.id.nav_profile);
       // nav_gallery.setTitle("NewTitleForGallery");

        startSlider();
        SharedPreferences prefs = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        String myname = prefs.getString("account_name", null);
        email = prefs.getString("account_email", null);
        username.setText(myname);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentMain.this,NewsList.class);
                startActivity(intent);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentMain.this,Events.class);
                startActivity(intent);
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentMain.this,Rate.class);
                startActivity(intent);
            }
        });
        clubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentMain.this,ClubsList.class);
                startActivity(intent);
            }
        });
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempid=prefs.getString("account_email", null).substring(0,5);

                if(!tempid.matches("[0-9]+")||email.equals("admin@admin.com")) {
                    Intent intent = new Intent(StudentMain.this, JobList.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(StudentMain.this, JobList.class);
                    startActivity(intent);
                }
            }
        });

        degree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempid=prefs.getString("account_email", null).substring(0,5);

                if(!tempid.matches("[0-9]+")||email.equals("admin@admin.com")) {
                    Intent intent = new Intent(StudentMain.this, DegreesList.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(StudentMain.this, BuildDegree.class);
                    startActivity(intent);
                }

            }
        });
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StudentMain.this,Notification_Settings.class);
                startActivity(intent);
            }
        });

        calend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(StudentMain.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(30,30,30,30);
                final Button bb = new Button(StudentMain.this);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
                params.setMargins(0,15,0,15);
                bb.setLayoutParams(params);
                bb.setText("Academic Calendar");
                bb.setBackground(getDrawable(R.drawable.roundedbutton));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bb.setTextColor(getColor(R.color.white));
                }
                final Button bb1 = new Button(StudentMain.this);
                LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
                params1.setMargins(0,15,0,15);
                bb1.setLayoutParams(params1);                bb1.setText("Events Calendar");
                bb1.setBackground(getDrawable(R.drawable.roundedbutton));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bb1.setTextColor(getColor(R.color.white));
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentMain.this);
                layout.addView(bb1);
                layout.addView(bb);

                builder.setView(layout);
                // builder.create().show();
                final AlertDialog ad = builder.show();

                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(StudentMain.this,academicCalendar.class);
                        startActivity(intent);
                        ad.dismiss();
                        //   Toast.makeText(BuildDegree.this,sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                    }
                });
                bb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(StudentMain.this,eventCalendar.class);
                        startActivity(intent);
                        ad.dismiss();
                        //   Toast.makeText(BuildDegree.this,sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        on_profile();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_myprofile:
                        on_myprofile();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_signout:
                        on_signout();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }

                return true;
            }
        });



    }

    public void startSlider()
    {
        // adding the urls inside array list
        sliderDataArrayList.add(new Slider(url1));
        sliderDataArrayList.add(new Slider(url2));
        sliderDataArrayList.add(new Slider(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    public void on_myprofile() {
        Intent intent=new Intent(StudentMain.this,Profile.class);
        startActivity(intent);
        finish();
    }
    public void on_profile() {
        Intent intent=new Intent(StudentMain.this,MyClubs.class);
        startActivity(intent);
        finish();
    }

    public void on_signout() {
        Intent intent=new Intent(StudentMain.this,MainScreen.class);
        startActivity(intent);
    }

    private void readprofile()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User users = snapshot1.getValue(User.class);
                    if(users.getUserid().equals(user.getUid()))
                    {
                        morning.setText("Hello "+users.getName());
                        if (users.getImageurl().equals("default"))
                        {
                            imageView.setImageResource(R.drawable.profile);
                        }
                        else
                        {
                            Glide.with(StudentMain.this).load(users.getImageurl()).into(imageView);

                            Glide.with(StudentMain.this).load(users.getImageurl()).into(navigation_image);
                        }


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}