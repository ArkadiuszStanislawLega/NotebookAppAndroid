package com.example.android.notebookapplication.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public
interface JobDAO {

        @Query("SELECT * FROM Jobs")
        List<Job> getAll();

        @Query("SELECT * FROM Jobs WHERE title LIKE :title LIMIT 1")
        Job findByName(String title);

        @Insert
        void insert(Job... Jobs);

        @Update
        public void update(Job... Jobs);

        @Delete
        void delete(Job Jobs);

        @Transaction
        @Query("SELECT * FROM Jobs WHERE jobs_list_id = :id ")
        public List<Job> getJobsList(long id);
}
