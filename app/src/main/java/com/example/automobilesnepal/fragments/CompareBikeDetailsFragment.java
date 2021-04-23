package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.BikesModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CompareBikeDetailsFragment extends Fragment {
    private Bundle bundle;
    private BikesModel bikesModel1;
    private BikesModel bikesModel2;

    private TextView text_view_detail_comparison_bike_1_mileage, text_view_detail_comparison_bike_2_mileage, text_view_detail_comparison_bike_1_front_brake,
            text_view_detail_comparison_bike_2_front_brake, text_view_detail_comparison_bike_1_displacement, text_view_detail_comparison_bike_2_displacement,
            text_view_detail_comparison_bike_1_max_power, text_view_detail_comparison_bike_2_max_power, text_view_detail_comparison_bike_1_max_torque,
            text_view_detail_comparison_bike_2_max_torque, text_view_detail_comparison_bike_1_rear_brake, text_view_detail_comparison_bike_2_rear_brake,
            text_view_detail_comparison_bike_1_engine_type, text_view_detail_comparison_bike_2_engine_type, text_view_detail_comparison_bike_1_no_of_cylinders,
            text_view_detail_comparison_bike_2_no_of_cylinders, text_view_detail_comparison_bike_1_fuel_capacity, text_view_detail_comparison_bike_2_fuel_capacity,
            text_view_detail_comparison_bike_1_body_type, text_view_detail_comparison_bike_2_body_type, text_view_detail_comparison_bike_1_name, text_view_detail_comparison_bike_2_name,
            text_view_detail_comparison_bike_1_price, text_view_detail_comparison_bike_2_price;

    private ImageView image_view_detail_comparison_bike_1_logo, image_view_detail_comparison_bike_2_logo, image_view_detail_comparison_bike_1_photo, image_view_detail_comparison_bike_2_photo;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_bike_details, container, false);

        bundle = getArguments();
        bikesModel1 = (BikesModel) bundle.getSerializable("first_bike");
        bikesModel2 = (BikesModel) bundle.getSerializable("second_bike");

        text_view_detail_comparison_bike_1_mileage = view.findViewById(R.id.text_view_detail_comparison_bike_1_mileage);
        text_view_detail_comparison_bike_2_mileage = view.findViewById(R.id.text_view_detail_comparison_bike_2_mileage);
        text_view_detail_comparison_bike_1_front_brake = view.findViewById(R.id.text_view_detail_comparison_bike_1_front_brake);
        text_view_detail_comparison_bike_2_front_brake = view.findViewById(R.id.text_view_detail_comparison_bike_2_front_brake);
        text_view_detail_comparison_bike_1_displacement = view.findViewById(R.id.text_view_detail_comparison_bike_1_displacement);
        text_view_detail_comparison_bike_2_displacement = view.findViewById(R.id.text_view_detail_comparison_bike_2_displacement);
        text_view_detail_comparison_bike_1_max_power = view.findViewById(R.id.text_view_detail_comparison_bike_1_max_power);
        text_view_detail_comparison_bike_2_max_power = view.findViewById(R.id.text_view_detail_comparison_bike_2_max_power);
        text_view_detail_comparison_bike_1_max_torque = view.findViewById(R.id.text_view_detail_comparison_bike_1_max_torque);
        text_view_detail_comparison_bike_2_max_torque = view.findViewById(R.id.text_view_detail_comparison_bike_2_max_torque);
        text_view_detail_comparison_bike_1_rear_brake = view.findViewById(R.id.text_view_detail_comparison_bike_1_rear_brake);
        text_view_detail_comparison_bike_2_rear_brake = view.findViewById(R.id.text_view_detail_comparison_bike_2_rear_brake);
        text_view_detail_comparison_bike_1_engine_type = view.findViewById(R.id.text_view_detail_comparison_bike_1_engine_type);
        text_view_detail_comparison_bike_2_engine_type = view.findViewById(R.id.text_view_detail_comparison_bike_2_engine_type);
        text_view_detail_comparison_bike_1_no_of_cylinders = view.findViewById(R.id.text_view_detail_comparison_bike_1_no_of_cylinders);
        text_view_detail_comparison_bike_2_no_of_cylinders = view.findViewById(R.id.text_view_detail_comparison_bike_2_no_of_cylinders);
        text_view_detail_comparison_bike_1_fuel_capacity = view.findViewById(R.id.text_view_detail_comparison_bike_1_fuel_capacity);
        text_view_detail_comparison_bike_2_fuel_capacity = view.findViewById(R.id.text_view_detail_comparison_bike_2_fuel_capacity);
        text_view_detail_comparison_bike_1_body_type = view.findViewById(R.id.text_view_detail_comparison_bike_1_body_type);
        text_view_detail_comparison_bike_2_body_type = view.findViewById(R.id.text_view_detail_comparison_bike_2_body_type);
        text_view_detail_comparison_bike_1_name = view.findViewById(R.id.text_view_detail_comparison_bike_1_name);
        text_view_detail_comparison_bike_2_name = view.findViewById(R.id.text_view_detail_comparison_bike_2_name);
        text_view_detail_comparison_bike_1_price = view.findViewById(R.id.text_view_detail_comparison_bike_1_price);
        text_view_detail_comparison_bike_2_price = view.findViewById(R.id.text_view_detail_comparison_bike_2_price);

        image_view_detail_comparison_bike_1_logo = view.findViewById(R.id.image_view_detail_comparison_bike_1_logo);
        image_view_detail_comparison_bike_2_logo = view.findViewById(R.id.image_view_detail_comparison_bike_2_logo);
        image_view_detail_comparison_bike_1_photo = view.findViewById(R.id.image_view_detail_comparison_bike_1_photo);
        image_view_detail_comparison_bike_2_photo = view.findViewById(R.id.image_view_detail_comparison_bike_2_photo);

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
        Glide.with(getContext()).load(bikesModel1.getBrand_logo()).into(image_view_detail_comparison_bike_1_logo);
        Glide.with(getContext()).load(bikesModel2.getBrand_logo()).into(image_view_detail_comparison_bike_2_logo);
        Glide.with(getContext()).load(bikesModel1.getBike_photo()).into(image_view_detail_comparison_bike_1_photo);
        Glide.with(getContext()).load(bikesModel2.getBike_photo()).into(image_view_detail_comparison_bike_2_photo);

        text_view_detail_comparison_bike_1_name.setText(bikesModel1.getModel_name());
        text_view_detail_comparison_bike_2_name.setText(bikesModel2.getModel_name());
        text_view_detail_comparison_bike_1_price.setText(bikesModel1.getPrice());
        text_view_detail_comparison_bike_2_price.setText(bikesModel2.getPrice());
        text_view_detail_comparison_bike_1_mileage.setText(bikesModel1.getMileage());
        text_view_detail_comparison_bike_2_mileage.setText(bikesModel2.getMileage());
        text_view_detail_comparison_bike_1_front_brake.setText(bikesModel1.getFront_brake());
        text_view_detail_comparison_bike_2_front_brake.setText(bikesModel2.getFront_brake());
        text_view_detail_comparison_bike_1_displacement.setText(bikesModel1.getDisplacement());
        text_view_detail_comparison_bike_2_displacement.setText(bikesModel2.getDisplacement());
        text_view_detail_comparison_bike_1_max_power.setText(bikesModel1.getMax_power());
        text_view_detail_comparison_bike_2_max_power.setText(bikesModel2.getMax_power());
        text_view_detail_comparison_bike_1_max_torque.setText(bikesModel1.getMax_torque());
        text_view_detail_comparison_bike_2_max_torque.setText(bikesModel2.getMax_torque());
        text_view_detail_comparison_bike_1_rear_brake.setText(bikesModel1.getRear_brake());
        text_view_detail_comparison_bike_2_rear_brake.setText(bikesModel2.getRear_brake());
        text_view_detail_comparison_bike_1_engine_type.setText(bikesModel1.getEngine_type());
        text_view_detail_comparison_bike_2_engine_type.setText(bikesModel2.getEngine_type());
        text_view_detail_comparison_bike_1_no_of_cylinders.setText(bikesModel1.getNo_of_cylinders());
        text_view_detail_comparison_bike_2_no_of_cylinders.setText(bikesModel2.getNo_of_cylinders());
        text_view_detail_comparison_bike_1_fuel_capacity.setText(bikesModel1.getFuel_capacity());
        text_view_detail_comparison_bike_2_fuel_capacity.setText(bikesModel2.getFuel_capacity());
        text_view_detail_comparison_bike_1_body_type.setText(bikesModel1.getBody_type());
        text_view_detail_comparison_bike_2_body_type.setText(bikesModel2.getBody_type());

    }
}
