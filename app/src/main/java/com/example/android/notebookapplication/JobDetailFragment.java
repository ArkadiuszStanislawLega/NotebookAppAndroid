package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notebookapplication.models.Job;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;

public class JobDetailFragment extends Fragment {

    private boolean _isEditModeOn = false;
    private Job _job;
    private View _currentView;
    private TextView _tvTitle;
    private TextView _tvContent;
    private TextView _tvCreated;
    private TextView _tvEdited;
    private Switch _sIsFinished;
    private FloatingActionButton _fabEdit;
    private FloatingActionButton _fabDelete;
    private FloatingActionButton _fabConfirmEdit;
    private FloatingActionButton _fabCancelEdit;

    private LinearLayout _llEditButtons;

    public static JobDetailFragment newInstance() {
        return new JobDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this._currentView = inflater.inflate(R.layout.job_detail_fragment, container, false);
        try {
            this._job = (Job) getArguments().getSerializable("job");
            this.initControls();
            this.setValuesToControls();
            this.setListeners();

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

    private void initControls(){
        this._tvTitle = this._currentView.findViewById(R.id.job_title);
        this._tvContent = this._currentView.findViewById(R.id.job_detail_content);
        this._tvCreated = this._currentView.findViewById(R.id.job_created_date);
        this._tvEdited = this._currentView.findViewById(R.id.job_edited_date);
        this._sIsFinished = this._currentView.findViewById(R.id.job_is_finished);
        this._fabEdit = this._currentView.findViewById(R.id.edit_job);
        this._fabDelete = this._currentView.findViewById(R.id.delete_job);
        this._fabConfirmEdit = this._currentView.findViewById(R.id.button_confirm_edit);
        this._fabCancelEdit = this._currentView.findViewById(R.id.button_cancel_edit);
        this._llEditButtons = this._currentView.findViewById(R.id.edit_buttons);
    }

    private void setListeners(){
        this._fabEdit.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Job Edited", Toast.LENGTH_SHORT).show();
                showEditMode();
            }
        });

        this._fabDelete.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Job Delete", Toast.LENGTH_SHORT).show();
                if (_isEditModeOn)
                    showEditMode();
            }
        });

        this._fabConfirmEdit.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Job Confirm Edit", Toast.LENGTH_SHORT).show();
                showEditMode();
            }
        });

        this._fabCancelEdit.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Job Cancel Edit", Toast.LENGTH_SHORT).show();
                showEditMode();
            }
        });
    }

    private void showEditMode(){
        if (!_isEditModeOn){
            this._llEditButtons.setVisibility(View.VISIBLE);
            this._fabEdit.setVisibility(View.GONE);
            this._isEditModeOn = true;
        }
        else{
            this._llEditButtons.setVisibility(View.GONE);
            this._fabEdit.setVisibility(View.VISIBLE);
            this._isEditModeOn = false;
        }
    }

    private void setValuesToControls(){
        String DATE_FORMAT = getString(R.string.date_format) ;
        String TIME_FORMAT = getString(R.string.time_format);

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT+ " " + TIME_FORMAT);
        String edited = formatter.format(this._job.getEdited());
        String created = formatter.format(this._job.getCreated());

        this._tvTitle.setText(this._job.getTitle());
        this._tvContent.setText(this._job.getContent());
        this._tvCreated.setText(created);
        this._tvEdited.setText(edited);
        this._sIsFinished.setChecked(this._job.isFinished());
    }

}