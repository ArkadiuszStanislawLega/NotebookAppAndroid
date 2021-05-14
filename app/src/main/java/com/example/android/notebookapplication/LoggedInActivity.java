package com.example.android.notebookapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.android.notebookapplication.Enumerators.AppFragment;
import com.example.android.notebookapplication.models.Job;
import com.example.android.notebookapplication.models.JobsList;

import java.util.HashMap;

public class LoggedInActivity extends AppCompatActivity {

    private FrameLayout _mainContent;
    private FragmentTransaction _fragmentTransaction;
    private FragmentManager _fragmentManager;
    private AppFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

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

            this._fragmentTransaction.addToBackStack(null);
            this._fragmentTransaction.commit();
        }
    }

    private void initControls(){
        this._mainContent = findViewById(R.id.main_content);
    }
}