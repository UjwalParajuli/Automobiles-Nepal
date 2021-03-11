package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarBrandsAdapter;
import com.example.automobilesnepal.models.CarBrandsModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarsFragment extends Fragment {
    private RecyclerView recycler_view_brands, recycler_view_new_cars, recycler_view_used_cars;
    private ArrayList<CarBrandsModel> carBrandsModelArrayList;
    private CarBrandsAdapter carBrandsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        recycler_view_brands =view.findViewById(R.id.brands_recycler_view);
        carBrandsModelArrayList = new ArrayList<>();
        carBrandsAdapter = new CarBrandsAdapter(carBrandsModelArrayList, getContext());
        getBrands();
        return view;
    }

    private void getBrands(){
        CarBrandsModel carBrandsModel = new CarBrandsModel("ford", "Ford");
        CarBrandsModel carBrandsModel2 = new CarBrandsModel("tesla", "Tesla");
        CarBrandsModel carBrandsModel3 = new CarBrandsModel("ferrari", "Ferrari");
        CarBrandsModel carBrandsModel4 = new CarBrandsModel("volkswagen", "Volkswagen");

        carBrandsModelArrayList.add(carBrandsModel);
        carBrandsModelArrayList.add(carBrandsModel2);
        carBrandsModelArrayList.add(carBrandsModel3);
        carBrandsModelArrayList.add(carBrandsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_brands.setLayoutManager(linearLayoutManager);
        recycler_view_brands.setAdapter(carBrandsAdapter);
        carBrandsAdapter.notifyDataSetChanged();



    }
}
