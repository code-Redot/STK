package com.studentToolkit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyeventsAdapter extends RecyclerView.Adapter<MyeventsAdapter.ViewHolder> {

    private Context context;
    private List<EventClass> events_classes;


    public MyeventsAdapter(Context context, List<EventClass> events_classes) {
        this.context = context;
        this.events_classes = events_classes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.events_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EventClass events=events_classes.get(position);
        holder.name.setText(events.getName());
       // Glide.with(context).load(events.getColor()).into(holder.profile);
        Drawable myIcon = context.getResources().getDrawable( Integer.valueOf(events.getColor()) );
        holder.profile.setBackground(myIcon);
        holder.desc.setText(events.getDay());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,club.getId(),Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return events_classes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,desc,read;
        public ImageView profile;


        public ViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.events_name);
            profile=itemView.findViewById(R.id.event_color);
            desc=itemView.findViewById(R.id.events_date);

        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }
}
