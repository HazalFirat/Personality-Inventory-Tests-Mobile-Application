package com.example.personalityinventory.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalityinventory.R;
import com.example.personalityinventory.helpers.InputValidation;
import com.example.personalityinventory.model.User;
import com.example.personalityinventory.sql.DatabaseHelper;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = Login.this;

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;

    private Button buttonLogin;

    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*   getSupportActionBar().hide();*/

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextUsername = (TextInputEditText) findViewById(R.id.textInputEditTextUsername);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);
    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), Signup.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextUsername, textInputLayoutUsername, getString(R.string.error_message_username))) {
            return;
        }
        /*if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }*/
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextUsername.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity, Home.class);
            int a=databaseHelper.getUser(textInputEditTextUsername.getText().toString().trim()).getUserID();
            accountsIntent.putExtra("USERID", Integer.toString(a));
//            String message = "" + databaseHelper.getUser(textInputEditTextUsername.getText().toString().trim()).getUserID() + databaseHelper.getUser(textInputEditTextUsername.getText().toString().trim()).getUsername();
            emptyInputEditText();
          //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            startActivity(accountsIntent);
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
