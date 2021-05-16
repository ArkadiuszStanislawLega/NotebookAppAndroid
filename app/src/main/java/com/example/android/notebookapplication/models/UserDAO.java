package com.example.android.notebookapplication.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM Users")
    List<User> getAll();

    @Query("SELECT * FROM Users WHERE userName LIKE :username LIMIT 1")
    User findByName(String username);

    @Insert
    void insert(User... Users);

    @Update
    public void update(User... Users);

    @Delete
    void delete(User User);

    @Transaction
    @Query("SELECT * FROM Users")
    public List<UserWithLists> getUsersWithJobsLists();
}

