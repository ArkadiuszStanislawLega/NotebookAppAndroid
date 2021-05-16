package com.example.android.notebookapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.User;
import com.example.android.notebookapplication.models.UserWithLists;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class LoggedInActivity extends AppCompatActivity {

    public static User loggedInUser;
    public static UserWithLists userWithLists;
    public static List<JobsList> list;
    private FrameLayout _mainContent;
    private FragmentTransaction _fragmentTransaction;
    private FragmentManager _fragmentManager;
    private AppFragment currentFragment;
    private FloatingActionButton _fabAddList;

    private NotebookDatabase _database;

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        this.loggedInUser = (User)getIntent().getSerializableExtra("user");
        this._database = NotebookDatabase.getDatabase(getApplicationContext());

        this._database.getQueryExecutor().execute(() -> {
            this.userWithLists = this._database.userDAO().getUsersWithJobsLists();
            this.list = this._database.jobsListDAO().getAll();
        });
        this._fabAddList = findViewById(R.id.add_list);

        this._fabAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _database.getQueryExecutor().execute(() -> {
                    JobsList jobsList = new JobsList();
                    jobsList.set_name("Nowa lista");
                    jobsList.set_owner_id(loggedInUser.get_userId());
                    jobsList.set_created(new Date());
                    jobsList.set_edited(new Date());

                    _database.jobsListDAO().insert(jobsList);
                });
            }
        });
        this._fragmentManager = getSupportFragmentManager();

        Button btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {

            btnLogout.setOnClickListener(view -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            });
        }
        this.changeContent(AppFragment.JobsList, null);

        Toast.makeText(this, "Witaj " + this.loggedInUser.get_userName() + "!", Toast.LENGTH_SHORT).show();
    }

    public void ButtonVisible(){
        this._fabAddList.setVisibility(View.VISIBLE);
    }

    private Fragment selectFragment(AppFragment appFragment){
        switch(appFragment)
        {
            case JobsList:
                return new ListFragment();
            case JobDetail:
                return new JobDetailFragment();
            case JobsListDetail:
                return new ListDetailFragment();
            default:
                return null;
        }
    }

    private Bundle dataForFragment(AppFragment appFragment, Object obj){
        Bundle bundle = new Bundle();
        if (appFragment == AppFragment.JobsListDetail)
            bundle.putSerializable("list", (JobsList) obj);

        if (appFragment == AppFragment.JobDetail)
            bundle.putSerializable("job", (Job)obj);

        return bundle;
    }

    public void changeContent(AppFragment appFragment, Object obj){
        Fragment instance = this.selectFragment(appFragment);
        if(instance != null) {
            instance.setArguments(this.dataForFragment(appFragment, obj));

            this._fragmentTransaction = this._fragmentManager.beginTransaction();

            if (this.currentFragment == null)
                this._fragmentTransaction.add(R.id.main_content, instance);
            else
                this._fragmentTransaction.replace(R.id.main_content, instance);

            this.currentFragment = appFragment;
            this._fabAddList.setVisibility(View.GONE);
            this._fragmentTransaction.addToBackStack(null);
            this._fragmentTransaction.commit();
        }
    }

    private void initControls(){
        this._mainContent = findViewById(R.id.main_content);
    }
}