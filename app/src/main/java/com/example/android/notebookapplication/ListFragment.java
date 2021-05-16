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

import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {

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

        this.SetDummyData();

    }
    private void SetDummyData(){
        Job j1 = new Job();
        Job j2 = new Job();
        Job j3 = new Job();

        j1.set_jobId(1);
        j1.set_content("Pierwszy kontent");
        j1.set_title("Pierwszy tytuł");
        j1.set_created(new Date());
        j1.set_edited(new Date());
        j2.set_jobId(2);
        j2.set_content("Drugi kontent");
        j2.set_title("Drugi tytuł");
        j2.set_created(new Date());
        j2.set_edited(new Date());
        j3.set_jobId(3);
        j3.set_content("Trzeci kontent");
        j3.set_title("Trzeci tytuł");
        j3.set_created(new Date());
        j3.set_edited(new Date());

        List<Job> jobs = new ArrayList<>();
        jobs.add(j1);
        jobs.add(j2);
        jobs.add(j3);

        JobsList jl1 = new JobsList();
        jl1.set_jobsListId(1);
        jl1.set_name("Pierwsza");
        jl1.set_created(new Date());
        jl1.set_edited(new Date());
        jl1.set_jobsList(jobs);

        JobsList jl2 = new JobsList();
        jl2.set_jobsListId(2);
        jl2.set_name("Druga");
        jl2.set_created(new Date());
        jl2.set_edited(new Date());
        jl2.set_jobsList(jobs);

        JobsList jl3 = new JobsList();
        jl3.set_jobsListId(3);
        jl3.set_name("Trzecia");
        jl3.set_created(new Date());
        jl3.set_edited(new Date());
        jl3.set_jobsList(jobs);

        JobsList jl4 = new JobsList();
        jl4.set_jobsListId(4);
        jl4.set_name("Czwarta");
        jl4.set_created(new Date());
        jl4.set_edited(new Date());

        JobsList jl5 = new JobsList();
        jl5.set_jobsListId(5);
        jl5.set_name("Piąta");
        jl5.set_created(new Date());
        jl5.set_edited(new Date());

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
        currentView = inflater.inflate(R.layout.lists_fragment, container, false);

        // Set the adapter
        if (currentView instanceof RecyclerView) {
            Context context = currentView.getContext();
            RecyclerView recyclerView = (RecyclerView) currentView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ListsRecyclerViewAdapter(this.lists));
        }
        return currentView;
    }


}