package com.example.android.notebookapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A fragment representing a list of Items.
 */
public class JobFragment extends Fragment {
    private User _loggedInUser;
    private NotebookDatabase _database;
    private View _currentView;
    private RecyclerView _rvList;
    private EditText _etJobName;
    private Button _bAddJob;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<Job> _jobs;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JobFragment newInstance(int columnCount) {
        JobFragment fragment = new JobFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.jobs_fragment, container, false);
        this._database = NotebookDatabase.getDatabase(this._currentView.getContext());
        this._loggedInUser = LoggedInActivity.loggedInUser;

        this._rvList = this._currentView.findViewById(R.id.list);
        this._etJobName = this._currentView.findViewById(R.id.et_title_job);
        this._bAddJob = this._currentView.findViewById(R.id.b_add_new_job);

        this._bAddJob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Job job = new Job();
                job.set_title(_etJobName.getText().toString());
                job.set_created(new Date());
                job.set_edited(new Date());

                _database.getQueryExecutor().execute(() -> {
                    _database.jobDAO().insert(job);
                });
                _etJobName.getText().clear();
                updateList();
                _database.getQueryExecutor().execute(() -> {
                    _jobs = _database.jobDAO().getJobsList(LoggedInActivity.listId);
                });

                _rvList.setAdapter(new JobsRecyclerViewAdapter(_jobs));
            }
        });
        
        updateList();
        // Set the adapter
        if (this._rvList instanceof RecyclerView) {
            Context context = this._currentView.getContext();

            if (mColumnCount <= 1) {
                this._rvList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                this._rvList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            this._rvList.setAdapter(new JobsRecyclerViewAdapter(this._jobs));
        }
        return this._currentView;
    }

    private void updateList(){
        this._database.getQueryExecutor().execute(() -> {
            this._loggedInUser.set_jobsList(null);
            _jobs = this._database.jobDAO().getJobsList(LoggedInActivity.listId);
        });

        try {
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}