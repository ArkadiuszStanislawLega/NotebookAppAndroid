package com.example.android.notebookapplication.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Jobs")
public class Job implements Serializable {
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "job_id")
    private int _jobId;

    @ColumnInfo(name = "created")
    private Date _created;

    @ColumnInfo(name = "edited")
    private Date _edited;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String _title;

    @ColumnInfo(name = "content")
    private String _content;

    @SerializedName("completed")
    @Expose
    @ColumnInfo(name = "is_finished")
    private boolean _isFinished;

    @SerializedName("userId")
    @Expose
    @ColumnInfo(name = "jobs_list_id")
    private long _parentId;

    public int get_jobId() {
        return _jobId;
    }

    public void set_jobId(int _jobId) {
        this._jobId = _jobId;
    }

    public Date get_created() {
        return _created;
    }

    public void set_created(Date _created) {
        this._created = _created;
    }

    public Date get_edited() {
        return _edited;
    }

    public void set_edited(Date _edited) {
        this._edited = _edited;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public long get_parentId() {
        return _parentId;
    }

    public void set_parentId(long _parentId) {
        this._parentId = _parentId;
    }

    public boolean is_isFinished() {
        return _isFinished;
    }

    public void set_isFinished(boolean _isFinished) {
        this._isFinished = _isFinished;
    }
}
