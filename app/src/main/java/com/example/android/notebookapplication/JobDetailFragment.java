package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.notebookapplication.models.Job;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;

public class JobDetailFragment extends Fragment {

    private Job _job;
    private View _currentView;
    private TextView _tvTitle;
    private TextView _tvContent;
    private TextView _tvCreated;
    private TextView _tvEdited;
    private Switch _sIsFinished;
    public static JobDetailFragment newInstance() {
        return new JobDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this._currentView = inflater.inflate(R.layout.job_detail_fragment, container, false);
        try {
            this._job = (Job) getArguments().getSerializable("job");

            String DATE_FORMAT = getString(R.string.date_format) ;
            String TIME_FORMAT = getString(R.string.time_format);

            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT+ " " + TIME_FORMAT);
            String edited = formatter.format(this._job.getEdited());
            String created = formatter.format(this._job.getCreated());

            this._tvTitle = this._currentView.findViewById(R.id.job_title);
            this._tvContent = this._currentView.findViewById(R.id.job_detail_content);
            this._tvCreated = this._currentView.findViewById(R.id.job_created_date);
            this._tvEdited = this._currentView.findViewById(R.id.job_edited_date);
            this._sIsFinished = this._currentView.findViewById(R.id.job_is_finished);

            this._tvTitle.setText(this._job.getTitle());
            this._tvContent.setText(this._job.getContent());
            this._tvCreated.setText(created);
            this._tvEdited.setText(edited);
            this._sIsFinished.setChecked(this._job.isFinished());


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