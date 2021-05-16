package com.example.android.notebookapplication.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class JobsListWithJobs {
    @Embedded
    public JobsList jobsList;

    @Relation(
            parentColumn = "jobs_list_id",
            entityColumn = "job_id"
    )
    public List<Job> jobs;
}