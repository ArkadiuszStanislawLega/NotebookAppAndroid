package com.example.android.notebookapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.ListsViewModel;
import com.example.android.notebookapplication.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {

    private View _currentView;
    private RecyclerView _rvList;
    private Button _bAddList;
    private EditText _etListName;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.lists_fragment, container, false);
        this._rvList = this._currentView.findViewById(R.id.list);

        this._etListName = this._currentView.findViewById(R.id.et_name_list);
        this._bAddList = this._currentView.findViewById(R.id.b_add_new_list);

        this._bAddList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!_etListName.getText().toString().equals("")) {
                    JobsList jobsList = new JobsList();
                    jobsList.set_name(_etListName.getText().toString());

                    LoggedInActivity.viewModel.addList(jobsList);

                    _rvList.setAdapter(new ListsRecyclerViewAdapter(null));
                    _rvList.setAdapter(new ListsRecyclerViewAdapter(LoggedInActivity.viewModel.getLists().getValue()));

                    _etListName.getText().clear();
                }
            }
        });

        // Set the adapter
        if (this._rvList instanceof RecyclerView) {
            Context context = _currentView.getContext();

            if (mColumnCount <= 1) {
                this._rvList.setLayoutManager(new LinearLayoutManager(context));
            } else {
                this._rvList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            this._rvList.setAdapter(new ListsRecyclerViewAdapter(LoggedInActivity.viewModel.getLists().getValue()));
        }
        return _currentView;
    }
}