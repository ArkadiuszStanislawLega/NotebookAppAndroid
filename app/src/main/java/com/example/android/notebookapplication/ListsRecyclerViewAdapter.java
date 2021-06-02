package com.example.android.notebookapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.ApiUtils;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ListsRecyclerViewAdapter extends RecyclerView.Adapter<ListsRecyclerViewAdapter.ViewHolder> {
    private final List<JobsList> _lists;
    private NotebookDatabase _database;
    private View _currentView;

    public ListsRecyclerViewAdapter(List<JobsList> items) {
        this._lists = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       this._currentView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists_item, parent, false);
        this._database = NotebookDatabase.getDatabase(this._currentView.getContext());

        return new ViewHolder(this._currentView);
    }
    List<Job> jobs = new ArrayList<>();
    private void getUsersApiCall(int position) {
        ApiUtils.getAPIService().getJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Response<List<Job>>>() {
                    @Override
                    public void onNext(retrofit2.Response<List<Job>> response) {
                        Log.i(TAG, "onNext: " + ApiUtils.getResponseStatusCode(response));
                        if (ApiUtils.getResponseStatusCode(response) == 200) {
                            LoggedInActivity.viewModel.setSelectedList(position, response.body());

//                            initRecyclerView(response.body());
                        } //if
                        else
                            return;
                    }
                    @Override
                    public void onError(Throwable ex) {
                        Log.e("API_CALL", ex.getMessage(), ex);
                        LoggedInActivity.viewModel.setSelectedList(position, null);
                        showJobsLisDetail();
                    }
                    @Override
                    public void onComplete() {
                        showJobsLisDetail();
                    }
                });
    }


    private void showJobsLisDetail(){
        LoggedInActivity loggedInActivity = (LoggedInActivity) this._currentView.getContext();
        loggedInActivity.changeContent(AppFragment.JobsListDetail);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_TIME_FORMAT);
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
                    getUsersApiCall(position);
//                    showJobsLisDetail();
//                    LoggedInActivity.viewModel.setSelectedList(position, null);

//                    LoggedInActivity loggedInActivity = (LoggedInActivity) view.getContext();
//                    loggedInActivity.changeContent(AppFragment.JobsListDetail);
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