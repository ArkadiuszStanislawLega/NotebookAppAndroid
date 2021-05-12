package com.example.android.notebookapplication;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.models.JobsList;

public class JobsListDetailFragment extends Fragment {

    private TextView tvListId;
    private JobsListDetailViewModel mViewModel;
    private JobsList jobsList;
    private View currentView;

    public static JobsListDetailFragment newInstance() {
        return new JobsListDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.currentView = inflater.inflate(R.layout.jobs_list_detail_fragment, container, false);
        try {
            jobsList = (JobsList) getArguments().getSerializable("list");
            this.tvListId = this.currentView.findViewById(R.id.list_id);
            this.tvListId.setText(""+this.jobsList.getId());

        }catch (NullPointerException e){
            System.out.println(e);
        }

        return currentView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(JobsListDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}