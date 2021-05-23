package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.Job;

import java.text.SimpleDateFormat;
import java.util.List;


public class JobsRecyclerViewAdapter extends RecyclerView.Adapter<JobsRecyclerViewAdapter.ViewHolder> {
    private final List<Job> _jobs;

    public JobsRecyclerViewAdapter(List<Job> items) {
        this._jobs = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobs_item, parent, false);

        return new JobsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
        String edited = formatter.format(this._jobs.get(position).get_edited());
        String created = formatter.format(this._jobs.get(position).get_created());

        holder.item = this._jobs.get(position);
        holder.tvTitle.setText(this._jobs.get(position).get_title());
        holder.tvEdited.setText(edited);
        holder.tvCreated.setText(created);
        if (this._jobs.get(position).is_isFinished())
            holder.ivIsFinished.setVisibility(View.VISIBLE);
        else
            holder.ivIsFinished.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() == null)
                    return;
                if (view.getContext() instanceof LoggedInActivity) {
                    LoggedInActivity.viewModel.setSelectedJob(position);
                    LoggedInActivity mainActivity = (LoggedInActivity) view.getContext();
                    mainActivity.changeContent(AppFragment.JobDetail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this._jobs != null)
            return _jobs.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View currentView;
        public final TextView tvTitle;
        public final TextView tvEdited;
        public final TextView tvCreated;
        public final ImageView ivIsFinished;
        public Job item;

        public ViewHolder(View view) {
            super(view);
            currentView = view;
            tvTitle = (TextView) view.findViewById(R.id.job_title);
            tvEdited = (TextView) view.findViewById(R.id.job_edited);
            tvCreated = (TextView) view.findViewById(R.id.job_created);
            ivIsFinished = (ImageView) view.findViewById(R.id.is_checked);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}