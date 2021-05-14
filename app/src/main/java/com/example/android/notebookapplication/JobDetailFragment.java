package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notebookapplication.models.Job;

public class JobDetailFragment extends Fragment {

    private Job _job;
    private View _currentView;
    private TextView _tvTitle;
    public static JobDetailFragment newInstance() {
        return new JobDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this._currentView = inflater.inflate(R.layout.job_detail_fragment, container, false);
        try {
            this._job = (Job) getArguments().getSerializable("job");

            this._tvTitle = this._currentView.findViewById(R.id.job_title);
            this._tvTitle.setText(this._job.getTitle());

        } catch (NullPointerException e){
            System.out.println(e);
        }

        return this._currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        // TODO: Use the ViewModel
    }

}