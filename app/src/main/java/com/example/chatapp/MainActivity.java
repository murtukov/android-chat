package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);

        if (null == token) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, ContactsActivity.class));
        }

        finish();
    }
}
