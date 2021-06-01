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

import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.Job;

public class JobFragment extends Fragment {

    private View _currentView;
    private RecyclerView _rvList;
    private EditText _etJobName;
    private Button _bAddJob;

    public JobFragment() {
    }

    public static JobFragment newInstance() {
        JobFragment fragment = new JobFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._currentView = inflater.inflate(R.layout.jobs_fragment, container, false);

        if (LoggedInActivity.viewModel.getSelectedList() != null) {
            initControls();
            initListener();

            if (this._rvList instanceof RecyclerView) {
                this._rvList.setAdapter(new JobsRecyclerViewAdapter(LoggedInActivity.viewModel.getSelectedList().get_jobs()));
            }
        }
        else{
            LoggedInActivity loggedInActivity = (LoggedInActivity) this._currentView.getContext();
            loggedInActivity.changeContent(AppFragment.JobsList);
        }
        return this._currentView;
    }

    private void initControls(){
        this._rvList = this._currentView.findViewById(R.id.list);
        this._etJobName = this._currentView.findViewById(R.id.et_title_job);
        this._bAddJob = this._currentView.findViewById(R.id.b_add_new_job);
    }

    private void initListener(){
        this._bAddJob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!_etJobName.getText().toString().equals("")) {
                    Job job = new Job();
                    job.set_title(_etJobName.getText().toString());

                    LoggedInActivity.viewModel.addJob(job);

                    _rvList.setAdapter(new JobsRecyclerViewAdapter(LoggedInActivity.viewModel.getSelectedList().get_jobs()));

                    _etJobName.getText().clear();
                }
            }
        });
    }
}