package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.automobilesnepal.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BikesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bikes, container, false);
        return view;
    }

}
