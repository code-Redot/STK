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

public class MyClubsAdapter extends RecyclerView.Adapter<MyClubsAdapter.ViewHolder> {

    private Context context;
    private List<Clubs_class> clubs_classes;

    public MyClubsAdapter(Context context, List<Clubs_class> clubs_classes) {
        this.context = context;
        this.clubs_classes = clubs_classes;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Clubs_class club=clubs_classes.get(position);
        holder.name.setText(club.getName());
        Glide.with(context).load(club.getLogo()).into(holder.profile);
        holder.desc.setText(club.getDescripe());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,club.getId(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Club.class);
                intent.putExtra("club_id",club.getId());
//                intent.putExtra("department",user.getStatus());
//                intent.putExtra("userid",user.getId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return clubs_classes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,desc,read;
        public ImageView profile;


        public ViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.club_title);
            profile=itemView.findViewById(R.id.club_img);
            desc=itemView.findViewById(R.id.club_desc);
            read=itemView.findViewById(R.id.club_more);

        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }
}
