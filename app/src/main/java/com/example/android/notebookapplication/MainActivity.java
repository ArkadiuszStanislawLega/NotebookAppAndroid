package com.example.android.notebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.databinding.ActivityMainBinding;
import com.example.android.notebookapplication.models.User;

public class MainActivity extends AppCompatActivity {

    public static NotebookDatabase database;
    private ActivityMainBinding binding;

    private User _loggedInUser;

    private EditText _etLogin;
    private EditText _etPass;
    private Button _bLogin;
    private Button _bRegister;
    private FrameLayout mainContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = NotebookDatabase.getDatabase(getApplicationContext());

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
        database.getQueryExecutor().execute(() -> {
            this._loggedInUser = database.userDAO().findByName(this._etLogin.getText().toString());

        });
    }

    private void runLoggedInActivity(){
        Intent intent = new Intent(this, LoggedInActivity.class);
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
                this.getUserFromDatabase();
                if (this.authenticateUser()) {
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
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
        if (this._loggedInUser != null)
            return this._etLogin.getText().toString().equals(this._loggedInUser.get_name());

        return false;
    }

    private boolean isPasswordValid() {
        if (this._loggedInUser != null)
            return this._etPass.getText().toString().equals(this._loggedInUser.get_password());

        return  false;
    }
}