package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView responseText = findViewById(R.id.text_response);
        TextView signInText = findViewById(R.id.text_sign_in);
        EditText username   = findViewById(R.id.edit_username);
        EditText email      = findViewById(R.id.edit_email);
        EditText password   = findViewById(R.id.edit_password);
        Spinner country     = findViewById(R.id.spinner_country);
        Button registerBtn  = findViewById(R.id.button_register);

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
                    responseText.setText(response.toString());

                    new SuccessDialog().show(getSupportFragmentManager(), "registration_success");
                },
                error -> {
                    try {
                        String body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        JSONObject jsonResponse = new JSONObject(body);
                        responseText.setText(jsonResponse.get("message").toString());

                        new ErrorDialog().show(getSupportFragmentManager(), "validation_error");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            );

            queue.add(jsonObjectRequest);
        });

        // On 'SIGN IN' click
        signInText.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    /**
     * Dialog showed on validation errors
     */
    public static class ErrorDialog extends DialogFragment
    {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("Validation Error")
                .setMessage("Some of the fields are invalid. Please correct them and try again.")
                .setNeutralButton("OK", (dialog, id) -> dialog.dismiss())
                .create();
        }
    }

    /**
     * Dialog showed on successful registration
     */
    public static class SuccessDialog extends DialogFragment
    {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("Completed!")
                .setMessage("Registration completed! You can log in!")
                .setPositiveButton("Log in", (dialog, id) -> {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                })
                .create();
        }
    }
}
