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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarsAdapter;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrandCarsListFragment extends Fragment {
    private RecyclerView recycler_view_brand_cars_list;
    private Bundle bundle;
    private CarBrandsModel carBrandsModel;
    private TextView text_view_brand_name;
    private ImageView image_view_brand_logo;
    private ArrayList<CarsModel> carsModelArrayList;
    private CarsAdapter carsAdapter;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_cars_list, container, false);

        bundle = getArguments();
        carBrandsModel = (CarBrandsModel) bundle.getSerializable("brand_cars_list");
        recycler_view_brand_cars_list = view.findViewById(R.id.recycler_view_fragment_brand_cars_list);
        text_view_brand_name = view.findViewById(R.id.text_view_fragment_brand_cars_list);
        image_view_brand_logo = view.findViewById(R.id.image_view_fragment_brand_cars_list_logo);
        carsModelArrayList = new ArrayList<>();
        carsAdapter = new CarsAdapter(carsModelArrayList, getContext());

        text_view_brand_name.setText(carBrandsModel.getBrand_name());
        Glide.with(getContext()).load(carBrandsModel.getBrand_logo()).into(image_view_brand_logo);

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

        getCars();
        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void getCars(){
        String url = "http://192.168.1.65:81/android/get_brand_cars.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any cars found", Toast.LENGTH_SHORT).show();

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

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_brand_cars_list.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_brand_cars_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_brand_cars_list.setAdapter(carsAdapter);
                        carsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_brand_cars_list).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
                params.put("brand_id", String.valueOf(carBrandsModel.getBrand_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }


}
