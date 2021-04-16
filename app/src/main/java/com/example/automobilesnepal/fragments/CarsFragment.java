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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.BrandCarsListActivity;
import com.example.automobilesnepal.CarDetailsActivity;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarBrandsAdapter;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.adapters.SliderAdapterExample;
import com.example.automobilesnepal.adapters.UsedCarsAdapter;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.UsedCarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.GridSpacingItemDecoration;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarsFragment extends Fragment {
    SliderView sliderView;
    private SliderAdapterExample adapter;
    private RecyclerView recycler_view_brands, recycler_view_new_cars, recycler_view_used_cars;
    private ArrayList<CarBrandsModel> carBrandsModelArrayList;
    private ArrayList<CarsModel> newCarsModelArrayList;
    private ArrayList<UsedCarsModel> usedCarsModelArrayList;
    private CarBrandsAdapter carBrandsAdapter;
    private CarsAdapter newCarsAdapter;
    private UsedCarsAdapter usedCarsAdapter;

    private Button button_sell_car, button_compare_car, button_accessories, button_car_valuation;
    private TextView text_view_view_all_brands;
    private TextView text_view_view_all_new_cars;
    private TextView text_view_view_all_used_cars;
    private ImageButton imageButton;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

        imageButton = view.findViewById(R.id.image_button_bar);
        searchView = view.findViewById(R.id.search_view);

        sliderView = view.findViewById(R.id.image_slider_cars);


        adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);
        //sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        //sliderView.setIndicatorSelectedColor(Color.WHITE);
        //sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        recycler_view_brands =view.findViewById(R.id.brands_recycler_view);
        recycler_view_new_cars =view.findViewById(R.id.new_cars_recycler_view);
        recycler_view_used_cars = view.findViewById(R.id.used_cars_recycler_view);

        carBrandsModelArrayList = new ArrayList<>();
        newCarsModelArrayList = new ArrayList<>();
        usedCarsModelArrayList = new ArrayList<>();

        carBrandsAdapter = new CarBrandsAdapter(carBrandsModelArrayList, getContext());
        newCarsAdapter = new CarsAdapter(newCarsModelArrayList, getContext());
        usedCarsAdapter = new UsedCarsAdapter(usedCarsModelArrayList, getContext());

        button_sell_car = view.findViewById(R.id.button_sell_car);
        button_compare_car = view.findViewById(R.id.button_compare_cars);
        button_accessories = view.findViewById(R.id.button_car_accessories);
        button_car_valuation = view.findViewById(R.id.button_car_valuation);

        text_view_view_all_brands = view.findViewById(R.id.text_view_view_all_brands);
        text_view_view_all_new_cars = view.findViewById(R.id.text_view_view_all_new_cars);
        text_view_view_all_used_cars = view.findViewById(R.id.text_view_view_all_used_cars);

        text_view_view_all_brands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CarBrandsFragment());
            }
        });

        text_view_view_all_new_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new AllNewCarsFragment());

            }
        });

        text_view_view_all_used_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new AllUsedCarsFragment());
            }
        });

        button_sell_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new SellCarFragment());
            }
        });

        button_compare_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CompareCarsFragment());
            }
        });

        button_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CarAccessoriesFragment());
            }
        });

        button_car_valuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new CarValuationFragment());
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked on cars fragment", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked on cars fragment", Toast.LENGTH_SHORT).show();
            }
        });

//        User user = SharedPrefManager.getInstance(getContext()).getUser();
//
//        Toast.makeText(getContext(), user.getId() + " " + user.getFull_name(), Toast.LENGTH_LONG).show();

        addNewItem();
        getNewCars();
        getUsedCars();
        getBrands();

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void getBrands(){
        String url = "http://192.168.1.65:81/android/get_car_brands.php";

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
                            int brand_id = jsonResponse.getInt("car_brand_id");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");

                            CarBrandsModel carBrandsModel = new CarBrandsModel(brand_logo, brand_name, brand_id);
                            carBrandsModelArrayList.add(carBrandsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_brands.setLayoutManager(linearLayoutManager);
                        recycler_view_brands.setAdapter(carBrandsAdapter);
                        recycler_view_brands.addItemDecoration(new SpacesItemDecoration(20));
                        carBrandsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_brands).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                CarBrandsModel car_brands_model = carBrandsModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("brand_cars_list", car_brands_model);
                                Fragment brandCarsListFragment = new BrandCarsListFragment();
                                brandCarsListFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, brandCarsListFragment)
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

    private void getNewCars(){
        String url = "http://192.168.1.65:81/android/get_new_cars.php";

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
                            newCarsModelArrayList.add(carsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_new_cars.setLayoutManager(linearLayoutManager);
                        recycler_view_new_cars.setAdapter(newCarsAdapter);
                        recycler_view_new_cars.addItemDecoration(new SpacesItemDecoration(20));
                        newCarsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_new_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                CarsModel car_model = newCarsModelArrayList.get(position);
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

        };
        requestQueue.add(stringRequest);

    }

    private void getUsedCars(){
        String url = "http://192.168.1.65:81/android/get_used_cars.php";

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

                            UsedCarsModel usedCarsModel = new UsedCarsModel(used_car_id, car_model_id, posted_by, no_of_previous_owner, total_kilometers, selling_price, registered_year, used_car_color, is_verified, selling_location, used_car_photo, posted_date, model_name);
                            usedCarsModelArrayList.add(usedCarsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recycler_view_used_cars.setLayoutManager(linearLayoutManager);
                        recycler_view_used_cars.setAdapter(usedCarsAdapter);
                        recycler_view_used_cars.addItemDecoration(new SpacesItemDecoration(20));
                        usedCarsAdapter.notifyDataSetChanged();

//                        ItemClickSupport.addTo(recycler_view_brand_cars_list).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                            @Override
//                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                                CarsModel car_model = carsModelArrayList.get(position);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("brand_cars_list", car_model);
//                                Fragment brandCarsListFragment = new BrandCarsListFragment();
//                                brandCarsListFragment.setArguments(bundle);
//                                getFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.fragment_container, brandCarsListFragment)
//                                        .addToBackStack(null).commit();
//                            }
//                        });


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
        String url = "http://192.168.1.65:81/android/get_featured_cars.php";

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
                            String video_link = "na";
                            String car_color = "na";

                            CarsModel carsModel = new CarsModel(car_model_id, brand_logo, image, model_name, brand_name, description, mileage, fuel_type, displacement, max_power, price, max_torque, seat_capacity, transmission_type, boot_space, fuel_capacity, body_type, video_link, car_color);
                            adapter.addItem(carsModel);
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
