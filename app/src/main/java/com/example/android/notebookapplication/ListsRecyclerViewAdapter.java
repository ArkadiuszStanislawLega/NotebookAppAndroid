package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.dummy.DummyContent.DummyItem;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListsRecyclerViewAdapter extends RecyclerView.Adapter<ListsRecyclerViewAdapter.ViewHolder> {
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private final String TIME_FORMAT = "HH:mm:ss";
    private final List<JobsList> _lists;
    private NotebookDatabase _database;

    public ListsRecyclerViewAdapter(List<JobsList> items) {
        this._lists = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists_item, parent, false);
        this._database = NotebookDatabase.getDatabase(view.getContext());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        String edited = formatter.format(this._lists.get(position).get_edited());
        String created = formatter.format(this._lists.get(position).get_created());

        holder.item = this._lists.get(position);
        holder.tvName.setText(this._lists.get(position).get_name());
        holder.tvEdited.setText(edited);
        holder.tvCreated.setText(created);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() == null)
                    return;
                if (view.getContext() instanceof LoggedInActivity) {
                    LoggedInActivity.selectedJobsList = _lists.get(position);
                    _database.getQueryExecutor().execute(() -> {
                        List<Job> jobs = _database.jobDAO().getJobsList(LoggedInActivity.selectedJobsList.get_jobsListId());
                        LoggedInActivity.selectedJobsList.set_jobs(jobs);
                    });

                    LoggedInActivity loggedInActivity = (LoggedInActivity) view.getContext();
                    loggedInActivity.changeContent(AppFragment.JobsListDetail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return _lists.size();
        }catch (NullPointerException e){
            Log.e(TAG, "getItemCount: ", e);
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View currentView;
        public final TextView tvName;
        public final TextView tvEdited;
        public final TextView tvCreated;
        public JobsList item;

        public ViewHolder(View view) {
            super(view);
            currentView = view;
            tvName = (TextView) view.findViewById(R.id.jobs_list_name);
            tvEdited = (TextView) view.findViewById(R.id.jobs_list_edited);
            tvCreated = (TextView) view.findViewById(R.id.jobs_list_created);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvName.getText() + "'";
        }
    }
}