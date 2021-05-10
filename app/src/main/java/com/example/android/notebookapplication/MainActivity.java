package com.example.android.notebookapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.notebookapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private EditText edtLogin;
    private EditText edtPass;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.initControls();
        this.initListeners();
    }


    private void initControls() {
        this.edtLogin = findViewById(R.id.edtLogin);
        this.edtPass = findViewById(R.id.edtPass);
        this.btnLogin = findViewById(R.id.btnLogin);
    }

    private boolean authenticateUser() {
        if (TextUtils.isEmpty(this.edtLogin.getText())) {
            Toast.makeText(this,
                    getString(R.string.login__validation_login_empty),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        } //if
        if (TextUtils.isEmpty(this.edtPass.getText())) {
            Toast.makeText(this,
                    getString(R.string.login__validation_pass_empty),
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        } //if
        if (this.isLoginValid() && this.isPasswordValid()) {
            return true;
        } //if
        return false;
    }

    private void initListeners() {
        if (this.btnLogin != null) {
            this.btnLogin.setOnClickListener(view -> {
                if (this.authenticateUser()) {
                    Toast.makeText(this,
                            getString(R.string.login__success),
                            Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(this, LoggedInActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(this,
                        getString(R.string.login__failure),
                        Toast.LENGTH_SHORT)
                        .show();
            });
        } //if
    }

    private boolean isLoginValid(){
        return this.edtLogin.getText().toString().equals("admin");
    }

    private boolean isPasswordValid(){
        return this.edtPass.getText().toString().equals("admin");
    }
}