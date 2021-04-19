package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.models.CarsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MyFavouritesFragment extends Fragment {
    private RecyclerView recycler_view_fragment_favourite_cars, recycler_view_fragment_favourite_bikes;
    private ArrayList<CarsModel> carsModelArrayList;
    private CarsAdapter carsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favourites, container, false);

        return view;
    }
}
