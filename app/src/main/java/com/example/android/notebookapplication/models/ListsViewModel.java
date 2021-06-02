package com.example.android.notebookapplication.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.notebookapplication.Database.NotebookDatabase;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListsViewModel extends ViewModel {
    private static final String TAG = "ListsViewModel";
    private final int DATABASE_TIMEOUT = 100;
    private long _userId;

    private MutableLiveData<List<JobsList>> _lists = new MutableLiveData<List<JobsList>>();

    private List<JobsList> _tempList;
    private List<Job> _tempJobs;

    private JobsList _selectedList;
    private Job _selectedJob;

    private NotebookDatabase _database;

    public ListsViewModel() {
        this.getLists();
    }

    public void setSelectedList(int i, List<Job> jobs) {
        if (jobs == null) {
            this._selectedList = this._lists.getValue().get(i);
            loadJobs();
        }else{
            this._selectedList = this._lists.getValue().get(i);
            this._selectedList.set_jobs(jobs);
        }
    }

    public void setSelectedJob(int i){
        this._selectedJob = this._selectedList.get_jobs().get(i);
    }

    public Job getSelectedJob() {
        return this._selectedJob;
    }

    public JobsList getSelectedList() {
        return this._selectedList;
    }

    public void setDatabase(NotebookDatabase _database) {
        this._database = _database;
    }

    public void setUserId(long _userId) {
        this._userId = _userId;
        this.loadLists();
    }

    public LiveData<List<JobsList>> getLists(){
        if (_lists == null){
            _lists = new MutableLiveData<List<JobsList>>();
            loadLists();
        }
        return _lists;
    }

    public void refreshLists(){
        this.loadLists();
    }

    private void loadLists(){
        if (this._userId > 0 && this._database != null) {
            this._database.getQueryExecutor().execute(() -> {
                this._tempList = this._database.jobsListDAO().getUserWithLists(this._userId);
            });
            try {
                TimeUnit.MILLISECONDS.sleep(DATABASE_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this._lists.setValue(this._tempList);
        }
    }

    private void loadJobs(){
        if (this._selectedList != null && this._database != null) {
            this._selectedList.set_jobs(null);
            this._database.getQueryExecutor().execute(() -> {
                this._tempJobs = this._database.jobDAO().getJobsList(this._selectedList.get_jobsListId());
            });
            try {
                TimeUnit.MILLISECONDS.sleep(DATABASE_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this._selectedList.set_jobs(this._tempJobs);
        }
    }

    public void addList(JobsList jobsList){
        if (jobsList != null && this._database != null) {
            jobsList.set_owner_id(this._userId);
            jobsList.set_created(new Date());
            jobsList.set_edited(new Date());

            this._database.getQueryExecutor().execute(() -> {
                this._database.jobsListDAO().insert(jobsList);
            });
            loadLists();
        }
    }

    public void removeList(){
        if (this._selectedList != null && this._database != null) {
            this._database.getQueryExecutor().execute(() -> {
                this._database.jobsListDAO().delete(this._selectedList);
                this._selectedList = null;
            });

            loadLists();
        }
    }

    public void addJob(Job job){
        if (job != null && this._selectedList != null && this._database != null) {
            job.set_parentId(this._selectedList.get_jobsListId());
            job.set_created(new Date());
            job.set_edited(new Date());

            this._selectedList.set_edited(new Date());

            this._database.getQueryExecutor().execute(() -> {
                this._database.jobDAO().insert(job);
                this._database.jobsListDAO().update(this._selectedList);
            });

            loadLists();
            loadJobs();
        }
    }

    public void removeJob(){
        if (this._selectedJob != null && this._selectedList != null && this._database != null) {
            this._selectedList.set_edited(new Date());

            this._database.getQueryExecutor().execute(() -> {
                this._database.jobDAO().delete(this._selectedJob);
                this._database.jobsListDAO().update(this._selectedList);
                this._selectedJob = null;
            });
            loadLists();
            loadJobs();
        }
    }

    public void updateJob(){
        if( this._selectedJob != null && this._selectedList != null && this._database != null) {
            this._selectedList.set_edited(new Date());
            this._selectedJob.set_edited(new Date());

            this._database.getQueryExecutor().execute(() -> {
                this._database.jobDAO().update(this._selectedJob);
                this._database.jobsListDAO().update(this._selectedList);
            });
        }
    }
}
