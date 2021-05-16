package com.example.android.notebookapplication.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JobsListDAO {

        @Query("SELECT * FROM Jobs_lists")
        List<JobsList> getAll();

        @Query("SELECT * FROM Jobs_lists WHERE name LIKE :name LIMIT 1")
        JobsList findByName(String name);

        @Insert
        void insert(JobsList... Jobs_lists);

        @Update
        public void update(JobsList... Jobs_lists);

        @Delete
        void delete(JobsList Jobs_lists);

        @Transaction
        @Query("SELECT * FROM Jobs_lists")
        public List<JobsListWithJobs> getJobsListWithJobs();
    }




