package com.example.automobilesnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText edit_text_full_name, edit_text_email, edit_text_password, edit_text_final_password;
    private Button button_create_account;
    private ProgressBar progress_bar_create_account;
    private TextView text_view_login;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edit_text_full_name = (EditText) findViewById(R.id.edit_text_full_name);
        edit_text_email = (EditText) findViewById(R.id.edit_text_sign_up_email);
        edit_text_password = (EditText) findViewById(R.id.edit_text_sign_up_password);
        edit_text_final_password = (EditText) findViewById(R.id.edit_text_sign_up_repeat_password);

        button_create_account = (Button) findViewById(R.id.button_sign_up);

        text_view_login = (TextView) findViewById(R.id.text_view_log_in);

        progress_bar_create_account = (ProgressBar) findViewById(R.id.progress_bar_create_account);

        edit_text_full_name.addTextChangedListener(nameTextWatcher);
        edit_text_email.addTextChangedListener(emailTextWatcher);
        edit_text_password.addTextChangedListener(passwordTextWatcher);
        edit_text_final_password.addTextChangedListener(finalPasswordTextWatcher);

        button_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String full_name = edit_text_full_name.getText().toString().trim();
            if (!full_name.matches("[a-zA-Z\\s]+")){
                edit_text_full_name.setError("Please enter your name properly");
                button_create_account.setEnabled(false);
            }
            else {
                edit_text_full_name.setError(null);
                button_create_account.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher emailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = edit_text_email.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edit_text_email.setError("Please enter a valid email address");
                button_create_account.setEnabled(false);
            }
            else {
                edit_text_email.setError(null);
                button_create_account.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pass = edit_text_password.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(pass).matches()){
                edit_text_password.setError("Password must contain one uppercase, lowercase, digit, special character and must be 6 character long");
                button_create_account.setEnabled(false);

            }
            else {
                edit_text_password.setError(null);
                button_create_account.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher finalPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pass = edit_text_password.getText().toString().trim();
            String pass2 = edit_text_final_password.getText().toString().trim();
            if (!pass.equals(pass2)){
                edit_text_final_password.setError("Password doesn't match");
                button_create_account.setEnabled(false);
            }
            else {
                edit_text_final_password.setError(null);
                button_create_account.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public void openLoginActivity(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void createUser(){
        final String full_name, email, password, final_password;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/create_user.php";
        full_name = edit_text_full_name.getText().toString().trim();
        email = edit_text_email.getText().toString().trim();
        password = edit_text_password.getText().toString().trim();
        final_password = edit_text_final_password.getText().toString().trim();

        if (full_name.isEmpty()){
            edit_text_full_name.setError("Please fill this field");
            error = true;
        }
        if (!full_name.matches("[a-zA-Z\\s]+")){
            edit_text_full_name.setError("Please enter your name properly");
            error = true;
        }
        if (email.isEmpty()){
            edit_text_email.setError("Please fill this field");
            error = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_text_email.setError("Please enter a valid email address");
            error = true;
        }
        if (password.isEmpty()){
            edit_text_password.setError("Please fill this field");
            error = true;
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            edit_text_password.setError("Password too weak. Must be 6 character long");
            error = true;
        }
        if (final_password.isEmpty()){
            edit_text_final_password.setError("Please fill this field");
            error = true;
        }
        if (!password.equals(final_password)) {
            edit_text_final_password.setError("Password doesn't match");
            error = true;
        }

        if (error == false) {
            progress_bar_create_account.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress_bar_create_account.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(SignUpActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                    else if (response.trim().equals("dbError")) {
                        Toast.makeText(getApplicationContext(), "Error while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("dbError2")) {
                        Toast.makeText(getApplicationContext(), "Error while fetching", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("email_taken")) {
                        Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar_create_account.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(SignUpActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("full_name", full_name);
                    params.put("email", email);
                    params.put("final_password", final_password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    }
}