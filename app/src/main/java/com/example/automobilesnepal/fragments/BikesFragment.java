package com.example.automobilesnepal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.automobilesnepal.BrandCarsListActivity;
import com.example.automobilesnepal.CarDetailsActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarBrandsAdapter;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ItemClickSupport;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BikesFragment extends Fragment {
    private RecyclerView recycler_view_bike_brands, recycler_view_new_bikes, recycler_view_used_bikes;
    private ArrayList<CarBrandsModel> bikeBrandsModelArrayList;
    private ArrayList<CarsModel> newBikesModelArrayList;
    private ArrayList<CarsModel> usedBikesModelArrayList;
    private CarBrandsAdapter bikeBrandsAdapter;
    private CarsAdapter newBikesAdapter;
    private CarsAdapter usedBikesAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bikes, container, false);

        recycler_view_bike_brands =view.findViewById(R.id.bike_brands_recycler_view);
        recycler_view_new_bikes =view.findViewById(R.id.new_bikes_recycler_view);
        recycler_view_used_bikes = view.findViewById(R.id.used_bikes_recycler_view);
        bikeBrandsModelArrayList = new ArrayList<>();
        newBikesModelArrayList = new ArrayList<>();
        usedBikesModelArrayList = new ArrayList<>();
        bikeBrandsAdapter = new CarBrandsAdapter(bikeBrandsModelArrayList, getContext());
        newBikesAdapter = new CarsAdapter(newBikesModelArrayList, getContext());
        usedBikesAdapter = new CarsAdapter(usedBikesModelArrayList, getContext());

        getBrands();
        getNewBikes();
        getUsedBikes();

        return view;
    }

    private void getBrands(){
        CarBrandsModel carBrandsModel = new CarBrandsModel("bajaj", "Bajaj");
        CarBrandsModel carBrandsModel2 = new CarBrandsModel("benelli", "Benelli");
        CarBrandsModel carBrandsModel3 = new CarBrandsModel("crossfire", "Crossfire");
        CarBrandsModel carBrandsModel4 = new CarBrandsModel("honda", "Honda");

        bikeBrandsModelArrayList.add(carBrandsModel);
        bikeBrandsModelArrayList.add(carBrandsModel2);
        bikeBrandsModelArrayList.add(carBrandsModel3);
        bikeBrandsModelArrayList.add(carBrandsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_bike_brands.setLayoutManager(linearLayoutManager);
        recycler_view_bike_brands.setAdapter(bikeBrandsAdapter);
        bikeBrandsAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_bike_brands).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarBrandsModel car_brands_model = bikeBrandsModelArrayList.get(position);
                Intent intent = new Intent(getContext(), BrandCarsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("brand_cars_list", car_brands_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getNewBikes(){
        CarsModel carsModel = new CarsModel(1, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel2 = new CarsModel(2, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel3 = new CarsModel(3, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel4 = new CarsModel(4, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");

        newBikesModelArrayList.add(carsModel);
        newBikesModelArrayList.add(carsModel2);
        newBikesModelArrayList.add(carsModel3);
        newBikesModelArrayList.add(carsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_new_bikes.setLayoutManager(linearLayoutManager);
        recycler_view_new_bikes.setAdapter(newBikesAdapter);
        newBikesAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_new_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = newBikesModelArrayList.get(position);
                Intent intent = new Intent(getContext(), CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getUsedBikes(){
        CarsModel carsModel = new CarsModel(1, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel2 = new CarsModel(2, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel3 = new CarsModel(3, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel4 = new CarsModel(4, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");

        usedBikesModelArrayList.add(carsModel);
        usedBikesModelArrayList.add(carsModel2);
        usedBikesModelArrayList.add(carsModel3);
        usedBikesModelArrayList.add(carsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_used_bikes.setLayoutManager(linearLayoutManager);
        recycler_view_used_bikes.setAdapter(usedBikesAdapter);
        usedBikesAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_used_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = newBikesModelArrayList.get(position);
                Intent intent = new Intent(getContext(), CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}
