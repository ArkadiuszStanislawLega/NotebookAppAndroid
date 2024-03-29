package com.example.android.notebookapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.ListsViewModel;
import com.example.android.notebookapplication.models.User;

import java.util.List;


public class LoggedInActivity extends AppCompatActivity {
    public static final String DATE_FORMAT =  "dd.MM.yyyy";
    public static final String TIME_FORMAT =  "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public static User loggedInUser;
    public static final ListsViewModel viewModel = new ListsViewModel();

    private FragmentTransaction _fragmentTransaction;
    private FragmentManager _fragmentManager;
    private AppFragment currentFragment;
    private Button _bLogout;

    private NotebookDatabase _database;


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        this.loggedInUser = (User)getIntent().getSerializableExtra("user");
        this._database = NotebookDatabase.getDatabase(getApplicationContext());

        viewModel.setDatabase(this._database);
        viewModel.setUserId(loggedInUser.get_userId());

        this._fragmentManager = getSupportFragmentManager();


        this.initControls();
        this.initListeners();

        this.changeContent(AppFragment.JobsList);

        Toast.makeText(this, "Witaj " + this.loggedInUser.get_userName() + "!", Toast.LENGTH_SHORT).show();

        List<android.app.Fragment> lists =  this.getFragmentManager().getFragments();

    }

    private void initListeners(){
        if (this._bLogout != null) {
            this._bLogout.setOnClickListener(view -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            });
        }
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

    public void changeContent(AppFragment appFragment){
        Fragment instance = this.selectFragment(appFragment);
        if(instance != null) {
            this._fragmentTransaction = this._fragmentManager.beginTransaction();

            if (this.currentFragment == null)
                this._fragmentTransaction.add(R.id.main_content, instance);
            else
                this._fragmentTransaction.replace(R.id.main_content, instance);

            this.currentFragment = appFragment;
            this._fragmentTransaction.addToBackStack(null);
            this._fragmentTransaction.commit();
        }
    }

    private void initControls(){
        this._bLogout = findViewById(R.id.btnLogout);
    }
}