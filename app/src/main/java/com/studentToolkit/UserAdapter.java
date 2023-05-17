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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;


    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user=users.get(position);
        holder.name.setText(user.getName());
        Glide.with(context).load(user.getImageurl()).into(holder.profile);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context,club.getId(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, UserPlan.class);
                intent.putExtra("user_id",user.getUserid());
//                intent.putExtra("department",user.getStatus());
//                intent.putExtra("userid",user.getId());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,desc,read;
        public ImageView profile;


        public ViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name);
            profile=itemView.findViewById(R.id.user_img);


        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }
}
