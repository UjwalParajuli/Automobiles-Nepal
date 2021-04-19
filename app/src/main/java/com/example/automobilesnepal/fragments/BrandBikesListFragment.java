package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.BikesAdapter;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.models.BikeBrandsModel;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.GridSpacingItemDecoration;
import com.example.automobilesnepal.utils.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrandBikesListFragment extends Fragment {
    private RecyclerView recycler_view_brand_bikes_list;
    private Bundle bundle;
    private BikeBrandsModel bikeBrandsModel;
    private TextView text_view_brand_name;
    private ImageView image_view_brand_logo;
    private ArrayList<BikesModel> bikesModelArrayList;
    private BikesAdapter bikesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_bikes_list, container, false);

        bundle = getArguments();
        bikeBrandsModel = (BikeBrandsModel) bundle.getSerializable("brand_bikes_list");
        recycler_view_brand_bikes_list = view.findViewById(R.id.recycler_view_fragment_brand_bikes_list);
        text_view_brand_name = view.findViewById(R.id.text_view_fragment_brand_bikes_list);
        image_view_brand_logo = view.findViewById(R.id.image_view_fragment_brand_bikes_list_logo);
        bikesModelArrayList = new ArrayList<>();
        bikesAdapter = new BikesAdapter(bikesModelArrayList, getContext());

        text_view_brand_name.setText(bikeBrandsModel.getBrand_name());
        Glide.with(getContext()).load(bikeBrandsModel.getBrand_logo()).into(image_view_brand_logo);

        getCars();

        return view;
    }

    private void getCars(){
        String url = "http://192.168.1.65:81/android/get_brand_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any bikes found", Toast.LENGTH_SHORT).show();

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

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_brand_bikes_list.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_brand_bikes_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_brand_bikes_list.setAdapter(bikesAdapter);
                        bikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_brand_bikes_list).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
                params.put("brand_id", String.valueOf(bikeBrandsModel.getBike_brand_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
