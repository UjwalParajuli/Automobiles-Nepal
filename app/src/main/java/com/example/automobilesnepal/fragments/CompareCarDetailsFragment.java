package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CompareCarDetailsFragment extends Fragment {
    private Bundle bundle;
    private CarsModel carsModel1;
    private CarsModel carsModel2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_car_details, container, false);

        bundle = getArguments();
        carsModel1 = (CarsModel) bundle.getSerializable("first_car");
        carsModel2 = (CarsModel) bundle.getSerializable("second_car");

        Toast.makeText(getContext(), carsModel1.getCar_name() + " " + carsModel2.getCar_name(), Toast.LENGTH_LONG).show();

        return view;
    }
}
