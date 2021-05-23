package com.example.android.notebookapplication.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.LoggedInActivity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListsViewModel extends ViewModel {
    private NotebookDatabase _database;
    private final int DATABASE_TIMEOUT = 100;
    private long _userId;
    private MutableLiveData<List<JobsList>> _lists = new MutableLiveData<List<JobsList>>();

    private List<JobsList> _tempList;
    private List<Job> _tempJobs;

    private JobsList _selectedList;
    private Job _selectedJob;

    public ListsViewModel() {
        this.getLists();
    }

    public void setSelectedList(int i) {
        this._selectedList = this._lists.getValue().get(i);
        loadJobs();
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
        this._database.getQueryExecutor().execute(() -> {
            _tempList = this._database.jobsListDAO().getUserWithLists(this._userId);
        });
        try {
            TimeUnit.MILLISECONDS.sleep(DATABASE_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this._lists.setValue(this._tempList);
    }

    private void loadJobs(){
        this._selectedList.set_jobs(null);
        this._database.getQueryExecutor().execute(() -> {
            _tempJobs = this._database.jobDAO().getJobsList(this._selectedList.get_jobsListId());
        });
        try {
            TimeUnit.MILLISECONDS.sleep(DATABASE_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this._selectedList.set_jobs(this._tempJobs);
    }

    public void addList(JobsList jobsList){

        jobsList.set_owner_id(this._userId);
        jobsList.set_created(new Date());
        jobsList.set_edited(new Date());

        this._database.getQueryExecutor().execute(() -> {
            this._database.jobsListDAO().insert(jobsList);
        });
        loadLists();
    }

    public void removeList(){
        _database.getQueryExecutor().execute(() -> {
            _database.jobsListDAO().delete(this._selectedList);
        });
        this._selectedJob = null;
        loadLists();
    }

    public void addJob(Job job){
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

    public void removeJob(){
        this._selectedList.set_edited(new Date());

        _database.getQueryExecutor().execute(() -> {
            _database.jobDAO().delete(this._selectedJob);
            _database.jobsListDAO().update(this._selectedList);
        });
        loadLists();
        loadJobs();
        this._selectedJob = null;
    }

    public void updateJob(){
        this._selectedList.set_edited(new Date());
        this._selectedJob.set_edited(new Date());

        _database.getQueryExecutor().execute(() -> {
            _database.jobDAO().update(this._selectedJob);
            _database.jobsListDAO().update(this._selectedList);
        });
    }
}
