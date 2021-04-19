package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.BikesAdapter;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.GridSpacingItemDecoration;
import com.example.automobilesnepal.utils.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllNewBikesFragment extends Fragment {

    private RecyclerView recycler_view_fragment_all_new_bikes;
    private ArrayList<BikesModel> bikesModelArrayList;
    private BikesAdapter bikesAdapter;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_new_bikes, container, false);

        recycler_view_fragment_all_new_bikes = view.findViewById(R.id.recycler_view_fragment_all_new_bikes);
        bikesModelArrayList = new ArrayList<>();
        bikesAdapter = new BikesAdapter(bikesModelArrayList, getContext());

        image_button_bar = view.findViewById(R.id.image_button_bar);
        image_button_heart = view.findViewById(R.id.image_button_heart);
        searchView = view.findViewById(R.id.search_view);

        getAllNewBikes();

        return view;
    }

    private void getAllNewBikes() {
        String url = "http://192.168.1.65:81/android/get_new_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any bikes found", Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++) {
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

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_fragment_all_new_bikes.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_fragment_all_new_bikes.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_fragment_all_new_bikes.setAdapter(bikesAdapter);
                        bikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_all_new_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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


                    } catch (JSONException e) {
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

        };
        requestQueue.add(stringRequest);
    }
}
