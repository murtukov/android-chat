package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button logOut = findViewById(R.id.button_log_out);

        logOut.setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("access_token");
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
