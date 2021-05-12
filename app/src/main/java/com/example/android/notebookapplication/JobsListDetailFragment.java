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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JobsListDetailFragment extends Fragment {

    private TextView tvListName;
    private TextView tvListEditedDate;
    private TextView tvListCreatedDate;
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
            this.tvListName = this.currentView.findViewById(R.id.list_name);
            this.tvListCreatedDate = this.currentView.findViewById(R.id.list_created_date);
            this.tvListEditedDate = this.currentView.findViewById(R.id.list_edited_date);

            String DATE_FORMAT = getString(R.string.date_format) ;
            String TIME_FORMAT = getString(R.string.time_format);

            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT+ " " + TIME_FORMAT);
            String edited = formatter.format(jobsList.getEdited());
            String created = formatter.format(jobsList.getCreated());

            this.tvListName.setText(""+this.jobsList.getName());
            this.tvListEditedDate.setText(edited);
            this.tvListCreatedDate.setText(created);

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