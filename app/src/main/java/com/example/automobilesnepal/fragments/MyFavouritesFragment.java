package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.MainActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.BikesAdapter;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFavouritesFragment extends Fragment {
    private RecyclerView recycler_view_fragment_favourite_cars, recycler_view_fragment_favourite_bikes;
    private ArrayList<CarsModel> carsModelArrayList;
    private ArrayList<BikesModel> bikesModelArrayList;
    private CarsAdapter carsAdapter;
    private BikesAdapter bikesAdapter;
    private TextView text_view_fav_bikes, text_view_fav_cars;
    private User user;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favourites, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(true);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        recycler_view_fragment_favourite_cars = view.findViewById(R.id.recycler_view_fragment_favourite_cars);
        recycler_view_fragment_favourite_bikes = view.findViewById(R.id.recycler_view_fragment_favourite_bikes);

        carsModelArrayList = new ArrayList<>();
        bikesModelArrayList = new ArrayList<>();

        carsAdapter = new CarsAdapter(carsModelArrayList, getContext());
        bikesAdapter = new BikesAdapter(bikesModelArrayList, getContext());

        text_view_fav_bikes = view.findViewById(R.id.text_view_fav_bikes);
        text_view_fav_cars = view.findViewById(R.id.text_view_fav_cars);

        getFavouriteCars();
        getFavouriteBikes();

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

    private void getFavouriteCars(){
        String url = "http://192.168.1.65:81/android/get_favourite_cars.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    text_view_fav_cars.setVisibility(View.GONE);
                    recycler_view_fragment_favourite_cars.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int car_model_id = jsonResponse.getInt("car_model_id");
                            String model_name = jsonResponse.getString("model_name");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");
                            String mileage = jsonResponse.getString("mileage");
                            String fuel_type = jsonResponse.getString("fuel_type");
                            String displacement = jsonResponse.getString("displacement");
                            String max_power = jsonResponse.getString("max_power");
                            String max_torque = jsonResponse.getString("max_torque");
                            String seat_capacity = jsonResponse.getString("seat_capacity");
                            String transmission_type = jsonResponse.getString("transmission_type");
                            String boot_space = jsonResponse.getString("boot_space");
                            String fuel_capacity = jsonResponse.getString("fuel_capacity");
                            String body_type = jsonResponse.getString("body_type");
                            String image = jsonResponse.getString("image");
                            String price = jsonResponse.getString("price");
                            String description = jsonResponse.getString("description");
                            String video_link = jsonResponse.getString("car_review_video_link");
                            String car_color = jsonResponse.getString("new_car_color");

                            CarsModel carsModel = new CarsModel(car_model_id, brand_logo, image, model_name, brand_name, description, mileage, fuel_type, displacement, max_power, price, max_torque, seat_capacity, transmission_type, boot_space, fuel_capacity, body_type, video_link, car_color);
                            carsModelArrayList.add(carsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_fragment_favourite_cars.setLayoutManager(linearLayoutManager);
                        recycler_view_fragment_favourite_cars.setAdapter(carsAdapter);
                        recycler_view_fragment_favourite_cars.addItemDecoration(new SpacesItemDecoration(20));
                        carsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_favourite_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                CarsModel car_model = carsModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("new_car_details", car_model);
                                Fragment newCarDetailsFragment = new NewCarDetailsFragment();
                                newCarDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, newCarDetailsFragment)
                                        .addToBackStack(null).commit();


                            }
                        });



                    }

                    catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void getFavouriteBikes(){
        String url = "http://192.168.1.65:81/android/get_favourite_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    text_view_fav_bikes.setVisibility(View.GONE);
                    recycler_view_fragment_favourite_bikes.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bike_model_id = jsonResponse.getInt("bike_model_id");
                            int bike_brands_id = jsonResponse.getInt("bike_brands_id");
                            String model_name = jsonResponse.getString("model_name");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");
                            String mileage = jsonResponse.getString("mileage");
                            String displacement = jsonResponse.getString("displacement");
                            String front_brake = jsonResponse.getString("front_brake");
                            String rear_brake = jsonResponse.getString("rear_brake");
                            String max_power = jsonResponse.getString("max_power");
                            String max_torque = jsonResponse.getString("max_torque");
                            String engine_type = jsonResponse.getString("engine_type");
                            String no_of_cylinders = jsonResponse.getString("no_of_cylinders");
                            String fuel_capacity = jsonResponse.getString("fuel_capacity");
                            String body_type = jsonResponse.getString("body_type");
                            String bike_photo = jsonResponse.getString("bike_photo");
                            String price = jsonResponse.getString("price");
                            String description = jsonResponse.getString("description");
                            String video_link = jsonResponse.getString("bike_review_video_link");
                            String bike_color = jsonResponse.getString("new_bike_color");

                            BikesModel bikesModel = new BikesModel(bike_model_id, bike_brands_id, model_name, mileage, displacement, engine_type, no_of_cylinders, max_power, max_torque, front_brake, rear_brake, fuel_capacity, body_type, price, description, bike_photo, brand_logo, brand_name, video_link, bike_color);
                            bikesModelArrayList.add(bikesModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_fragment_favourite_bikes.setLayoutManager(linearLayoutManager);
                        recycler_view_fragment_favourite_bikes.setAdapter(bikesAdapter);
                        recycler_view_fragment_favourite_bikes.addItemDecoration(new SpacesItemDecoration(20));
                        bikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_favourite_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                BikesModel bike_model = bikesModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("new_bike_details", bike_model);
                                Fragment newBikeDetailsFragment = new NewBikeDetailsFragment();
                                newBikeDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, newBikeDetailsFragment)
                                        .addToBackStack(null).commit();


                            }
                        });



                    }

                    catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

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
