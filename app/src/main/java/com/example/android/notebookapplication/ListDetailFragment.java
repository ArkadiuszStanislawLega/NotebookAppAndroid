package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notebookapplication.models.JobsList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;

public class ListDetailFragment extends Fragment {
    private boolean _isEditModeOn = false;
    private JobsList _jobsList;
    private TextView _tvListName;
    private TextView _tvListEditedDate;
    private TextView _tvListCreatedDate;
    private FloatingActionButton _fabAdd;
    private FloatingActionButton _fabEdit;
    private FloatingActionButton _fabDelete;
    private FloatingActionButton _fabConfirmEdit;
    private FloatingActionButton _fabCancelEdit;
    private LinearLayout _llEditButtons;
    private View _currentView;

    public static ListDetailFragment newInstance() {
        return new ListDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.list_detail_fragment, container, false);

        try {
            _jobsList = (JobsList) getArguments().getSerializable("list");
            this.initControls();
            this.setValuesToControls();
            this.setListeners();

        }catch (NullPointerException e){
            System.out.println(e);
        }

        return _currentView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(JobsListDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initControls(){
        this._tvListName = this._currentView.findViewById(R.id.list_name);
        this._tvListCreatedDate = this._currentView.findViewById(R.id.list_created_date);
        this._tvListEditedDate = this._currentView.findViewById(R.id.list_edited_date);
        this._fabAdd = this._currentView.findViewById(R.id.add_job);
        this._fabEdit = this._currentView.findViewById(R.id.edit_list);
        this._fabDelete = this._currentView.findViewById(R.id.delete_list);
        this._fabConfirmEdit = this._currentView.findViewById(R.id.button_confirm_edit);
        this._fabCancelEdit = this._currentView.findViewById(R.id.button_cancel_edit);
        this._llEditButtons = this._currentView.findViewById(R.id.edit_buttons);
    }

    private void setListeners(){
        this._fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Job Added", Toast.LENGTH_SHORT).show();
                if (_isEditModeOn)
                    showEditMode();
            }
        });

        this._fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "List Edited", Toast.LENGTH_SHORT).show();
                showEditMode();
            }
        });

        this._fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "List Delete", Toast.LENGTH_SHORT).show();
                if (_isEditModeOn)
                    showEditMode();
            }
        });
        this._fabConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Confirm edit list", Toast.LENGTH_SHORT).show();
                showEditMode();
            }
        });
        this._fabCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Cancel edit list", Toast.LENGTH_SHORT).show();
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
        String edited = formatter.format(_jobsList.getEdited());
        String created = formatter.format(_jobsList.getCreated());

        this._tvListName.setText(""+this._jobsList.getName());
        this._tvListEditedDate.setText(edited);
        this._tvListCreatedDate.setText(created);
    }

}