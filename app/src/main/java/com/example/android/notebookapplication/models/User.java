package com.example.android.notebookapplication.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Users")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private long _userId;

    @ColumnInfo(name = "username")
    private String _userName;

    @ColumnInfo(name = "email")
    private String _email;

    @ColumnInfo(name = "password")
    private String _password;

    @ColumnInfo(name = "name")
    private String _name;

    @ColumnInfo(name = "last_name")
    private String _lastName;

    @ColumnInfo(name = "is_active")
    private Boolean _isActive;

    @Ignore
    private List<JobsList> _jobsList;

    public long getId() {
        return this._userId;
    }

    public void setId(long id) {
        this._userId = id;
    }

    public String get_userName() {
        return this._userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_email() {
        return this._email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_name() {
        return this._name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_lastName() {
        return this._lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }

    public Boolean get_isActive() {
        return _isActive;
    }

    public void set_isActive(Boolean _isActive) {
        this._isActive = _isActive;
    }

    public List<JobsList> get_jobsList() {
        return _jobsList;
    }

    public void set_jobsList(List<JobsList> _jobsList) {
        this._jobsList = _jobsList;
    }
}
