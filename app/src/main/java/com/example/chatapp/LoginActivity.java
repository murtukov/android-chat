package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signUpText = findViewById(R.id.text_register);
        TextView responseText = findViewById(R.id.response);
        Button loginButton  = findViewById(R.id.button_sign_in);
        EditText username = findViewById(R.id.edit_username);
        EditText password = findViewById(R.id.edit_password);

//        usernameInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(s.toString().trim().length() == 0) {
//                    loginButton.setEnabled(false);
//                } else {
//                    loginButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        loginButton.setOnClickListener(v -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);

            // Read text from input fields
            Map<String, String> params = new HashMap<>();
            params.put("username", username.getText().toString());
            params.put("password", password.getText().toString());

            // Configure a json request.
            JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                getString(R.string.api_login),
                new JSONObject(params),
                response -> {
                    try {
                        SharedPreferences sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("access_token", response.get("token").toString());
                        editor.apply();

                        startActivity(new Intent(this, ContactsActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // responseText.setText(response.toString());
                },
                error -> {
                    try {
                        String jsonString = new String(error.networkResponse.data);
                        JSONObject response = new JSONObject(jsonString);
                        responseText.setText(response.get("message").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            );

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        });

        signUpText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        });
    }
}
