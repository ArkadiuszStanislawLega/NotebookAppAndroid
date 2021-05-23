package com.example.android.notebookapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.notebookapplication.models.JobsList;


public class ListFragment extends Fragment {

    private View _currentView;
    private RecyclerView _rvList;
    private Button _bAddList;
    private EditText _etListName;

    public ListFragment() {

    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.lists_fragment, container, false);
        this._rvList = this._currentView.findViewById(R.id.list);

        this._etListName = this._currentView.findViewById(R.id.et_name_list);
        this._bAddList = this._currentView.findViewById(R.id.b_add_new_list);

        this._bAddList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!_etListName.getText().toString().equals("")) {
                    JobsList jobsList = new JobsList();
                    jobsList.set_name(_etListName.getText().toString());

                    LoggedInActivity.viewModel.addList(jobsList);

                    _rvList.setAdapter(new ListsRecyclerViewAdapter(null));
                    _rvList.setAdapter(new ListsRecyclerViewAdapter(LoggedInActivity.viewModel.getLists().getValue()));

                    _etListName.getText().clear();
                }
            }
        });

        if (this._rvList instanceof RecyclerView) {
            this._rvList.setAdapter(new ListsRecyclerViewAdapter(LoggedInActivity.viewModel.getLists().getValue()));
        }
        return _currentView;
    }
}