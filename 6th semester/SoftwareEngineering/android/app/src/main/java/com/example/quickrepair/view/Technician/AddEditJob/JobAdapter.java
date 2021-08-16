package com.example.quickrepair.view.Technician.AddEditJob;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickrepair.R;
import com.example.quickrepair.domain.Job;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder>
{
    private List<Job> jobs;

    JobAdapter(List<Job> jobs)
    {
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        JobAdapter.ViewHolder vh = new JobAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Job job = jobs.get(position);

        holder.jobTitle.setText(job.getJobType().getName());
        holder.price.setText(String.format("%10.2f", job.getPrice()) + "€");
    }

    @Override
    public int getItemCount()
    {
        return jobs == null ? 0 : jobs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ViewGroup listItem;
        TextView jobTitle;
        public TextView price;

        ViewHolder(ViewGroup viewGroup)
        {
            super(viewGroup);

            listItem = viewGroup;
            jobTitle = listItem.findViewById(R.id.JobTypeTextView);
            price = listItem.findViewById(R.id.PriceTextView);
        }
    }

    public List<Job> getJobs()
    {
        return jobs;
    }
}
