package com.example.android.notebookapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.notebookapplication.Database.NotebookDatabase;
import com.example.android.notebookapplication.models.User;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    private Button _bLogin, _bRegister;
    private EditText _etName, _etLastName, _etUsername, _etPassword, _etConfirmPassword, _etEmail;
    private NotebookDatabase _database;
    private User _user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this._database = NotebookDatabase.getDatabase(getApplicationContext());
        this.initControls();
        this.initListeners();
    }

    private void runMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initControls() {
        this._bLogin = findViewById(R.id.b_register_back_to_login);
        this._bRegister = findViewById(R.id.b_register_confirm);
        this._etUsername = findViewById(R.id.et_register_username);
        this._etName = findViewById(R.id.et_register_name);
        this._etLastName = findViewById(R.id.et_register_last_name);
        this._etEmail = findViewById(R.id.et_register_email);
        this._etPassword = findViewById(R.id.et_register_password);
        this._etConfirmPassword = findViewById(R.id.et_register_confirm_password);
    }

    private boolean isUsernameValid() {
        boolean value = false;

        if (TextUtils.isEmpty(this._etUsername.getText())) {
            return false;
        }
        this._user = null;

        this._database.getQueryExecutor().execute(() -> {
            this._user = this._database.userDAO().findByUsername(this._etUsername.getText().toString());
        });

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this._user == null) {
            value = true;
        }

        if (!value)
            Toast.makeText(this, getString(R.string.register_wrong_username), Toast.LENGTH_SHORT).show();

        return value;
    }

    private boolean isNameValid() {
        if (TextUtils.isEmpty(this._etName.getText())) {
            Toast.makeText(this, getString(R.string.register_wrong_name), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private boolean isSurnameValid() {
        if (TextUtils.isEmpty(this._etLastName.getText())) {
            Toast.makeText(this, getString(R.string.register_wrong_surname), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private boolean isPasswordsFieldsEmpty() {
        if (TextUtils.isEmpty(this._etPassword.getText()) && TextUtils.isEmpty(this._etConfirmPassword.getText()))
            return true;
        return false;
    }

    private boolean isPasswordsAreSame() {
        String password = this._etPassword.getText().toString();
        String confirmPassword = this._etConfirmPassword.getText().toString();
        return password.equals(confirmPassword);
    }

    private boolean isPasswordValid() {
        if (!this.isPasswordsFieldsEmpty() && this.isPasswordsAreSame())
            return true;

        Toast.makeText(this, getString(R.string.register_wrong_password), Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isEmailValid() {
        if (TextUtils.isEmpty(this._etUsername.getText())) {
            Toast.makeText(this, getString(R.string.login_validation_pass_empty), Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void initListeners() {
        if (this._bLogin != null) {
            this._bLogin.setOnClickListener(view -> {
                this.runMainActivity();
            });
        }

        if (this._bRegister != null) {
            this._bRegister.setOnClickListener(view -> {
                if (isUsernameValid() && isNameValid() && isSurnameValid() && isEmailValid() && isPasswordValid()) {

                    User user = new User();
                    user.set_userName(this._etUsername.getText().toString());
                    user.set_name(this._etName.getText().toString());
                    user.set_lastName(this._etLastName.getText().toString());
                    user.set_email(this._etEmail.getText().toString());
                    user.set_password(this._etPassword.getText().toString());
                    user.set_isActive(true);

                    this._database.getQueryExecutor().execute(() -> {
                        this._database.userDAO().insert(user);
                    });

                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    this.runMainActivity();
                }
            });
        }
    }
}