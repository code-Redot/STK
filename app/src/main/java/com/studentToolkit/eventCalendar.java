package com.studentToolkit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class eventCalendar extends AppCompatActivity {
    String mydata="";
    CalendarView calendar;
    RecyclerView recyclerView;
    private MyeventsAdapter userAdapter;
    private List<EventClass> eventClasses;

    EventClass eventClass;
    private EventsAdapter evebtsAdapter;
    private List<Events_class> event_classes;

    ImageView home;
    com.applandeo.materialcalendarview.CalendarView calendarView;
    ArrayList<String> arrayList=new ArrayList<String>();
    String myToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        recyclerView=findViewById(R.id.recycler_events1);
        calendarView=findViewById(R.id.calendarView1);
        home=findViewById(R.id.home15);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(eventCalendar.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventClasses=new ArrayList<>();
        event_classes=new ArrayList<>();

        readevents();
    }

    private void readevents()
    {

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                event_classes.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Events_class club = snapshot1.getValue(Events_class.class);
                    event_classes.add(club);
                }
                getDates();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//    public void sendnotific(String type) {
//        FCMSend.pushNotification(this,"dbTxo9J0R8C0vQMZkpJqXA:APA91bF0wHBG73Hxt5b_2kcAuXFtZYkCjT2d9BSBVtWbLO2ZeUAFdBMnNpVs59-e342wrk77ohivYiSPDW0G4Y4TTNhn3rxIWO-7J_I0jwHDjVMAUtrLQ90tElDvResYh_95Kq0Aa8OO","Order","New order added now, check orders! ");
//    }
    public void getDates()
    {
        int counter=0;
        int color = 0;


        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, com.studentToolkit.DrawableUtils.getCircleDrawableWithText(getApplicationContext(), "T")));


        DateFormat fmt = new SimpleDateFormat("dd,mm,yyyy");//get the current format of the date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); //get the correct format of the date

        Date d;
        Date t = new Date();
        String current="";
        for (int i=0;i<event_classes.size();i++) {
            if (counter == 0) {
                color = R.drawable.purple_circle;
            } else if (counter == 1) {
                color = R.drawable.blue_circle;
            } else if (counter == 2) {
                color = R.drawable.green_circle;
            } else if (counter == 3) {
                color = R.drawable.yellow_circle;
            }
            Calendar calendar1 = Calendar.getInstance();
            eventClass=new EventClass(String.valueOf(color),event_classes.get(i).getDate() +" , "+event_classes.get(i).getTime(),event_classes.get(i).getName());
            eventClasses.add(eventClass);
            current = sdf.format(t);
            calendar1.add(Calendar.DAY_OF_MONTH, getDaysDiff(event_classes.get(i).getDate(), current));
            events.add(new EventDay(calendar1, color));

            calendarView.setEvents(events);
            if(counter>3)
                counter=0;
            else
                counter++;
        }
        userAdapter = new MyeventsAdapter(eventCalendar.this, eventClasses);
        recyclerView.setAdapter(userAdapter);
        calendarView.setOnDayClickListener(eventDay ->
                compateDates(eventDay.getCalendar().getTime(),event_classes));
    }
    public int getDaysDiff(String mydate,String today)
    {
        long differenceDates=0;
        try {

            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
            date1 = dates.parse(mydate);
            date2 = dates.parse(today);
            long difference = (date1.getTime() - date2.getTime());
            differenceDates = difference / (24 * 60 * 60 * 1000);

        } catch (Exception exception) {
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }
        return (int) differenceDates;
    }


    public void compateDates(Date mydate,List<Events_class> events_classes)
    {
        String clickedDate;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1,date2;
        date1=date2=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        clickedDate = sdf.format(mydate);
        try {
            date1 = dateFormat.parse(clickedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // System.out.println("events clicked"+size);

        for(int i=0;i<events_classes.size();i++)
        {
                try {
                    date2 = dateFormat.parse(events_classes.get(i).getDate());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }


                if(date1.equals(date2))
                {
                    Toast.makeText(eventCalendar.this,events_classes.get(i).getName(),Toast.LENGTH_SHORT).show();

                }
            }

        }
}