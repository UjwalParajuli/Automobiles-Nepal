package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CompareCarDetailsFragment extends Fragment {
    private Bundle bundle;
    private CarsModel carsModel1;
    private CarsModel carsModel2;

    private TextView text_view_detail_comparison_car_1_mileage, text_view_detail_comparison_car_2_mileage, text_view_detail_comparison_car_1_fuel_type,
            text_view_detail_comparison_car_2_fuel_type, text_view_detail_comparison_car_1_displacement, text_view_detail_comparison_car_2_displacement,
            text_view_detail_comparison_car_1_max_power, text_view_detail_comparison_car_2_max_power, text_view_detail_comparison_car_1_max_torque,
            text_view_detail_comparison_car_2_max_torque, text_view_detail_comparison_car_1_seat_capacity, text_view_detail_comparison_car_2_seat_capacity,
            text_view_detail_comparison_car_1_transmission_type, text_view_detail_comparison_car_2_transmission_type, text_view_detail_comparison_car_1_boot_space,
            text_view_detail_comparison_car_2_boot_space, text_view_detail_comparison_car_1_fuel_capacity, text_view_detail_comparison_car_2_fuel_capacity,
            text_view_detail_comparison_car_1_body_type, text_view_detail_comparison_car_2_body_type, text_view_detail_comparison_car_1_name, text_view_detail_comparison_car_2_name,
            text_view_detail_comparison_car_1_price, text_view_detail_comparison_car_2_price;

    private ImageView image_view_detail_comparison_car_1_logo, image_view_detail_comparison_car_2_logo, image_view_detail_comparison_car_1_photo, image_view_detail_comparison_car_2_photo;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_car_details, container, false);

        bundle = getArguments();
        carsModel1 = (CarsModel) bundle.getSerializable("first_car");
        carsModel2 = (CarsModel) bundle.getSerializable("second_car");

        text_view_detail_comparison_car_1_mileage = view.findViewById(R.id.text_view_detail_comparison_car_1_mileage);
        text_view_detail_comparison_car_2_mileage = view.findViewById(R.id.text_view_detail_comparison_car_2_mileage);
        text_view_detail_comparison_car_1_fuel_type = view.findViewById(R.id.text_view_detail_comparison_car_1_fuel_type);
        text_view_detail_comparison_car_2_fuel_type = view.findViewById(R.id.text_view_detail_comparison_car_2_fuel_type);
        text_view_detail_comparison_car_1_displacement = view.findViewById(R.id.text_view_detail_comparison_car_1_displacement);
        text_view_detail_comparison_car_2_displacement = view.findViewById(R.id.text_view_detail_comparison_car_2_displacement);
        text_view_detail_comparison_car_1_max_power = view.findViewById(R.id.text_view_detail_comparison_car_1_max_power);
        text_view_detail_comparison_car_2_max_power = view.findViewById(R.id.text_view_detail_comparison_car_2_max_power);
        text_view_detail_comparison_car_1_max_torque = view.findViewById(R.id.text_view_detail_comparison_car_1_max_torque);
        text_view_detail_comparison_car_2_max_torque = view.findViewById(R.id.text_view_detail_comparison_car_2_max_torque);
        text_view_detail_comparison_car_1_seat_capacity = view.findViewById(R.id.text_view_detail_comparison_car_1_seat_capacity);
        text_view_detail_comparison_car_2_seat_capacity = view.findViewById(R.id.text_view_detail_comparison_car_2_seat_capacity);
        text_view_detail_comparison_car_1_transmission_type = view.findViewById(R.id.text_view_detail_comparison_car_1_transmission_type);
        text_view_detail_comparison_car_2_transmission_type = view.findViewById(R.id.text_view_detail_comparison_car_2_transmission_type);
        text_view_detail_comparison_car_1_boot_space = view.findViewById(R.id.text_view_detail_comparison_car_1_boot_space);
        text_view_detail_comparison_car_2_boot_space = view.findViewById(R.id.text_view_detail_comparison_car_2_boot_space);
        text_view_detail_comparison_car_1_fuel_capacity = view.findViewById(R.id.text_view_detail_comparison_car_1_fuel_capacity);
        text_view_detail_comparison_car_2_fuel_capacity = view.findViewById(R.id.text_view_detail_comparison_car_2_fuel_capacity);
        text_view_detail_comparison_car_1_body_type = view.findViewById(R.id.text_view_detail_comparison_car_1_body_type);
        text_view_detail_comparison_car_2_body_type = view.findViewById(R.id.text_view_detail_comparison_car_2_body_type);
        text_view_detail_comparison_car_1_name = view.findViewById(R.id.text_view_detail_comparison_car_1_name);
        text_view_detail_comparison_car_2_name = view.findViewById(R.id.text_view_detail_comparison_car_2_name);
        text_view_detail_comparison_car_1_price = view.findViewById(R.id.text_view_detail_comparison_car_1_price);
        text_view_detail_comparison_car_2_price = view.findViewById(R.id.text_view_detail_comparison_car_2_price);

        image_view_detail_comparison_car_1_logo = view.findViewById(R.id.image_view_detail_comparison_car_1_logo);
        image_view_detail_comparison_car_2_logo = view.findViewById(R.id.image_view_detail_comparison_car_2_logo);
        image_view_detail_comparison_car_1_photo = view.findViewById(R.id.image_view_detail_comparison_car_1_photo);
        image_view_detail_comparison_car_2_photo = view.findViewById(R.id.image_view_detail_comparison_car_2_photo);

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

        setData();

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void setData(){
        Glide.with(getContext()).load(carsModel1.getBrand_logo()).into(image_view_detail_comparison_car_1_logo);
        Glide.with(getContext()).load(carsModel2.getBrand_logo()).into(image_view_detail_comparison_car_2_logo);
        Glide.with(getContext()).load(carsModel1.getCar_image()).into(image_view_detail_comparison_car_1_photo);
        Glide.with(getContext()).load(carsModel2.getCar_image()).into(image_view_detail_comparison_car_2_photo);

        text_view_detail_comparison_car_1_name.setText(carsModel1.getCar_name());
        text_view_detail_comparison_car_2_name.setText(carsModel2.getCar_name());
        text_view_detail_comparison_car_1_price.setText(carsModel1.getCar_price());
        text_view_detail_comparison_car_2_price.setText(carsModel2.getCar_price());
        text_view_detail_comparison_car_1_mileage.setText(carsModel1.getCar_mileage());
        text_view_detail_comparison_car_2_mileage.setText(carsModel2.getCar_mileage());
        text_view_detail_comparison_car_1_fuel_type.setText(carsModel1.getCar_fuel_type());
        text_view_detail_comparison_car_2_fuel_type.setText(carsModel2.getCar_fuel_type());
        text_view_detail_comparison_car_1_displacement.setText(carsModel1.getCar_displacement());
        text_view_detail_comparison_car_2_displacement.setText(carsModel2.getCar_displacement());
        text_view_detail_comparison_car_1_max_power.setText(carsModel1.getCar_max_power());
        text_view_detail_comparison_car_2_max_power.setText(carsModel2.getCar_max_power());
        text_view_detail_comparison_car_1_max_torque.setText(carsModel1.getCar_max_torque());
        text_view_detail_comparison_car_2_max_torque.setText(carsModel2.getCar_max_torque());
        text_view_detail_comparison_car_1_seat_capacity.setText(carsModel1.getCar_seat_capacity());
        text_view_detail_comparison_car_2_seat_capacity.setText(carsModel2.getCar_seat_capacity());
        text_view_detail_comparison_car_1_transmission_type.setText(carsModel1.getCar_transmission_type());
        text_view_detail_comparison_car_2_transmission_type.setText(carsModel2.getCar_transmission_type());
        text_view_detail_comparison_car_1_boot_space.setText(carsModel1.getCar_boot_space());
        text_view_detail_comparison_car_2_boot_space.setText(carsModel2.getCar_boot_space());
        text_view_detail_comparison_car_1_fuel_capacity.setText(carsModel1.getCar_fuel_capacity());
        text_view_detail_comparison_car_2_fuel_capacity.setText(carsModel2.getCar_fuel_capacity());
        text_view_detail_comparison_car_1_body_type.setText(carsModel1.getCar_body_type());
        text_view_detail_comparison_car_2_body_type.setText(carsModel2.getCar_body_type());

    }
}
