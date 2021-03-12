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

public class CarsFragment extends Fragment {
    private RecyclerView recycler_view_brands, recycler_view_new_cars, recycler_view_used_cars;
    private ArrayList<CarBrandsModel> carBrandsModelArrayList;
    private ArrayList<CarsModel> newCarsModelArrayList;
    private ArrayList<CarsModel> usedCarsModelArrayList;
    private CarBrandsAdapter carBrandsAdapter;
    private CarsAdapter newCarsAdapter;
    private CarsAdapter usedCarsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        recycler_view_brands =view.findViewById(R.id.brands_recycler_view);
        recycler_view_new_cars =view.findViewById(R.id.new_cars_recycler_view);
        recycler_view_used_cars = view.findViewById(R.id.used_cars_recycler_view);
        carBrandsModelArrayList = new ArrayList<>();
        newCarsModelArrayList = new ArrayList<>();
        usedCarsModelArrayList = new ArrayList<>();
        carBrandsAdapter = new CarBrandsAdapter(carBrandsModelArrayList, getContext());
        newCarsAdapter = new CarsAdapter(newCarsModelArrayList, getContext());
        usedCarsAdapter = new CarsAdapter(usedCarsModelArrayList, getContext());

        getBrands();
        getNewCars();
        getUsedCars();
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

        ItemClickSupport.addTo(recycler_view_brands).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarBrandsModel car_brands_model = carBrandsModelArrayList.get(position);
                Intent intent = new Intent(getContext(), BrandCarsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("brand_cars_list", car_brands_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getNewCars(){
        CarsModel carsModel = new CarsModel("mahindra_thar", "Mahindra Thar", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel2 = new CarsModel("maruti_swift", "Suzuki Swift", "Maruti", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel3 = new CarsModel("hyundai_i20", "Hyundai I20", "Hyundai", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel4 = new CarsModel("lamborghini", "Urus Pearl", "Lamborghini", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");

        newCarsModelArrayList.add(carsModel);
        newCarsModelArrayList.add(carsModel2);
        newCarsModelArrayList.add(carsModel3);
        newCarsModelArrayList.add(carsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_new_cars.setLayoutManager(linearLayoutManager);
        recycler_view_new_cars.setAdapter(newCarsAdapter);
        newCarsAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_new_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = newCarsModelArrayList.get(position);
                Intent intent = new Intent(getContext(), CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getUsedCars(){
        CarsModel carsModel = new CarsModel("mahindra_thar", "Mahindra", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel2 = new CarsModel("maruti_swift", "Suzuki", "Maruti", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel3 = new CarsModel("hyundai_i20", "Hyundai", "Hyundai", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");
        CarsModel carsModel4 = new CarsModel("lamborghini", "Urus", "Lamborghini", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.");

        usedCarsModelArrayList.add(carsModel);
        usedCarsModelArrayList.add(carsModel2);
        usedCarsModelArrayList.add(carsModel3);
        usedCarsModelArrayList.add(carsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_used_cars.setLayoutManager(linearLayoutManager);
        recycler_view_used_cars.setAdapter(usedCarsAdapter);
        usedCarsAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_used_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = newCarsModelArrayList.get(position);
                Intent intent = new Intent(getContext(), CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
