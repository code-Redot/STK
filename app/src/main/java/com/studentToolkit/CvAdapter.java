package com.studentToolkit;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CvAdapter extends RecyclerView.Adapter<CvAdapter.ViewHolder> {

    private Context context;
    private List<Job_user> job_users;
    List<User>users;
    List<Job_Class>jobs;

    public CvAdapter(Context context, List<Job_user> job_users,List<User>users, List<Job_Class>jobs) {
        this.context = context;
        this.job_users = job_users;
        this.users=users;
        this.jobs=jobs;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.cv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int x=position;
        Job_user job_user=job_users.get(position);
        holder.name.setText(job_user.getU_id());
        for(int i=0;i<users.size();i++)
        {
            if(job_user.getU_id().equals(users.get(i).getUserid())) {
                holder.name.setText(users.get(i).getName());
                Glide.with(context).load(users.get(i).getImageurl()).into(holder.profile);

            }

        }
        for(int i=0;i<jobs.size();i++)
        {
            if(job_user.getJ_id().equals(jobs.get(i).getId())) {
                holder.desc.setText(jobs.get(i).getName());


            }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloading(x);

            }
        });
    }

    @Override
    public int getItemCount() {
        return job_users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,desc,read;
        public ImageView profile;


        public ViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cv_title);
            profile=itemView.findViewById(R.id.cv_img);
            desc=itemView.findViewById(R.id.cv_job);
            read=itemView.findViewById(R.id.cv_more);

        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable","project.college.chatting");

        return drawableResourceId;
    }

    public void downloading(int i)
    {
        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(job_users.get(i).getCv());
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,DIRECTORY_DOWNLOADS,"record.pdf");
        downloadManager.enqueue(request);
    }

}
