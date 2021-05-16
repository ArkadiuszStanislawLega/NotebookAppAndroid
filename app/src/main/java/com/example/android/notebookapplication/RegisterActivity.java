package com.example.android.notebookapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button _bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initControls();
        this.initListeners();
    }

    private void runMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initControls() {
        this._bLogin = findViewById(R.id.b_back_to_login);
    }


    private void initListeners() {
        if (this._bLogin != null) {
            this._bLogin.setOnClickListener(view -> {
                this.runMainActivity();
            });
        }
    }

}