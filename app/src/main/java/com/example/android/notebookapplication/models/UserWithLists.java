package com.example.android.notebookapplication.models;

import androidx.room.Embedded;
import androidx.room.Relation;


import java.io.Serializable;
import java.util.List;

public class UserWithLists implements Serializable {
        @Embedded
        public User user;

        @Relation(parentColumn = "user_id",
                  entityColumn = "jobs_list_id")
        public List<JobsList> jobsLists;
}
