package com.example.android.notebookapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "Jobs_lists")
public class JobsList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "jobs_list_id")
    private long _jobsListId;

    @ColumnInfo(name = "name")
    private String _name;

    @ColumnInfo(name = "created")
    private Date _created;

    @ColumnInfo(name = "edited")
    private Date _edited;

    @ColumnInfo(name = "owner")
    private int _owner_id;

    private List<Job> _jobsList;

    public List<Job> get_jobsList() {
        return this._jobsList;
    }

    public void set_jobsList(List<Job> _jobsList) {
        this._jobsList = _jobsList;
    }

    public String get_name() {
        return this._name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Date get_created() {
        return this._created;
    }

    public void set_created(Date _created) {
        this._created = _created;
    }

    public Date get_edited() {
        return this._edited;
    }

    public void set_edited(Date _edited) {
        this._edited = _edited;
    }

    public int get_owner_id() {
        return this._owner_id;
    }

    public void set_owner_id(int _owner_id) {
        this._owner_id = _owner_id;
    }

    public long get_jobsListId() {
        return _jobsListId;
    }

    public void set_jobsListId(long _jobsListId) {
        this._jobsListId = _jobsListId;
    }
}
