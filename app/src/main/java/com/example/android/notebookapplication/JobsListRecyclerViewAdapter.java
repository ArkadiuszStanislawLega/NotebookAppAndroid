package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.dummy.DummyContent.DummyItem;
import com.example.android.notebookapplication.models.JobsList;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class JobsListRecyclerViewAdapter extends RecyclerView.Adapter<JobsListRecyclerViewAdapter.ViewHolder> {

    private final List<JobsList> mValues;

    public JobsListRecyclerViewAdapter(List<JobsList> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String DATE_FORMAT = "dd.MM.yyyy";
        String TIME_FORMAT = "HH:mm:ss";
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        holder.mItem = mValues.get(position);
        holder.tvId.setText(""+mValues.get(position).getId());
        holder.tvName.setText(mValues.get(position).getName());

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String edited = formatter.format(mValues.get(position).getEdited());
        String created = formatter.format(mValues.get(position).getCreated());

        holder.tvEdited.setText(edited);
        holder.tvCreated.setText(created);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View currentView;
        public final TextView tvId;
        public final TextView tvName;
        public final TextView tvEdited;
        public final TextView tvCreated;
        public JobsList mItem;

        public ViewHolder(View view) {
            super(view);
            currentView = view;
            tvId = (TextView) view.findViewById(R.id.jobs_list_id);
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