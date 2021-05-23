package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;


public class JobDetailFragment extends Fragment {
    private boolean _isEditModeOn = false;
    private View _currentView;
    private TextView _tvTitle,
            _tvContent,
            _tvCreated,
            _tvEdited;
    private EditText _etTitle,
            _etContent;
    private Switch _sIsFinished;
    private FloatingActionButton _fabEdit,
            _fabDelete,
            _fabConfirmEdit,
            _fabCancelEdit;

    private LinearLayout _llEditButtons;

    public static JobDetailFragment newInstance() {
        return new JobDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this._currentView = inflater.inflate(R.layout.job_detail_fragment, container, false);

        if (LoggedInActivity.viewModel.getSelectedJob() != null) {
            this.initControls();
            this.setValuesToControls();
            this.setListeners();
        }

        return this._currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initControls() {
        this._tvTitle = this._currentView.findViewById(R.id.job_title);
        this._tvContent = this._currentView.findViewById(R.id.job_detail_content);
        this._tvCreated = this._currentView.findViewById(R.id.job_created_date);
        this._tvEdited = this._currentView.findViewById(R.id.job_edited_date);
        this._etTitle = this._currentView.findViewById(R.id.et_job_title);
        this._etContent = this._currentView.findViewById(R.id.et_job_content);
        this._sIsFinished = this._currentView.findViewById(R.id.job_is_finished);
        this._fabEdit = this._currentView.findViewById(R.id.edit_job);
        this._fabDelete = this._currentView.findViewById(R.id.delete_job);
        this._fabConfirmEdit = this._currentView.findViewById(R.id.button_confirm_edit);
        this._fabCancelEdit = this._currentView.findViewById(R.id.button_cancel_edit);
        this._llEditButtons = this._currentView.findViewById(R.id.edit_buttons);
    }

    private void setListeners() {
        this._fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditMode();
            }
        });

        this._fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Usunięto zadanie: " + LoggedInActivity.viewModel.getSelectedJob().get_title(), Toast.LENGTH_SHORT).show();
                if (_isEditModeOn)
                    showEditMode();

                LoggedInActivity.viewModel.removeJob();

                LoggedInActivity loggedInActivity = (LoggedInActivity) view.getContext();
                loggedInActivity.changeContent(AppFragment.JobsListDetail);
            }
        });

        this._fabConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditMode();

                LoggedInActivity.viewModel.getSelectedJob().set_content(_etContent.getText().toString());
                LoggedInActivity.viewModel.getSelectedJob().set_title(_etTitle.getText().toString());

                LoggedInActivity.viewModel.updateJob();

                SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
                String edited = formatter.format(LoggedInActivity.viewModel.getSelectedJob().get_edited());

                _tvContent.setText(LoggedInActivity.viewModel.getSelectedJob().get_content());
                _tvTitle.setText(LoggedInActivity.viewModel.getSelectedJob().get_title());
                _tvEdited.setText(edited);
            }
        });

        this._fabCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEditMode();

                _etTitle.setText(LoggedInActivity.viewModel.getSelectedJob().get_title());
                _etContent.setText(LoggedInActivity.viewModel.getSelectedJob().get_content());
            }
        });

        this._sIsFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoggedInActivity.viewModel.getSelectedJob().set_isFinished(isChecked);

                LoggedInActivity.viewModel.updateJob();

                SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
                String edited = formatter.format(LoggedInActivity.viewModel.getSelectedJob().get_edited());

                _tvEdited.setText(edited);
            }
        });
    }

    private void showEditMode() {
        if (!_isEditModeOn) {
            this._llEditButtons.setVisibility(View.VISIBLE);
            this._etTitle.setVisibility(View.VISIBLE);
            this._etContent.setVisibility(View.VISIBLE);
            this._tvTitle.setVisibility(View.GONE);
            this._tvContent.setVisibility(View.GONE);
            this._fabEdit.setVisibility(View.GONE);
            this._isEditModeOn = true;
        } else {
            this._llEditButtons.setVisibility(View.GONE);
            this._etTitle.setVisibility(View.GONE);
            this._etContent.setVisibility(View.GONE);
            this._tvTitle.setVisibility(View.VISIBLE);
            this._tvContent.setVisibility(View.VISIBLE);
            this._fabEdit.setVisibility(View.VISIBLE);
            this._isEditModeOn = false;
        }
    }

    private void setValuesToControls() {
        if (LoggedInActivity.viewModel.getSelectedJob() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
            String edited = formatter.format(LoggedInActivity.viewModel.getSelectedJob().get_edited());
            String created = formatter.format(LoggedInActivity.viewModel.getSelectedJob().get_created());

            this._etTitle.setText(LoggedInActivity.viewModel.getSelectedJob().get_title());
            this._etContent.setText(LoggedInActivity.viewModel.getSelectedJob().get_content());
            this._tvTitle.setText(LoggedInActivity.viewModel.getSelectedJob().get_title());
            this._tvContent.setText(LoggedInActivity.viewModel.getSelectedJob().get_content());
            this._tvCreated.setText(created);
            this._tvEdited.setText(edited);
            this._sIsFinished.setChecked(LoggedInActivity.viewModel.getSelectedJob().is_isFinished());
        }
    }

}