package com.studentToolkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ViewHolder> {

    private Context context;
    private List<Plans> plans;
    private List<Subject> subjects;


    public PlansAdapter(Context context, List<Plans> plans,List<Subject> subjects) {
        this.context = context;
        this.plans = plans;
        this.subjects=subjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.plans_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Subject subject;
        Plans plan=plans.get(position);
      //  System.out.println("plans are3: "+plan.getCourse());

        subject=getID(plan);

        holder.name.setText(subject.getName());
        holder.code.setText(subject.getCode());
        holder.hours.setText(subject.getHours());
     //   Glide.with(context).load(plan.getImageurl()).into(holder.profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if( plans==null)
            return 0;
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,code,hours;
        public ImageView profile;


        public ViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.course_name);
            code=itemView.findViewById(R.id.course_code);
            hours=itemView.findViewById(R.id.course_hours);



        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }

    public Subject getID(Plans plan)
    {


        for(int i=0;i<subjects.size();i++)
        {
            if(subjects.get(i).getId().equals(plan.getCourse())) {
                return subjects.get(i);
            }
        }
        return subjects.get(0);

    }
}
