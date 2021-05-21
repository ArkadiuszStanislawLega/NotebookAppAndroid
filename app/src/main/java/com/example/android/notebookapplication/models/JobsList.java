package com.example.android.notebookapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

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

    @ForeignKey(entity = User.class,
            parentColumns = "user_id",
            childColumns = "jobs_list_id",
            onDelete = CASCADE)
    private long _owner_id;

    @Ignore
    private List<Job> _jobs;

    public List<Job> get_jobs() {
        return _jobs;
    }

    public void set_jobs(List<Job> _jobs) {
        this._jobs = _jobs;
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

    public long get_owner_id() {
        return this._owner_id;
    }

    public void set_owner_id(long _owner_id) {
        this._owner_id = _owner_id;
    }

    public long get_jobsListId() {
        return _jobsListId;
    }

    public void set_jobsListId(long _jobsListId) {
        this._jobsListId = _jobsListId;
    }
}
