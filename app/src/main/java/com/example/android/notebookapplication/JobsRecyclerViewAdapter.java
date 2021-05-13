package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.models.Job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class JobsRecyclerViewAdapter extends RecyclerView.Adapter<JobsRecyclerViewAdapter.ViewHolder> {

    private final List<Job> _jobs;

    public JobsRecyclerViewAdapter(List<Job> items) {
        _jobs = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobs_item, parent, false);

        return new JobsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String DATE_FORMAT = "dd.MM.yyyy";
        String TIME_FORMAT = "HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        String edited = formatter.format(this._jobs.get(position).getEdited());
        String created = formatter.format(this._jobs.get(position).getCreated());

        holder.item = this._jobs.get(position);
        holder.tvTitle.setText(this._jobs.get(position).getTitle());
        holder.tvContent.setText(this._jobs.get(position).getContent());
        holder.tvEdited.setText(edited);
        holder.tvCreated.setText(created);
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
        public final TextView tvContent;
        public final TextView tvEdited;
        public final TextView tvCreated;
        public Job item;

        public ViewHolder(View view) {
            super(view);
            currentView = view;
            tvTitle = (TextView) view.findViewById(R.id.jobs_list_id);
            tvContent = (TextView) view.findViewById(R.id.jobs_list_name);
            tvEdited = (TextView) view.findViewById(R.id.jobs_list_edited);
            tvCreated = (TextView) view.findViewById(R.id.jobs_list_created);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}