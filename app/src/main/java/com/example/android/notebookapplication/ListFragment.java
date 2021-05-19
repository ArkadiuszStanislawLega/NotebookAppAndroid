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
    RecyclerView _rvList;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {

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
        this._rvList = this._currentView.findViewById(R.id.list);

        this._database = NotebookDatabase.getDatabase(this._currentView.getContext());
        this._loggedInUser = LoggedInActivity.loggedInUser;

        this._database.getQueryExecutor().execute(() -> {
            this._loggedInUser.set_jobsList(null);
            List<JobsList> jobsLists =
                    this._database.jobsListDAO().getUserWithLists(this._loggedInUser.get_userId());
//            List<JobsList> jobsLists = this._database.jobsListDAO().getAll();
            this._loggedInUser.set_jobsList(jobsLists);
        });

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("TTTTT" + this._loggedInUser.get_userName());
        // Set the adapter
        if (this._rvList instanceof RecyclerView) {
            Context context = _currentView.getContext();

            if (mColumnCount <= 1) {
                this._rvList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                this._rvList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            this._rvList.setAdapter(new ListsRecyclerViewAdapter(this._loggedInUser.get_jobsList()));
        }
        return _currentView;
    }


}