package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView signInText = findViewById(R.id.text_sign_in);
        EditText username = findViewById(R.id.edit_username);
        EditText email = findViewById(R.id.edit_email);
        EditText password = findViewById(R.id.edit_password);
        Spinner country = findViewById(R.id.spinner_country);
        Button registerBtn = findViewById(R.id.button_register);

        // On 'REGISTER' click
        registerBtn.setOnClickListener(v -> {
            RequestQueue queue = Volley.newRequestQueue(this);

            Map<String, String> params = new HashMap<>();
            params.put("username", username.getText().toString());
            params.put("email", email.getText().toString());
            params.put("password", password.getText().toString());
            params.put("country", country.getSelectedItem().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/api/register",
                new JSONObject(params),
                response -> {

                },
                error -> {

                }
            );
        });

        // On 'SIGN IN' click
        signInText.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}
