package com.example.automobilesnepal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.BrandCarsListActivity;
import com.example.automobilesnepal.CarDetailsActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.BikeBrandsAdapter;
import com.example.automobilesnepal.adapters.BikeSliderAdapter;
import com.example.automobilesnepal.adapters.BikesAdapter;
import com.example.automobilesnepal.adapters.CarBrandsAdapter;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.adapters.SliderAdapterExample;
import com.example.automobilesnepal.adapters.UsedBikesAdapter;
import com.example.automobilesnepal.adapters.UsedCarsAdapter;
import com.example.automobilesnepal.models.BikeBrandsModel;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.UsedBikesModel;
import com.example.automobilesnepal.models.UsedCarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BikesFragment extends Fragment {
    SliderView sliderView;
    private BikeSliderAdapter adapter;
    private RecyclerView recycler_view_bike_brands, recycler_view_new_bikes, recycler_view_used_bikes;
    private ArrayList<BikeBrandsModel> bikeBrandsModelArrayList;
    private ArrayList<BikesModel> newBikesModelArrayList;
    private ArrayList<UsedBikesModel> usedBikesModelArrayList;
    private BikeBrandsAdapter bikeBrandsAdapter;
    private BikesAdapter newBikesAdapter;
    private UsedBikesAdapter usedBikesAdapter;

    private Button button_sell_bike, button_compare_bikes, button_bike_accessories, button_bike_valuation;
    private TextView text_view_view_all_bike_brands;
    private TextView text_view_view_all_new_bikes;
    private TextView text_view_view_all_used_bikes;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bikes, container, false);

        image_button_bar = view.findViewById(R.id.image_button_bar);
        image_button_heart = view.findViewById(R.id.image_button_heart);
        searchView = view.findViewById(R.id.search_view);

        recycler_view_bike_brands =view.findViewById(R.id.bike_brands_recycler_view);
        recycler_view_new_bikes =view.findViewById(R.id.new_bikes_recycler_view);
        recycler_view_used_bikes = view.findViewById(R.id.used_bikes_recycler_view);

        bikeBrandsModelArrayList = new ArrayList<>();
        newBikesModelArrayList = new ArrayList<>();
        usedBikesModelArrayList = new ArrayList<>();

        bikeBrandsAdapter = new BikeBrandsAdapter(bikeBrandsModelArrayList, getContext());
        newBikesAdapter = new BikesAdapter(newBikesModelArrayList, getContext());
        usedBikesAdapter = new UsedBikesAdapter(usedBikesModelArrayList, getContext());

        sliderView = view.findViewById(R.id.image_slider_bikes);
        adapter = new BikeSliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        //sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        //sliderView.setIndicatorSelectedColor(Color.WHITE);
        //sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        text_view_view_all_bike_brands = view.findViewById(R.id.text_view_view_all_bike_brands);
        text_view_view_all_new_bikes = view.findViewById(R.id.text_view_view_all_new_bikes);
        text_view_view_all_used_bikes = view.findViewById(R.id.text_view_view_all_used_bikes);

        button_sell_bike = view.findViewById(R.id.button_sell_bike);
        button_compare_bikes = view.findViewById(R.id.button_compare_bikes);
        button_bike_accessories = view.findViewById(R.id.button_bike_accessories);
        button_bike_valuation = view.findViewById(R.id.button_bike_valuation);

        text_view_view_all_bike_brands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new BikeBrandsFragment());
            }
        });

        text_view_view_all_new_bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new AllNewBikesFragment());
            }
        });

        text_view_view_all_used_bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new AllUsedBikesFragment());
            }
        });

        button_sell_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new SellBikeFragment());
            }
        });

        button_compare_bikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CompareBikesFragment());
            }
        });

        button_bike_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new BikeAccessoriesFragment());
            }
        });

        button_bike_valuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CarValuationFragment());
            }
        });

        addNewItem();

        getBrands();
        getNewBikes();
        getUsedBikes();

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void getBrands(){
        String url = "http://192.168.1.65:81/android/get_bike_brands.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("first_db_error")) {
                    Toast.makeText(getContext(), "Fetch Database Error", Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int brand_id = jsonResponse.getInt("bike_brand_id");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");

                            BikeBrandsModel bikeBrandsModel = new BikeBrandsModel(brand_id, brand_name, brand_logo);
                            bikeBrandsModelArrayList.add(bikeBrandsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_bike_brands.setLayoutManager(linearLayoutManager);
                        recycler_view_bike_brands.setAdapter(bikeBrandsAdapter);
                        recycler_view_bike_brands.addItemDecoration(new SpacesItemDecoration(20));
                        bikeBrandsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_bike_brands).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                BikeBrandsModel bike_brands_model = bikeBrandsModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("brand_bikes_list", bike_brands_model);
                                Fragment brandBikesListFragment = new BrandBikesListFragment();
                                brandBikesListFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, brandBikesListFragment)
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

        };
        requestQueue.add(stringRequest);

    }

    private void getNewBikes(){
        String url = "http://192.168.1.65:81/android/get_new_bikes.php";

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
                            newBikesModelArrayList.add(bikesModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_new_bikes.setLayoutManager(linearLayoutManager);
                        recycler_view_new_bikes.setAdapter(newBikesAdapter);
                        recycler_view_new_bikes.addItemDecoration(new SpacesItemDecoration(20));
                        newBikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_new_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                BikesModel bike_model = newBikesModelArrayList.get(position);
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

        };
        requestQueue.add(stringRequest);

    }

    private void getUsedBikes(){
        String url = "http://192.168.1.65:81/android/get_used_bikes.php";

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
                        recycler_view_used_bikes.setLayoutManager(linearLayoutManager);
                        recycler_view_used_bikes.setAdapter(usedBikesAdapter);
                        recycler_view_used_bikes.addItemDecoration(new SpacesItemDecoration(20));
                        usedBikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_used_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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

        };
        requestQueue.add(stringRequest);

    }

    public void addNewItem() {
        String url = "http://192.168.1.65:81/android/get_featured_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "Slider Database Error", Toast.LENGTH_SHORT).show();

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
                            adapter.addItem(bikesModel);
                        }

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

        };
        requestQueue.add(stringRequest);

    }

}
