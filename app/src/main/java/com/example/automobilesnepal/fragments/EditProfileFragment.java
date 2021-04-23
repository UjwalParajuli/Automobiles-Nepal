package com.example.automobilesnepal.fragments;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.LoginActivity;
import com.example.automobilesnepal.MainActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.SignUpActivity;
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

public class EditProfileFragment extends Fragment {
    private User user;
    private String old_password, old_email;
    private int old_user_id;
    private EditText edit_text_edit_full_name, edit_text_edit_email;
    private Button button_edit_profile;

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

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(true);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        old_password = user.getPassword();
        old_user_id = user.getId();
        old_email = user.getEmail();

        edit_text_edit_full_name = view.findViewById(R.id.edit_text_edit_full_name);
        edit_text_edit_email = view.findViewById(R.id.edit_text_edit_email);
        button_edit_profile = view.findViewById(R.id.button_edit_profile);
        
        edit_text_edit_full_name.setText(user.getFull_name());
        edit_text_edit_email.setText(user.getEmail());

        edit_text_edit_full_name.addTextChangedListener(nameTextWatcher);
        edit_text_edit_email.addTextChangedListener(emailTextWatcher);
        

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        image_button_bar = view.findViewById(R.id.image_button_bar);
        image_button_heart = view.findViewById(R.id.image_button_heart);
        searchView = view.findViewById(R.id.search_view);

        image_button_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ProfileFragment());
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("searchQuery", query);
                Fragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, searchFragment).addToBackStack(null).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        image_button_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new MyFavouritesFragment());
            }
        });

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String full_name = edit_text_edit_full_name.getText().toString().trim();
            if (!full_name.matches("[a-zA-Z\\s]+")){
                edit_text_edit_full_name.setError("Please enter your name properly");
                button_edit_profile.setEnabled(false);
            }
            else {
                edit_text_edit_full_name.setError(null);
                button_edit_profile.setEnabled(true);
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
            String email = edit_text_edit_email.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edit_text_edit_email.setError("Please enter a valid email address");
                button_edit_profile.setEnabled(false);
            }
            else {
                edit_text_edit_email.setError(null);
                button_edit_profile.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void updateProfile(){
        final String full_name, new_email;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/update_user.php";
        full_name = edit_text_edit_full_name.getText().toString().trim();
        new_email = edit_text_edit_email.getText().toString().trim();

        if (full_name.isEmpty()){
            edit_text_edit_full_name.setError("Please fill this field");
            error = true;
        }
        if (!full_name.matches("[a-zA-Z\\s]+")){
            edit_text_edit_full_name.setError("Please enter your name properly");
            error = true;
        }
        if (new_email.isEmpty()){
            edit_text_edit_email.setError("Please fill this field");
            error = true;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(new_email).matches()) {
            edit_text_edit_email.setError("Please enter a valid email address");
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

                        User user = new User(old_user_id, full_name, new_email, old_password);
                        SharedPrefManager.getInstance(getContext()).userLogin(user);
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, new CarsFragment()).addToBackStack(null).commit();

                    }
                    else if (response.trim().equals("dbError")) {
                        Toast.makeText(getContext(), "Error while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("dbError2")) {
                        Toast.makeText(getContext(), "Error while fetching", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.trim().equals("email_taken")) {
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
                    params.put("full_name", full_name);
                    params.put("new_email", new_email);
                    params.put("old_email", old_email);
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
