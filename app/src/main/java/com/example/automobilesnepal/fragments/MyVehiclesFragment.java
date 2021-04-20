package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.automobilesnepal.adapters.UsedBikesAdapter;
import com.example.automobilesnepal.adapters.UsedCarsAdapter;
import com.example.automobilesnepal.models.UsedBikesModel;
import com.example.automobilesnepal.models.UsedCarsModel;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyVehiclesFragment extends Fragment {
    private User user;
    private LinearLayout verified_cars, verified_bikes;
    private RecyclerView recycler_view_fragment_my_verified_bikes, recycler_view_fragment_my_verified_cars;
    private ArrayList<UsedCarsModel> usedCarsModelArrayList;
    private ArrayList<UsedBikesModel> usedBikesModelArrayList;
    private UsedCarsAdapter usedCarsAdapter;
    private UsedBikesAdapter usedBikesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_vehicles, container, false);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null)
            activity.hideBottomBar(true);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        verified_cars = view.findViewById(R.id.verified_cars);
        verified_bikes = view.findViewById(R.id.verified_bikes);
        recycler_view_fragment_my_verified_bikes = view.findViewById(R.id.recycler_view_fragment_my_verified_bikes);
        recycler_view_fragment_my_verified_cars = view.findViewById(R.id.recycler_view_fragment_my_verified_cars);

        usedBikesModelArrayList = new ArrayList<>();
        usedCarsModelArrayList = new ArrayList<>();

        usedCarsAdapter = new UsedCarsAdapter(usedCarsModelArrayList, getContext());
        usedBikesAdapter = new UsedBikesAdapter(usedBikesModelArrayList, getContext());

        getVerifiedCars();
        getVerifiedBikes();

        return view;
    }

    private void getVerifiedCars(){
        String url = "http://192.168.1.65:81/android/get_my_verified_cars.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    verified_cars.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int car_model_id = jsonResponse.getInt("car_model_id");
                            int used_car_id = jsonResponse.getInt("used_car_id");
                            int posted_by = jsonResponse.getInt("posted_by");
                            int no_of_previous_owner = jsonResponse.getInt("no_of_previous_owner");
                            int total_kilometers = jsonResponse.getInt("total_kilometers");
                            double selling_price = jsonResponse.getDouble("selling_price");
                            String model_name = jsonResponse.getString("model_name");
                            String registered_year = jsonResponse.getString("registered_year");
                            String used_car_color = jsonResponse.getString("used_car_color");
                            String is_verified = jsonResponse.getString("is_verified");
                            String selling_location = jsonResponse.getString("selling_location");
                            String used_car_photo = jsonResponse.getString("used_car_photo");
                            String posted_date = jsonResponse.getString("posted_date");
                            String brand_name = jsonResponse.getString("brand_name");

                            UsedCarsModel usedCarsModel = new UsedCarsModel(used_car_id, car_model_id, posted_by, no_of_previous_owner, total_kilometers, selling_price, registered_year, used_car_color, is_verified, selling_location, used_car_photo, posted_date, model_name, brand_name);
                            usedCarsModelArrayList.add(usedCarsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_fragment_my_verified_cars.setLayoutManager(linearLayoutManager);
                        recycler_view_fragment_my_verified_cars.setAdapter(usedCarsAdapter);
                        recycler_view_fragment_my_verified_cars.addItemDecoration(new SpacesItemDecoration(20));
                        usedCarsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_my_verified_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                UsedCarsModel usedCarsModel = usedCarsModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("used_car_details", usedCarsModel);
                                Fragment usedCarDetailsFragment = new UsedCarDetailsFragment();
                                usedCarDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, usedCarDetailsFragment)
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

    private void getVerifiedBikes(){
        String url = "http://192.168.1.65:81/android/get_my_verified_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    verified_bikes.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bike_model_id = jsonResponse.getInt("bike_model_id");
                            int used_bike_id = jsonResponse.getInt("used_bike_id");
                            int posted_by = jsonResponse.getInt("posted_by");
                            int no_of_previous_owner = jsonResponse.getInt("no_of_previous_owner");
                            int total_kilometers = jsonResponse.getInt("total_kilometers");
                            double selling_price = jsonResponse.getDouble("selling_price");
                            String model_name = jsonResponse.getString("model_name");
                            String registered_year = jsonResponse.getString("registered_year");
                            String used_bike_color = jsonResponse.getString("used_bike_color");
                            String is_verified = jsonResponse.getString("is_verified");
                            String selling_location = jsonResponse.getString("selling_location");
                            String used_bike_photo = jsonResponse.getString("used_bike_photo");
                            String posted_date = jsonResponse.getString("posted_date");
                            String brand_name = jsonResponse.getString("brand_name");

                            UsedBikesModel usedBikesModel = new UsedBikesModel(used_bike_id, bike_model_id, no_of_previous_owner, total_kilometers, posted_by, selling_price, registered_year, used_bike_color, is_verified, selling_location, used_bike_photo, posted_date, model_name, brand_name);
                            usedBikesModelArrayList.add(usedBikesModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_fragment_my_verified_bikes.setLayoutManager(linearLayoutManager);
                        recycler_view_fragment_my_verified_bikes.setAdapter(usedBikesAdapter);
                        recycler_view_fragment_my_verified_bikes.addItemDecoration(new SpacesItemDecoration(20));
                        usedBikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_my_verified_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                UsedBikesModel usedBikesModel = usedBikesModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("used_bike_details", usedBikesModel);
                                Fragment usedBikeDetailsFragment = new UsedBikeDetailsFragment();
                                usedBikeDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, usedBikeDetailsFragment)
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
}
