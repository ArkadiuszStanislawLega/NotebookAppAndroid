package com.example.android.notebookapplication.models;

import androidx.room.Embedded;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;

import java.util.List;

public class UserWithLists {
        @Embedded
        public User user;

        @Relation(parentColumn = "user_id",
                  entityColumn = "jobs_list_id")
        public List<JobsList> jobsLists;
}
