package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.example.automobilesnepal.adapters.CompareCarAdapter;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.GridSpacingItemDecoration;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.example.automobilesnepal.utils.VerticalSpacesItemDecoration;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompareCarsFragment extends Fragment {
    private LinearLayout linear_layout_compare_cars, linear_layout_car_1_details, linear_layout_car_2_details;
    private Button button_select_car_1, button_select_car_2;
    private ImageView image_view_car_1_brand_logo, image_view_car_2_brand_logo, image_view_car_1_photo, image_view_car_2_photo;
    private TextView text_view_car_1_name, text_view_car_1_price, text_view_car_2_name, text_view_car_2_price;

    private CarsModel carsModel;
    private CarsModel carsModel2;

    private RecyclerView recycler_view_compare_cars;
    private CompareCarAdapter compareCarAdapter;
    private ArrayList<CarsModel> carsModelArrayList;
    private ArrayList<CarsModel> carsModelArrayList2;

    private Bundle bundle;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_cars, container, false);

        linear_layout_compare_cars = view.findViewById(R.id.linear_layout_compare_cars);
        linear_layout_car_1_details = view.findViewById(R.id.linear_layout_car_1_details);
        linear_layout_car_2_details = view.findViewById(R.id.linear_layout_car_2_details);

        button_select_car_1 = view.findViewById(R.id.button_select_car_1);
        button_select_car_2 = view.findViewById(R.id.button_select_car_2);

        image_view_car_1_brand_logo = view.findViewById(R.id.image_view_compare_car_brand_logo);
        image_view_car_2_brand_logo = view.findViewById(R.id.image_view_compare_car_2_brand_logo);
        image_view_car_1_photo = view.findViewById(R.id.image_view_compare_car_1_photo);
        image_view_car_2_photo = view.findViewById(R.id.image_view_compare_car_2_photo);

        text_view_car_1_name = view.findViewById(R.id.text_view_compare_car_1_name);
        text_view_car_2_name = view.findViewById(R.id.text_view_compare_car_2_name);
        text_view_car_1_price = view.findViewById(R.id.text_view_car_1_price);
        text_view_car_2_price = view.findViewById(R.id.text_view_car_2_price);

        recycler_view_compare_cars = view.findViewById(R.id.recycler_view_car_comparisons);
        carsModelArrayList = new ArrayList<>();
        carsModelArrayList2 = new ArrayList<>();
        compareCarAdapter = new CompareCarAdapter(carsModelArrayList, carsModelArrayList2, getContext());

        bundle = new Bundle();

        getComparisonCars();

        button_select_car_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFirstCar(v);
            }
        });

        button_select_car_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSecondCar(v);
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


    private void selectFirstCar(View v){
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
                        PopupMenu menu = new PopupMenu(getContext(), v);

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int car_model_id = jsonResponse.getInt("car_model_id");
                            String model_name = jsonResponse.getString("model_name");

                            menu.getMenu().add(Menu.NONE, car_model_id, i +1, model_name);
                        }

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int i = item.getItemId();

                                String url = "http://192.168.1.65:81/android/get_compare_cars.php";

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

                                                jsonResponse = jsonArray.getJSONObject(0);
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

                                                carsModel = new CarsModel(car_model_id, brand_logo, image, model_name, brand_name, description, mileage, fuel_type, displacement, max_power, price, max_torque, seat_capacity, transmission_type, boot_space, fuel_capacity, body_type, video_link, car_color);

                                                ViewGroup.LayoutParams params = linear_layout_compare_cars.getLayoutParams();
                                                // Changes the height and width to the specified *pixels*
                                                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 194, getResources().getDisplayMetrics());
                                                params.height = height;
                                                linear_layout_compare_cars.setLayoutParams(params);
                                                button_select_car_1.setVisibility(View.GONE);
                                                linear_layout_car_1_details.setVisibility(View.VISIBLE);

                                                Glide.with(getContext()).load(carsModel.getBrand_logo()).into(image_view_car_1_brand_logo);
                                                Glide.with(getContext()).load(carsModel.getCar_image()).into(image_view_car_1_photo);
                                                text_view_car_1_name.setText(model_name);
                                                text_view_car_1_price.setText("NPR " + price);

                                                if (linear_layout_car_2_details.getVisibility() == View.VISIBLE){
                                                    bundle.putSerializable("second_car", carsModel2);
                                                    bundle.putSerializable("first_car", carsModel);
                                                    Fragment compareCarDetailsFragment = new CompareCarDetailsFragment();
                                                    compareCarDetailsFragment.setArguments(bundle);
                                                    getFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.fragment_container, compareCarDetailsFragment)
                                                            .addToBackStack(null).commit();

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
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("car_model_id", String.valueOf(i));
                                        return params;
                                    }

                                };
                                requestQueue.add(stringRequest);

                                return true;
                            }
                        });

                        menu.show();
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

    private void selectSecondCar(View v){
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
                        PopupMenu menu = new PopupMenu(getContext(), v);

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int car_model_id = jsonResponse.getInt("car_model_id");
                            String model_name = jsonResponse.getString("model_name");

                            menu.getMenu().add(Menu.NONE, car_model_id, i +1, model_name);
                        }

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int i = item.getItemId();

                                String url = "http://192.168.1.65:81/android/get_compare_cars.php";

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

                                                jsonResponse = jsonArray.getJSONObject(0);
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

                                                carsModel2 = new CarsModel(car_model_id, brand_logo, image, model_name, brand_name, description, mileage, fuel_type, displacement, max_power, price, max_torque, seat_capacity, transmission_type, boot_space, fuel_capacity, body_type, video_link, car_color);

                                                ViewGroup.LayoutParams params = linear_layout_compare_cars.getLayoutParams();
                                                // Changes the height and width to the specified *pixels*
                                                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 194, getResources().getDisplayMetrics());
                                                params.height = height;
                                                linear_layout_compare_cars.setLayoutParams(params);
                                                button_select_car_2.setVisibility(View.GONE);
                                                linear_layout_car_2_details.setVisibility(View.VISIBLE);

                                                Glide.with(getContext()).load(carsModel2.getBrand_logo()).into(image_view_car_2_brand_logo);
                                                Glide.with(getContext()).load(carsModel2.getCar_image()).into(image_view_car_2_photo);
                                                text_view_car_2_name.setText(model_name);
                                                text_view_car_2_price.setText("NPR " + price);

                                                if (linear_layout_car_1_details.getVisibility() == View.VISIBLE){
                                                    bundle.putSerializable("first_car", carsModel);
                                                    bundle.putSerializable("second_car", carsModel2);
                                                    Fragment compareCarDetailsFragment = new CompareCarDetailsFragment();
                                                    compareCarDetailsFragment.setArguments(bundle);
                                                    getFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.fragment_container, compareCarDetailsFragment)
                                                            .addToBackStack(null).commit();

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
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("car_model_id", String.valueOf(i));
                                        return params;
                                    }

                                };
                                requestQueue.add(stringRequest);

                                return true;
                            }
                        });

                        menu.show();
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

    private void getComparisonCars(){
        String url = "http://192.168.1.65:81/android/popular_comparisons.php";

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
                            int first_car_model_id = jsonResponse.getInt("first_car_model_id");
                            String first_model_name = jsonResponse.getString("first_car_model_name");
                            String first_brand_name = jsonResponse.getString("first_car_brand_name");
                            String first_brand_logo = jsonResponse.getString("first_car_brand_logo");
                            String first_mileage = jsonResponse.getString("first_car_mileage");
                            String first_fuel_type = jsonResponse.getString("first_car_fuel_type");
                            String first_displacement = jsonResponse.getString("first_car_displacement");
                            String first_max_power = jsonResponse.getString("first_car_max_power");
                            String first_max_torque = jsonResponse.getString("first_car_max_torque");
                            String first_seat_capacity = jsonResponse.getString("first_car_seat_capacity");
                            String first_transmission_type = jsonResponse.getString("first_car_transmission_type");
                            String first_boot_space = jsonResponse.getString("first_car_boot_space");
                            String first_fuel_capacity = jsonResponse.getString("first_car_fuel_capacity");
                            String first_body_type = jsonResponse.getString("first_car_body_type");
                            String first_image = jsonResponse.getString("first_car_image");
                            String first_price = jsonResponse.getString("first_car_price");
                            String first_description = jsonResponse.getString("first_car_description");
                            String first_video_link = jsonResponse.getString("first_car_video_link");
                            String first_car_color = jsonResponse.getString("first_car_color");

                            int second_car_model_id = jsonResponse.getInt("second_car_model_id");
                            String second_model_name = jsonResponse.getString("second_car_model_name");
                            String second_brand_name = jsonResponse.getString("second_car_brand_name");
                            String second_brand_logo = jsonResponse.getString("second_car_brand_logo");
                            String second_mileage = jsonResponse.getString("second_car_mileage");
                            String second_fuel_type = jsonResponse.getString("second_car_fuel_type");
                            String second_displacement = jsonResponse.getString("second_car_displacement");
                            String second_max_power = jsonResponse.getString("second_car_max_power");
                            String second_max_torque = jsonResponse.getString("second_car_max_torque");
                            String second_seat_capacity = jsonResponse.getString("second_car_seat_capacity");
                            String second_transmission_type = jsonResponse.getString("second_car_transmission_type");
                            String second_boot_space = jsonResponse.getString("second_car_boot_space");
                            String second_fuel_capacity = jsonResponse.getString("second_car_fuel_capacity");
                            String second_body_type = jsonResponse.getString("second_car_body_type");
                            String second_image = jsonResponse.getString("second_car_image");
                            String second_price = jsonResponse.getString("second_car_price");
                            String second_description = jsonResponse.getString("second_car_description");
                            String second_video_link = jsonResponse.getString("second_car_video_link");
                            String second_car_color = jsonResponse.getString("second_car_color");

                            CarsModel carsModel = new CarsModel(first_car_model_id, first_brand_logo, first_image, first_model_name, first_brand_name, first_description, first_mileage, first_fuel_type, first_displacement, first_max_power, first_price, first_max_torque, first_seat_capacity, first_transmission_type, first_boot_space, first_fuel_capacity, first_body_type, first_video_link, first_car_color);
                            carsModelArrayList.add(carsModel);

                            CarsModel carsModel2 = new CarsModel(second_car_model_id, second_brand_logo, second_image, second_model_name, second_brand_name, second_description, second_mileage, second_fuel_type, second_displacement, second_max_power, second_price, second_max_torque, second_seat_capacity, second_transmission_type, second_boot_space, second_fuel_capacity, second_body_type, second_video_link, second_car_color);
                            carsModelArrayList2.add(carsModel2);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler_view_compare_cars.setLayoutManager(linearLayoutManager);
                        recycler_view_compare_cars.setAdapter(compareCarAdapter);
                        recycler_view_compare_cars.addItemDecoration(new VerticalSpacesItemDecoration(40));
                        compareCarAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_compare_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                CarsModel car_model = carsModelArrayList.get(position);
                                CarsModel car_model2 = carsModelArrayList2.get(position);
                                bundle.putSerializable("first_car", car_model);
                                bundle.putSerializable("second_car", car_model2);
                                Fragment compareCarDetailsFragment = new CompareCarDetailsFragment();
                                compareCarDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, compareCarDetailsFragment)
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
}
