package com.appforall.ftgrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appforall.ftgrocery.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Intent intent1;
ActivityMainBinding mainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        launchLoginScreen();
    }
    /**
     * Launches login screen
     */
    private void launchLoginScreen() {
        intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }
}