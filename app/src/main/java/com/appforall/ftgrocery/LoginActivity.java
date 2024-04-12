package com.appforall.ftgrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.appforall.ftgrocery.databinding.ActivityLoginBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding loginBinding;
    SharedPreferences sharedPreferences1;
    DBHelper dbHelper;
    Intent intentWelcome;
    User user;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);
        init();
    }

    /**
     * Initialize variables, get shared preferences and set listeners
     */
    public void init() {
        dbHelper = new DBHelper(LoginActivity.this);
        loginBinding.btnLogin.setOnClickListener(this);
        loginBinding.btnSignUp.setOnClickListener(this);
        sharedPreferences1 = getSharedPreferences("login_details", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBinding.btnSignUp.getId()) {
            signUpUser();
        }
           else if (v.getId() == loginBinding.btnLogin.getId()) {
            if (validateLogin()) {
                //intentWelcome = new Intent(this, TestHomeActivity.class);
                intentWelcome = new Intent(this, HomeActivity.class);
                passDataUsingSharedPreferences();
                startActivity(intentWelcome);
            } else {


                //Using Material Alert Dialog
                new MaterialAlertDialogBuilder(LoginActivity.this)
                        .setMessage("Invalid UserId or Password")
                        .setTitle("Login Failed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(getActivity(), "OK Clicked!",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(getActivity(), "Cancel Clicked!", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        }
    }

    private void signUpUser() {
        if(!addUser()) {
            new MaterialAlertDialogBuilder(LoginActivity.this)
                    .setMessage("Username already exists. All fields are required.")
                    .setTitle("Sign Up")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(getActivity(), "OK Clicked!",Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(getActivity(), "Cancel Clicked!", Toast.LENGTH_LONG).show();
                        }
                    }).show();
        }else{
            new MaterialAlertDialogBuilder(LoginActivity.this)
                    .setMessage("User registration successful")
                    .setTitle("Sign Up")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(getActivity(), "OK Clicked!",Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Toast.makeText(getActivity(), "Cancel Clicked!", Toast.LENGTH_LONG).show();
                        }
                    }).show();
        }
    }

    private boolean addUser() {
        User u = new User(loginBinding.edtUserName.getText().toString().trim(),
                loginBinding.edtPasswd.getText().toString().trim(), loginBinding.edtEmailid.getText().toString().trim());
        return dbHelper.insertUser(u);
    }

    private boolean validateLogin() {
        user = dbHelper.findUser(loginBinding.edtUserName.getText().toString(), loginBinding.edtPasswd.getText().toString());
        return null != user;
    }

    /**
     * pass Data using SharedPreferences
     */
    private void passDataUsingSharedPreferences() {
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putInt("USER_ID", user.getId());
        editor1.putString("USER_NAME", user.getUsername());
        editor1.putString("EMAIL_ID", user.getEmail());
        editor1.commit();
    }


}