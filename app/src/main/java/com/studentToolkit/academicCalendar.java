package com.studentToolkit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.utils.DateUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class academicCalendar extends AppCompatActivity {
    String mydata="";
    CalendarView calendar;
    RecyclerView recyclerView;
    private MyeventsAdapter userAdapter;
    private List<EventClass> eventClasses;

    EventClass eventClass;

    ImageView home;
    com.applandeo.materialcalendarview.CalendarView calendarView;
    ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);


     //   calendar=findViewById(R.id.academic_calendar);
      //  calendar.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        //apply();
        recyclerView=findViewById(R.id.recycler_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventClasses=new ArrayList<>();
        home=findViewById(R.id.home1);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(academicCalendar.this,StudentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        content content1=new content();
        content1.execute();
    }

    private class content extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July","August","September","October","November","December");
            String temp="";
            String temp1="";
            String endDate="";
            String endDate1="";
            String current="";


            List<EventDay> events = new ArrayList<>();
            calendarView = (com.applandeo.materialcalendarview.CalendarView) findViewById(R.id.calendarView);

            Calendar calendar = Calendar.getInstance();

            int counter=0;
            int color=0;
            System.out.println("events are  size " + arrayList.toString());
            String[][] mydates=new String[arrayList.size()][3];

            for (int i = 0; i < arrayList.size();i=i+1) {//check the dates
                System.out.println("events are  color"+i+" : " + arrayList.get(i));
                for(int x=0;x<months.size();x++)//check if the index starts with the month
                {


                    if (arrayList.get(i).startsWith(months.get(x))) {

                        if (counter == 0) {
                            color = R.drawable.purple_circle;
                        } else if (counter == 1) {
                            color = R.drawable.blue_circle;
                        } else if (counter == 2) {
                            color = R.drawable.green_circle;
                        } else if (counter == 3) {
                            color = R.drawable.yellow_circle;
                        }
                        System.out.println("events are " + i + "  : " + arrayList.get(i));
                        eventClass = new EventClass(String.valueOf(color), arrayList.get(i), arrayList.get(i + 1));
                        eventClasses.add(eventClass);
                        temp = arrayList.get(i).replace(",", "");//formatting the date
                        //  System.out.println("events are : " + temp);

                        String[] mystring = temp.split(" ");
                        if (mystring.length == 3) {
                            temp = temp.replace(" ", ",");
                            DateFormat fmt = new SimpleDateFormat("MMMM,dd,yyyy");//get the current format of the date
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); //get the correct format of the date
                            //  eventClass=new EventClass(String.valueOf(color),temp,arrayList.get(i));
                            // eventClasses.add(eventClass);
                            Date d;
                            Date t = new Date();
                            try {
                                d = fmt.parse(temp);
                                endDate = sdf.format(d);
                                current = sdf.format(t);

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            events.add(new EventDay(calendar, com.studentToolkit.DrawableUtils.getCircleDrawableWithText(getApplicationContext(), "T")));

                            Calendar calendar1 = Calendar.getInstance();
                            mydates[i][0] = endDate;
                            mydates[i][1] = endDate;
                            mydates[i][2] = arrayList.get(i + 1);
                            calendar1.add(Calendar.DAY_OF_MONTH, getDaysDiff(endDate, current));
                            events.add(new EventDay(calendar1, color));

                            calendarView.setEvents(events);
                        } else if (mystring.length == 5 || mystring.length == 6) {
                            // temp=temp.replace(" ",",");
                            if (mystring.length == 5) {// preparing the date formate for the event which has many days in same month
                                temp = mystring[0] + "," + mystring[1] + "," + mystring[4];
                                // System.out.println("events are2 : " + temp);
                                temp1 = mystring[0] + "," + mystring[3] + "," + mystring[4];
                                //System.out.println("events are3 : " + temp1);
                            } else {// preparing the date formate for the event which has many days in different months
                                if (mystring[0].equals("December")) {
                                    temp = mystring[0] + "," + mystring[1] + ",2022";
                                    //  System.out.println("events are22 : " + temp);

                                } else {
                                    temp = mystring[0] + "," + mystring[1] + "," + mystring[5];
                                    //System.out.println("events are2 : " + temp);
                                }

                                temp1 = mystring[3] + "," + mystring[4] + "," + mystring[5];
                                //System.out.println("events are3 : " + temp1);

                            }
                            //   eventClass=new EventClass(String.valueOf(color),temp+"-"+temp1,arrayList.get(i));
                            //  eventClasses.add(eventClass);

                            DateFormat fmt = new SimpleDateFormat("MMMM,dd,yyyy");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            Date d, d1;
                            Date t = new Date();
                            if (temp1.contains("TBA")) {
                                try {
                                    d = fmt.parse(temp);
                                    endDate = sdf.format(d);
                                    current = sdf.format(t);

                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                mydates[i][0] = endDate;
                                mydates[i][1] = endDate;
                                mydates[i][2] = arrayList.get(i + 1);
                                events.add(new EventDay(calendar, com.studentToolkit.DrawableUtils.getCircleDrawableWithText(getApplicationContext(), "T")));

                                Calendar calendar1 = Calendar.getInstance();


                                calendar1.add(Calendar.DAY_OF_MONTH, getDaysDiff(endDate, current));
                                events.add(new EventDay(calendar1, color));

                                calendarView.setEvents(events);
                            } else {


                                try {
                                    d = fmt.parse(temp);
                                    d1 = fmt.parse(temp1);
                                    endDate1 = sdf.format(d1);

                                    endDate = sdf.format(d);
                                    current = sdf.format(t);

                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                events.add(new EventDay(calendar, com.studentToolkit.DrawableUtils.getCircleDrawableWithText(getApplicationContext(), "T")));
                                mydates[i][0] = endDate;
                                mydates[i][1] = endDate1;
                                mydates[i][2] = arrayList.get(i + 1);
                                int diff1 = getDaysDiff(endDate, current);
                                int diff2 = getDaysDiff(endDate1, current);
                                //System.out.println("events are8 : " + diff1);
                                //System.out.println("events are9 : " + diff2);
                                for (int j = diff1; j <= diff2; j++) {
                                    Calendar calendar1 = Calendar.getInstance();

                                    calendar1.add(Calendar.DAY_OF_MONTH, j);
                                    events.add(new EventDay(calendar1, color));

                                    calendarView.setEvents(events);
                                }
                            }
                        }
                    }
                    userAdapter = new MyeventsAdapter(academicCalendar.this, eventClasses);
                    recyclerView.setAdapter(userAdapter);

                    if(counter>3)
                        counter=0;
                    else
                        counter++;
                }

              //  calendar=findViewById(R.id.academic_calendar);
              //  calendar.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
           }
            for (int s = 0; s < arrayList.size(); s++)
            {
                for (int s1=0;s1<3;s1++)
                {
                    System.out.println("events are formated:"+ mydates[s][s1]);
                }
                System.out.println("events are formated: ///////////////");

            }
            calendarView.setOnDayClickListener(eventDay ->
                            compateDates(eventDay.getCalendar().getTime(),mydates,arrayList.size()));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int month=3;
            int subject=4;
            try {
                String url = "https://www.pmu.edu.sa/admission/academic_calendar_2022_2023_ro";
                Document document  = Jsoup.connect(url).get();

                Elements data = document.select("div.accordion-content").select("td");
               // System.out.println("mydata"+ data.toString());
                String temp =data.toString().replace("</td>","");
                temp=temp.replace("&nbsp","");
                String[] words = temp.split("<td>");
                for (int i = 0; i < words.length; i++) {
                    if(i==month)
                    {
                        arrayList.add(words[i]);
                        month=month+4;
                    }
                    if(i==subject)
                    {
                        arrayList.add(words[i]);
                        subject=subject+4;

                    }
                }
                for (int i = 0; i < arrayList.size();i++)
                {

                    if(arrayList.get(i).contains("colspan"))
                    {

                   //     arrayList.remove(i);
                    }
                   // System.out.println("events are words: "+arrayList.get(i));


                }
               // apply();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
    public long getmilis(String date)
    {
        String parts[] = date.split("-");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        return milliTime;
    }
    public void compateDates(Date mydate,String[][] events,int size)
    {
        for(int i=0;i<size;i++) {
            for(int x=0;x<3;x++)
                System.out.println("events clicked" + events[i][x]);
        }
        String clickedDate;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1,date2,date3;
        date1=date2=date3=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        clickedDate = sdf.format(mydate);
        try {
            date1 = dateFormat.parse(clickedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("events clicked"+size);

        for(int i=0;i<size;i=i+2)
        {
             System.out.println("events clickeddd"+events[i][0]);
            System.out.println("events clickeddd"+events[i][1]);
            System.out.println("events clickeddd"+events[i][2]);

            if(!(events[i][0]==null))
            {

                try {
                    date2 = dateFormat.parse(events[i][0]);
                    date3 = dateFormat.parse(events[i][1]);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println("events was clicked"+date1+"///"+date2+"///"+date3);

                if(date1.equals(date2)||date1.equals(date3)||(date1.after(date2)&&date1.before(date3)))
                {
                    Toast.makeText(academicCalendar.this,events[i][2],Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
                //   i=i+3;
            }
        }

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

    public void apply()
    {

        // calendarView.setDisabledDays(getDisabledDays());


    }
    private List<Calendar> getDisabledDays() {
        Calendar firstDisabled = DateUtils.getCalendar();
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2);

        Calendar secondDisabled = DateUtils.getCalendar();
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1);

        Calendar thirdDisabled = DateUtils.getCalendar();
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18);

        List<Calendar> calendars = new ArrayList<>();
        calendars.add(firstDisabled);
        calendars.add(secondDisabled);
        calendars.add(thirdDisabled);
        return calendars;
    }

    private Calendar getRandomCalendar() {
        Random random = new Random();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, random.nextInt(99));

        return calendar;
    }
}
