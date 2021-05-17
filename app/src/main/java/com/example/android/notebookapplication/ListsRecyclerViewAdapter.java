package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.dummy.DummyContent.DummyItem;
import com.example.android.notebookapplication.models.JobsList;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListsRecyclerViewAdapter extends RecyclerView.Adapter<ListsRecyclerViewAdapter.ViewHolder> {

    private final List<JobsList> _lists;

    public ListsRecyclerViewAdapter(List<JobsList> items) {
        this._lists = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String DATE_FORMAT = "dd.MM.yyyy";
        String TIME_FORMAT = "HH:mm:ss";
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
                    LoggedInActivity mainActivity = (LoggedInActivity) view.getContext();
                    mainActivity.changeContent(AppFragment.JobsListDetail, _lists.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
            return _lists.size();

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