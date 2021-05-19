package com.example.android.notebookapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.User;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddListFragment extends Fragment {

    private Button _bAddList;
    private EditText _etListName;
    private NotebookDatabase _database;
    private User _loggedUser;
    private View _currentView;

    public AddListFragment() {
        // Required empty public constructor
    }

    public static AddListFragment newInstance() {
        AddListFragment fragment = new AddListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this._loggedUser = LoggedInActivity.loggedInUser;
        this._currentView = inflater.inflate(R.layout.fragment_add_list, container, false);
        
        this.initDatabase();
        this.initControls();
        this.initListeners();

        return this._currentView;
    }
    private void initListeners(){
        this._bAddList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                _database.getQueryExecutor().execute(() -> {
                    JobsList jobsList = new JobsList();
                    jobsList.set_name(_etListName.getText().toString());
                    jobsList.set_owner_id(_loggedUser.get_userId());
                    jobsList.set_created(new Date());
                    jobsList.set_edited(new Date());

                    _database.jobsListDAO().insert(jobsList);
                });
            }
        });
    }

    private void initDatabase(){
        this._database = NotebookDatabase.getDatabase(getActivity().getApplicationContext());

        this._database.getQueryExecutor().execute(() -> {
            this._loggedUser.set_jobsList(this._database.jobsListDAO().getUserWithLists(this._loggedUser.get_userId()));
        });
    }
    private void initControls(){
        this._etListName = this._currentView.findViewById(R.id.et_name_list);
        this._bAddList = this._currentView.findViewById(R.id.b_add_new_list);
    }
}