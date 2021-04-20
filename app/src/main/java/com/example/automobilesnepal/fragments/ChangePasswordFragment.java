package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.MainActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ChangePasswordFragment extends Fragment {
    private User user;
    private EditText edit_text_old_password, edit_text_new_password;
    private Button button_change_password;
    private int user_id;
    private String user_name, user_email;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(true);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        user_id = user.getId();
        user_name = user.getFull_name();
        user_email = user.getEmail();

        edit_text_old_password = view.findViewById(R.id.edit_text_old_password);
        edit_text_new_password = view.findViewById(R.id.edit_text_new_password);
        button_change_password = view.findViewById(R.id.button_change_password);

        edit_text_old_password.addTextChangedListener(oldPasswordTextWatcher);
        edit_text_new_password.addTextChangedListener(finalPasswordTextWatcher);


        button_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        return view;
    }

    private TextWatcher oldPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pass = edit_text_old_password.getText().toString().trim();
            String pass2 = user.getPassword();
            if (!pass.equals(pass2)){
                edit_text_old_password.setError("Password doesn't match");
                button_change_password.setEnabled(false);
            }
            else {
                edit_text_old_password.setError(null);
                button_change_password.setEnabled(true);
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
            String pass = edit_text_new_password.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(pass).matches()){
                edit_text_new_password.setError("Password must contain one uppercase, lowercase, digit, special character and must be 6 character long");
                button_change_password.setEnabled(false);

            }
            else {
                edit_text_new_password.setError(null);
                button_change_password.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void changePassword(){
        final String new_password, old_password;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/change_password.php";
        new_password = edit_text_new_password.getText().toString().trim();
        old_password = edit_text_old_password.getText().toString().trim();

        if (new_password.isEmpty()){
            edit_text_new_password.setError("Please fill this field");
            error = true;
        }
        if (old_password.isEmpty()){
            edit_text_old_password.setError("Please fill this field");
            error = true;
        }

        if (!error) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        User user = new User(user_id, user_name, user_email, new_password);
                        SharedPrefManager.getInstance(getContext()).userLogin(user);
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, new CarsFragment()).addToBackStack(null).commit();
                    } else if (response.trim().equals("dbError")) {
                        Toast.makeText(getContext(), "Error while inserting", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("dbError2")) {
                        Toast.makeText(getContext(), "Error while fetching", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("email_taken")) {
                        Toast.makeText(getContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("new_password", new_password);
                    params.put("user_id", String.valueOf(user.getId()));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(false);    // to show the bottom bar when
        // we destroy this fragment
    }
}
