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
import android.widget.Toast;

import com.example.android.notebookapplication.models.JobsList;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class JobsListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobsListFragment() {
    }

    private List<JobsList> lists = new ArrayList<>();
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JobsListFragment newInstance(int columnCount) {
        JobsListFragment fragment = new JobsListFragment();
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

        this.SetDummyData();

    }
    private void SetDummyData(){
        JobsList jl1 = new JobsList();
        jl1.setId(1);
        jl1.setName("Pierwsza");
        jl1.setCreated(new Date());
        jl1.setEdited(new Date());

        JobsList jl2 = new JobsList();
        jl2.setId(2);
        jl2.setName("Druga");
        jl2.setCreated(new Date());
        jl2.setEdited(new Date());

        JobsList jl3 = new JobsList();
        jl3.setId(3);
        jl3.setName("Trzecia");
        jl3.setCreated(new Date());
        jl3.setEdited(new Date());

        JobsList jl4 = new JobsList();
        jl4.setId(4);
        jl4.setName("Czwarta");
        jl4.setCreated(new Date());
        jl4.setEdited(new Date());

        JobsList jl5 = new JobsList();
        jl5.setId(5);
        jl5.setName("Piąta");
        jl5.setCreated(new Date());
        jl5.setEdited(new Date());

        this.lists.add(jl1);
        this.lists.add(jl2);
        this.lists.add(jl3);
        this.lists.add(jl4);
        this.lists.add(jl5);
    }
    View currentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.jobs_list_fragment, container, false);

        // Set the adapter
        if (currentView instanceof RecyclerView) {
            Context context = currentView.getContext();
            RecyclerView recyclerView = (RecyclerView) currentView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new JobsListRecyclerViewAdapter(this.lists));
        }
        return currentView;
    }


}