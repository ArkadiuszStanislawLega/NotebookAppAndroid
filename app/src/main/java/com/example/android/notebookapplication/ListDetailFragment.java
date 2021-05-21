package com.example.android.notebookapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.JobsList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListDetailFragment extends Fragment {
    private boolean _isEditModeOn = false;
    private JobsList _jobsList;
    private TextView _tvListName,
            _tvListEditedDate,
            _tvListCreatedDate;
    private EditText _etListName;
    private FloatingActionButton  _fabEdit,
            _fabDelete,
            _fabConfirmEdit,
            _fabCancelEdit;
    private LinearLayout _llEditButtons;
    private View _currentView;
    private NotebookDatabase _database;

    public static ListDetailFragment newInstance() {
        return new ListDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.list_detail_fragment, container, false);
        this._database = NotebookDatabase.getDatabase(this._currentView.getContext());

        try {
            this._jobsList = LoggedInActivity.selectedJobsList;
            this.initControls();
            this.setValuesToControls();
            this.setListeners();

        } catch (NullPointerException e) {
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

    private void initControls() {
        this._tvListName = this._currentView.findViewById(R.id.list_name);
        this._tvListCreatedDate = this._currentView.findViewById(R.id.list_created_date);
        this._tvListEditedDate = this._currentView.findViewById(R.id.list_edited_date);
        this._etListName = this._currentView.findViewById(R.id.et_list_name);
        this._fabEdit = this._currentView.findViewById(R.id.edit_list);
        this._fabDelete = this._currentView.findViewById(R.id.delete_list);
        this._fabConfirmEdit = this._currentView.findViewById(R.id.button_confirm_edit);
        this._fabCancelEdit = this._currentView.findViewById(R.id.button_cancel_edit);
        this._llEditButtons = this._currentView.findViewById(R.id.edit_buttons);
    }

    private void setListeners() {
        this._fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchEditMode();
            }
        });

        this._fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Lista " + _tvListName.getText().toString() + " została usunięta."  , Toast.LENGTH_SHORT).show();
                if (_isEditModeOn)
                    switchEditMode();

                _database.getQueryExecutor().execute(() -> {
                    _database.jobsListDAO().delete(_jobsList);
                });

                LoggedInActivity loggedInActivity = (LoggedInActivity) view.getContext();
                loggedInActivity.changeContent(AppFragment.JobsList);
            }
        });
        this._fabConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchEditMode();

                _jobsList.set_name(_etListName.getText().toString());
                _jobsList.set_edited(new Date());

                SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
                String edited = formatter.format(_jobsList.get_edited());

                _tvListName.setText(_jobsList.get_name());
                _tvListEditedDate.setText(edited);

                _database.getQueryExecutor().execute(() -> {
                    _database.jobsListDAO().update(_jobsList);
                });
            }
        });
        this._fabCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_currentView.getContext(), "Cancel edit list", Toast.LENGTH_SHORT).show();

                _tvListName.setText(_jobsList.get_name());
                _etListName.setText(_jobsList.get_name());
                switchEditMode();
            }
        });
    }

    private void switchEditMode() {
        if (!_isEditModeOn) {
            this._llEditButtons.setVisibility(View.VISIBLE);
            this._etListName.setVisibility(View.VISIBLE);
            this._tvListName.setVisibility(View.GONE);
            this._fabEdit.setVisibility(View.GONE);
            this._isEditModeOn = true;
        } else {
            this._llEditButtons.setVisibility(View.GONE);
            this._etListName.setVisibility(View.GONE);
            this._tvListName.setVisibility(View.VISIBLE);
            this._fabEdit.setVisibility(View.VISIBLE);
            this._isEditModeOn = false;
        }
    }


    private void setValuesToControls() {
        SimpleDateFormat formatter = new SimpleDateFormat(LoggedInActivity.DATE_FORMAT + " " + LoggedInActivity.TIME_FORMAT);
        String edited = formatter.format(_jobsList.get_edited());
        String created = formatter.format(_jobsList.get_created());

        this._etListName.setText("" + this._jobsList.get_name());
        this._tvListName.setText("" + this._jobsList.get_name());
        this._tvListEditedDate.setText(edited);
        this._tvListCreatedDate.setText(created);
    }

}