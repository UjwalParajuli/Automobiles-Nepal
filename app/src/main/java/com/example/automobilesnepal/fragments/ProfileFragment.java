package com.example.automobilesnepal.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.automobilesnepal.LoginActivity;
import com.example.automobilesnepal.MainActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProfileFragment extends Fragment {
    private TextView text_view_profile_name, text_view_profile_details, text_view_edit_profile, text_view_edit_password, text_view_my_bookings, text_view_my_vehicles, text_view_logout;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(true);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        text_view_profile_name = view.findViewById(R.id.text_view_profile_name);
        text_view_profile_details = view.findViewById(R.id.text_view_profile_details);
        text_view_edit_profile = view.findViewById(R.id.text_view_edit_profile);
        text_view_edit_password = view.findViewById(R.id.text_view_edit_password);
        text_view_my_bookings = view.findViewById(R.id.text_view_my_bookings);
        text_view_my_vehicles = view.findViewById(R.id.text_view_my_vehicles);
        text_view_logout = view.findViewById(R.id.text_view_logout);

        text_view_profile_name.setText(user.getFull_name());
        text_view_profile_details.setText(user.getEmail());

        text_view_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new EditProfileFragment());
            }
        });

        text_view_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ChangePasswordFragment());
            }
        });

        text_view_my_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new MyBookingsFragment());
            }
        });

        text_view_my_vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new MyVehiclesFragment());
            }
        });

        text_view_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Logout?");
                builder.setMessage("Are you sure want to logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                        SharedPrefManager.getInstance(getContext()).logout();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
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
