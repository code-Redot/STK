package com.studentToolkit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Context context;
    private List<Events_class> events_classes;


    public EventsAdapter(Context context, List<Events_class> events_classes) {
        this.context = context;
        this.events_classes = events_classes;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Events_class events=events_classes.get(position);
        holder.name.setText(events.getName());
        Glide.with(context).load(events.getLogo()).into(holder.profile);
        holder.desc.setText(events.getDescripe());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,club.getId(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Event.class);
                intent.putExtra("event_id",events.getId());
//                intent.putExtra("department",user.getStatus());
//                intent.putExtra("userid",user.getId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

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
            name=itemView.findViewById(R.id.news_title);
            profile=itemView.findViewById(R.id.news_imgs);
            desc=itemView.findViewById(R.id.news_desc);
            read=itemView.findViewById(R.id.news_more);

        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }
}
