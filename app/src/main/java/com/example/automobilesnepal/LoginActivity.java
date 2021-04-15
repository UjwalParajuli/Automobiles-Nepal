package com.example.automobilesnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class LoginActivity extends AppCompatActivity {
    private EditText edit_text_email, edit_text_password;
    private Button button_login;
    private TextView text_view_signup;
    private ProgressBar progress_bar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_text_email = (EditText) findViewById(R.id.edit_text_email);
        edit_text_password = (EditText) findViewById(R.id.edit_text_password);

        button_login = (Button) findViewById(R.id.button_login);

        text_view_signup = (TextView) findViewById(R.id.text_view_sign_up);

        progress_bar_login = (ProgressBar) findViewById(R.id.progress_bar_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void openSignUpActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void login(){
        final String email, password;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/login.php";
        email = edit_text_email.getText().toString().trim();
        password = edit_text_password.getText().toString().trim();

        if (email.isEmpty()) {
            edit_text_email.setError("Please fill this field");
            error = true;
        }

        if (password.isEmpty()) {
            edit_text_password.setError("Please fill this field");
            error = true;
        }

        if (!error){
            progress_bar_login.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress_bar_login.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("first_db_error")) {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();

                    } else if (response.trim().equals("second_db_error")) {
                        Toast.makeText(getApplicationContext(), "Error while fetching", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("credential_error")) {
                        Toast.makeText(getApplicationContext(), "Invalid email/password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            int id = jsonResponse.getInt("user_id");
                            String name = jsonResponse.getString("full_name");
                            String fetched_email = jsonResponse.getString("email");

                            User user = new User(id, name, fetched_email, password);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar_login.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(LoginActivity.this, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}