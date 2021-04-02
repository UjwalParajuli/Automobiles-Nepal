package com.example.automobilesnepal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
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
    private ArrayList<CarsModel> usedCarsModelArrayList;
    private CarBrandsAdapter carBrandsAdapter;
    private CarsAdapter newCarsAdapter;
    private CarsAdapter usedCarsAdapter;

    private Button button_sell_car;
    private TextView text_view_view_all_brands;
    private TextView text_view_view_all_new_cars;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cars, container, false);

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
        usedCarsAdapter = new CarsAdapter(usedCarsModelArrayList, getContext());

        button_sell_car = view.findViewById(R.id.button_sell_car);

        text_view_view_all_brands = view.findViewById(R.id.text_view_view_all_brands);
        text_view_view_all_new_cars = view.findViewById(R.id.text_view_view_all_new_cars);

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

        User user = SharedPrefManager.getInstance(getContext()).getUser();

        Toast.makeText(getContext(), user.getId() + " " + user.getFull_name(), Toast.LENGTH_LONG).show();

        addNewItem();
        getBrands();
        getNewCars();
        getUsedCars();
        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void getBrands(){
        String url = "https://automobiles-nepal.000webhostapp.com/android/get_car_brands.php";

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
                            int brand_id = jsonResponse.getInt("brand_id");
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
        String url = "https://automobiles-nepal.000webhostapp.com/android/get_new_cars.php";

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

    private void getUsedCars(){
        CarsModel carsModel = new CarsModel(1, "thar","mahindra_thar", "Mahindra Thar", "Mahindra Thar", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel", "youtube", "green");
        CarsModel carsModel2 = new CarsModel(2, "thar","mahindra_thar", "Mahindra Thar", "Mahindra Thar", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel", "youtube", "green");
        CarsModel carsModel3 = new CarsModel(3, "thar","mahindra_thar", "Mahindra Thar", "Mahindra Thar", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel", "youtube", "green");
        CarsModel carsModel4 = new CarsModel(4, "thar","mahindra_thar", "Mahindra Thar","Mahindra Thar", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel", "youtube", "green");

        usedCarsModelArrayList.add(carsModel);
        usedCarsModelArrayList.add(carsModel2);
        usedCarsModelArrayList.add(carsModel3);
        usedCarsModelArrayList.add(carsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_used_cars.setLayoutManager(linearLayoutManager);
        recycler_view_used_cars.setAdapter(usedCarsAdapter);
        usedCarsAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_used_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = newCarsModelArrayList.get(position);
                Intent intent = new Intent(getContext(), CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void addNewItem() {
        String url = "https://automobiles-nepal.000webhostapp.com/android/get_featured_cars.php";

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
