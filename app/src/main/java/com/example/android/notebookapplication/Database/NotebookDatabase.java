package com.example.android.notebookapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.notebookapplication.models.User;
import com.example.android.notebookapplication.models.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 3, exportSchema = false)
public abstract class NotebookDatabase extends RoomDatabase {
    public abstract UserDAO userEntityDAO();

    private static volatile NotebookDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NotebookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotebookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    NotebookDatabase.class, "example_database")
                                    .fallbackToDestructiveMigration() //!!!uwaga!!!
                                    .build();
                }
            }
        }
        return INSTANCE;

    }
}

