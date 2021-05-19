package com.example.android.notebookapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;
import com.example.android.notebookapplication.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;


public class LoggedInActivity extends AppCompatActivity {

    public static User loggedInUser;
    private FrameLayout _mainContent;
    private FragmentTransaction _fragmentTransaction;
    private FragmentManager _fragmentManager;
    private AppFragment currentFragment;
    private Button _bLogout;


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        this.loggedInUser = (User)getIntent().getSerializableExtra("user");
        this._fragmentManager = getSupportFragmentManager();

        this.initControls();
        this.initListeners();

        this.changeContent(AppFragment.JobsList, null);

        Toast.makeText(this, "Witaj " + this.loggedInUser.get_userName() + "!", Toast.LENGTH_SHORT).show();
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
            case AddList:
                return new AddListFragment();
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
            this._fragmentTransaction.addToBackStack(null);
            this._fragmentTransaction.commit();
        }
    }

    private void initControls(){
        this._bLogout = findViewById(R.id.btnLogout);
        this._mainContent = findViewById(R.id.main_content);
    }
}