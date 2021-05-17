package com.example.android.notebookapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.User;
import com.example.android.notebookapplication.models.UserWithLists;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {

    private User _loggedInUser;
    NotebookDatabase _database;
    View _currentView;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
        this._loggedInUser = LoggedInActivity.loggedInUser;
    }

    private List<JobsList> lists = new ArrayList<>();

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
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

    public void REFRESH(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _currentView = inflater.inflate(R.layout.lists_fragment, container, false);

        this._database = NotebookDatabase.getDatabase(this.getContext());

        this._database.getQueryExecutor().execute(() -> {
            this._loggedInUser.set_jobsList(null);
            List<JobsList> jobsLists = this._database.jobsListDAO().getUserWithLists(this._loggedInUser.get_userId());
            this._loggedInUser.set_jobsList(jobsLists);
        });

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LoggedInActivity parent = (LoggedInActivity) getActivity();
        parent.ButtonVisible();

        // Set the adapter
        if (_currentView instanceof RecyclerView) {
            Context context = _currentView.getContext();
            RecyclerView recyclerView = (RecyclerView) _currentView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setAdapter(new ListsRecyclerViewAdapter(this._loggedInUser.get_jobsList()));
        }
        return _currentView;
    }


}