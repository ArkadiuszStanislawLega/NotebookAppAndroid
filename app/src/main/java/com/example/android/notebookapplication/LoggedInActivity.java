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
import com.example.android.notebookapplication.models.JobsList;

import java.util.HashMap;

public class LoggedInActivity extends AppCompatActivity {

    private FrameLayout _mainContent;
    private FragmentTransaction _fragmentTransaction;
    private FragmentManager _fragmentManager;
    private AppFragment currentFragment;

    public static final HashMap<AppFragment, Fragment> APPLICATIONS_FRAGMENTS = new HashMap<AppFragment, Fragment>()
    {
        {put(AppFragment.JobsList, new ListFragment());}
        {put(AppFragment.JobDetail, new JobDetailFragment());}
        {put(AppFragment.JobsListDetail, new ListDetailFragment());}
    };

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

    public void changeContent(AppFragment appFragment, Object obj){
        Fragment instance = this.APPLICATIONS_FRAGMENTS.get((appFragment));
        if(instance != null) {
            if (appFragment == AppFragment.JobsListDetail) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (JobsList) obj);
                instance.setArguments(bundle);
            }

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