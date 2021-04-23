package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.AccessoriesModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CarAccessoryDetailsFragment extends Fragment {
    private Bundle bundle;
    private AccessoriesModel accessoriesModel;
    private ImageView image_view_car_accessory_details;
    private TextView text_view_accessory_name_car_accessory_details, text_view_price_car_accessory_details, text_view_type_car_accessory_details,
            text_view_quantity_car_accessory_details, text_view_description_car_accessory_details;
    private Button button_order_car_accessory;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_accessory_details, container, false);

        bundle = getArguments();
        accessoriesModel = (AccessoriesModel) bundle.getSerializable("accessory_details");

        image_view_car_accessory_details = view.findViewById(R.id.image_view_car_accessory_details);

        text_view_accessory_name_car_accessory_details = view.findViewById(R.id.text_view_accessory_name_car_accessory_details);
        text_view_price_car_accessory_details = view.findViewById(R.id.text_view_price_car_accessory_details);
        text_view_type_car_accessory_details = view.findViewById(R.id.text_view_type_car_accessory_details);
        text_view_quantity_car_accessory_details = view.findViewById(R.id.text_view_quantity_car_accessory_details);
        text_view_description_car_accessory_details = view.findViewById(R.id.text_view_description_car_accessory_details);

        button_order_car_accessory = view.findViewById(R.id.button_order_car_accessory);

        setData();

        button_order_car_accessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
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

    private void setData(){
        Glide.with(getContext()).load(accessoriesModel.getImage()).into(image_view_car_accessory_details);
        text_view_accessory_name_car_accessory_details.setText(accessoriesModel.getTitle());
        text_view_price_car_accessory_details.setText("Rs. " + accessoriesModel.getAccessory_price());
        text_view_type_car_accessory_details.setText(accessoriesModel.getType());
        text_view_quantity_car_accessory_details.setText(String.valueOf(accessoriesModel.getAvailable_quantity()));
        text_view_description_car_accessory_details.setText(accessoriesModel.getDescription());
    }

    private void confirmOrder(){
        if (accessoriesModel.getAvailable_quantity() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("accessory_details", accessoriesModel);
            Fragment checkoutAccessoryFragment = new CheckoutAccessoryFragment();
            checkoutAccessoryFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, checkoutAccessoryFragment)
                    .addToBackStack(null).commit();
        }
        else{
            Toast.makeText(getContext(), "Sorry, the available stock has been finished", Toast.LENGTH_SHORT).show();
        }

    }
}
