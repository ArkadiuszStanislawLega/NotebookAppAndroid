package com.example.android.notebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.databinding.ActivityMainBinding;
import com.example.android.notebookapplication.models.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private User _loggedInUser;
    private EditText _etLogin, _etPass;
    private Button _bLogin, _bRegister;
    private NotebookDatabase _database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this._database = NotebookDatabase.getDatabase(getApplicationContext());
        this.initControls();
        this.initListeners();
    }


    private void initControls() {
        this._etLogin = findViewById(R.id.edtLogin);
        this._etPass = findViewById(R.id.edtPass);
        this._bLogin = findViewById(R.id.btnLogin);
        this._bRegister = findViewById(R.id.b_register);
    }

    private boolean authenticateUser() {
        if (TextUtils.isEmpty(this._etLogin.getText())) {
            Toast.makeText(this,getString(R.string.login_validation_login_empty),Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(this._etPass.getText())) {
            Toast.makeText(this, getString(R.string.login_validation_pass_empty), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (this.isLoginValid() && this.isPasswordValid()) {
            return true;
        }
        return false;
    }

    private void getUserFromDatabase() {
        this._database.getQueryExecutor().execute(() -> {
            this._loggedInUser = this._database.userDAO().findByUsername(this._etLogin.getText().toString());
        });
    }

    private void awaitForData(){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void runLoggedInActivity(){
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.putExtra("user", this._loggedInUser);
        startActivity(intent);
        finish();
    }

    private void runRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void initListeners() {
        if (this._bLogin != null) {
            this._bLogin.setOnClickListener(view -> {
                this._loggedInUser = null;

                this.getUserFromDatabase();
                this.awaitForData();

                if (this.authenticateUser()) {
                    this.runLoggedInActivity();
                    return;
                }
                Toast.makeText(this, getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
            });
        }
        if (this._bRegister != null){
            this._bRegister.setOnClickListener(view -> {
                runRegisterActivity();
            });
        }

    }

    private boolean isLoginValid() {
        if (this._loggedInUser != null) {
            String etUsername = this._etLogin.getText().toString();
            String dbUsername = this._loggedInUser.get_userName();
            return etUsername.equals(dbUsername);
        }
        return false;
    }

    private boolean isPasswordValid() {
        if (this._loggedInUser != null){
            String etPassword = this._etPass.getText().toString();
            String dbPassword = this._loggedInUser.get_password();
            return etPassword.equals(dbPassword);
        }
        return  false;
    }
}