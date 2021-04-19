package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.example.automobilesnepal.adapters.CompareBikeAdapter;
import com.example.automobilesnepal.adapters.CompareCarAdapter;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.ItemClickSupport;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompareBikesFragment extends Fragment {
    private LinearLayout linear_layout_compare_bikes, linear_layout_bike_1_details, linear_layout_bike_2_details;
    private Button button_select_bike_1, button_select_bike_2;
    private ImageView image_view_bike_1_brand_logo, image_view_bike_2_brand_logo, image_view_bike_1_photo, image_view_bike_2_photo;
    private TextView text_view_bike_1_name, text_view_bike_1_price, text_view_bike_2_name, text_view_bike_2_price;

    private BikesModel bikesModel;
    private BikesModel bikesModel2;

    private RecyclerView recycler_view_compare_bikes;
    private CompareBikeAdapter comparebikeAdapter;
    private ArrayList<BikesModel> bikesModelArrayList;
    private ArrayList<BikesModel> bikesModelArrayList2;

    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare_bikes, container, false);

        linear_layout_compare_bikes = view.findViewById(R.id.linear_layout_compare_bikes);
        linear_layout_bike_1_details = view.findViewById(R.id.linear_layout_bike_1_details);
        linear_layout_bike_2_details = view.findViewById(R.id.linear_layout_bike_2_details);

        button_select_bike_1 = view.findViewById(R.id.button_select_bike_1);
        button_select_bike_2 = view.findViewById(R.id.button_select_bike_2);

        image_view_bike_1_brand_logo = view.findViewById(R.id.image_view_compare_bike_brand_logo);
        image_view_bike_2_brand_logo = view.findViewById(R.id.image_view_compare_bike_2_brand_logo);
        image_view_bike_1_photo = view.findViewById(R.id.image_view_compare_bike_1_photo);
        image_view_bike_2_photo = view.findViewById(R.id.image_view_compare_bike_2_photo);

        text_view_bike_1_name = view.findViewById(R.id.text_view_compare_bike_1_name);
        text_view_bike_2_name = view.findViewById(R.id.text_view_compare_bike_2_name);
        text_view_bike_1_price = view.findViewById(R.id.text_view_bike_1_price);
        text_view_bike_2_price = view.findViewById(R.id.text_view_bike_2_price);

        recycler_view_compare_bikes = view.findViewById(R.id.recycler_view_bike_comparisons);
        bikesModelArrayList = new ArrayList<>();
        bikesModelArrayList2 = new ArrayList<>();
        comparebikeAdapter = new CompareBikeAdapter(bikesModelArrayList, bikesModelArrayList2, getContext());

        bundle = new Bundle();

        getComparisonBikes();

        button_select_bike_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFirstBike(v);
            }
        });

        button_select_bike_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSecondBike(v);
            }
        });

        return view;
    }

    private void selectFirstBike(View v){
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
                        PopupMenu menu = new PopupMenu(getContext(), v);

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bike_model_id = jsonResponse.getInt("bike_model_id");
                            String model_name = jsonResponse.getString("model_name");

                            menu.getMenu().add(Menu.NONE, bike_model_id, i +1, model_name);
                        }

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int i = item.getItemId();

                                String url = "http://192.168.1.65:81/android/get_compare_bikes.php";

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

                                                jsonResponse = jsonArray.getJSONObject(0);
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

                                                bikesModel = new BikesModel(bike_model_id, bike_brands_id, model_name, mileage, displacement, engine_type, no_of_cylinders, max_power, max_torque, front_brake, rear_brake, fuel_capacity, body_type, price, description, bike_photo, brand_logo, brand_name, video_link, bike_color);

                                                ViewGroup.LayoutParams params = linear_layout_compare_bikes.getLayoutParams();
                                                // Changes the height and width to the specified *pixels*
                                                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 194, getResources().getDisplayMetrics());
                                                params.height = height;
                                                linear_layout_compare_bikes.setLayoutParams(params);
                                                button_select_bike_1.setVisibility(View.GONE);
                                                linear_layout_bike_1_details.setVisibility(View.VISIBLE);

                                                Glide.with(getContext()).load(bikesModel.getBrand_logo()).into(image_view_bike_1_brand_logo);
                                                Glide.with(getContext()).load(bikesModel.getBike_photo()).into(image_view_bike_1_photo);
                                                text_view_bike_1_name.setText(model_name);
                                                text_view_bike_1_price.setText("NPR " + price);

                                                if (linear_layout_bike_2_details.getVisibility() == View.VISIBLE){
                                                    bundle.putSerializable("second_bike", bikesModel2);
                                                    bundle.putSerializable("first_bike", bikesModel);
                                                    Fragment compareBikeDetailsFragment = new CompareBikeDetailsFragment();
                                                    compareBikeDetailsFragment.setArguments(bundle);
                                                    getFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.fragment_container, compareBikeDetailsFragment)
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
                                        params.put("bike_model_id", String.valueOf(i));
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

    private void selectSecondBike(View v){
        String url = "http://192.168.1.65:81/android/get_new_bikes.php";

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
                            int bike_model_id = jsonResponse.getInt("bike_model_id");
                            String model_name = jsonResponse.getString("model_name");

                            menu.getMenu().add(Menu.NONE, bike_model_id, i +1, model_name);
                        }

                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int i = item.getItemId();

                                String url = "http://192.168.1.65:81/android/get_compare_bikes.php";

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

                                                jsonResponse = jsonArray.getJSONObject(0);
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

                                                bikesModel2 = new BikesModel(bike_model_id, bike_brands_id, model_name, mileage, displacement, engine_type, no_of_cylinders, max_power, max_torque, front_brake, rear_brake, fuel_capacity, body_type, price, description, bike_photo, brand_logo, brand_name, video_link, bike_color);

                                                ViewGroup.LayoutParams params = linear_layout_compare_bikes.getLayoutParams();
                                                // Changes the height and width to the specified *pixels*
                                                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 194, getResources().getDisplayMetrics());
                                                params.height = height;
                                                linear_layout_compare_bikes.setLayoutParams(params);
                                                button_select_bike_2.setVisibility(View.GONE);
                                                linear_layout_bike_2_details.setVisibility(View.VISIBLE);

                                                Glide.with(getContext()).load(bikesModel2.getBrand_logo()).into(image_view_bike_2_brand_logo);
                                                Glide.with(getContext()).load(bikesModel2.getBike_photo()).into(image_view_bike_2_photo);
                                                text_view_bike_2_name.setText(model_name);
                                                text_view_bike_2_price.setText("NPR " + price);

                                                if (linear_layout_bike_1_details.getVisibility() == View.VISIBLE){
                                                    bundle.putSerializable("first_bike", bikesModel);
                                                    bundle.putSerializable("second_bike", bikesModel2);
                                                    Fragment compareBikeDetailsFragment = new CompareBikeDetailsFragment();
                                                    compareBikeDetailsFragment.setArguments(bundle);
                                                    getFragmentManager()
                                                            .beginTransaction()
                                                            .replace(R.id.fragment_container, compareBikeDetailsFragment)
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
                                        params.put("bike_model_id", String.valueOf(i));
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

    private void getComparisonBikes(){
        String url = "http://192.168.1.65:81/android/popular_comparisons_bike.php";

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
                            int first_bike_model_id = jsonResponse.getInt("first_bike_model_id");
                            int first_bike_brand_id = jsonResponse.getInt("first_bike_brand_id");
                            String first_bike_model_name = jsonResponse.getString("first_bike_model_name");
                            String first_bike_brand_name = jsonResponse.getString("first_bike_brand_name");
                            String first_bike_brand_logo = jsonResponse.getString("first_bike_brand_logo");
                            String first_bike_mileage = jsonResponse.getString("first_bike_mileage");
                            String first_bike_displacement = jsonResponse.getString("first_bike_displacement");
                            String first_bike_front_brake = jsonResponse.getString("first_bike_front_brake");
                            String first_bike_rear_brake = jsonResponse.getString("first_bike_rear_brake");
                            String first_bike_max_power = jsonResponse.getString("first_bike_max_power");
                            String first_bike_max_torque = jsonResponse.getString("first_bike_max_torque");
                            String first_bike_engine_type = jsonResponse.getString("first_bike_engine_type");
                            String first_bike_no_of_cylinders = jsonResponse.getString("first_bike_no_of_cylinders");
                            String first_bike_fuel_capacity = jsonResponse.getString("first_bike_fuel_capacity");
                            String first_bike_body_type = jsonResponse.getString("first_bike_body_type");
                            String first_bike_bike_photo = jsonResponse.getString("first_bike_photo");
                            String first_bike_price = jsonResponse.getString("first_bike_price");
                            String first_bike_description = jsonResponse.getString("first_bike_description");
                            String first_bike_video_link = jsonResponse.getString("first_bike_video_link");
                            String first_bike_bike_color = jsonResponse.getString("first_bike_color");

                            int second_bike_model_id = jsonResponse.getInt("second_bike_model_id");
                            int second_bike_brand_id = jsonResponse.getInt("second_bike_brand_id");
                            String second_bike_model_name = jsonResponse.getString("second_bike_model_name");
                            String second_bike_brand_name = jsonResponse.getString("second_bike_brand_name");
                            String second_bike_brand_logo = jsonResponse.getString("second_bike_brand_logo");
                            String second_bike_mileage = jsonResponse.getString("second_bike_mileage");
                            String second_bike_displacement = jsonResponse.getString("second_bike_displacement");
                            String second_bike_front_brake = jsonResponse.getString("second_bike_front_brake");
                            String second_bike_rear_brake = jsonResponse.getString("second_bike_rear_brake");
                            String second_bike_max_power = jsonResponse.getString("second_bike_max_power");
                            String second_bike_max_torque = jsonResponse.getString("second_bike_max_torque");
                            String second_bike_engine_type = jsonResponse.getString("second_bike_engine_type");
                            String second_bike_no_of_cylinders = jsonResponse.getString("second_bike_no_of_cylinders");
                            String second_bike_fuel_capacity = jsonResponse.getString("second_bike_fuel_capacity");
                            String second_bike_body_type = jsonResponse.getString("second_bike_body_type");
                            String second_bike_bike_photo = jsonResponse.getString("second_bike_photo");
                            String second_bike_price = jsonResponse.getString("second_bike_price");
                            String second_bike_description = jsonResponse.getString("second_bike_description");
                            String second_bike_video_link = jsonResponse.getString("second_bike_video_link");
                            String second_bike_bike_color = jsonResponse.getString("second_bike_color");

                            BikesModel bikesModel = new BikesModel(first_bike_model_id, first_bike_brand_id, first_bike_model_name, first_bike_mileage, first_bike_displacement, first_bike_engine_type, first_bike_no_of_cylinders, first_bike_max_power, first_bike_max_torque, first_bike_front_brake, first_bike_rear_brake, first_bike_fuel_capacity, first_bike_body_type, first_bike_price, first_bike_description, first_bike_bike_photo, first_bike_brand_logo, first_bike_brand_name, first_bike_video_link, first_bike_bike_color);
                            bikesModelArrayList.add(bikesModel);

                            BikesModel bikesModel2 = new BikesModel(second_bike_model_id, second_bike_brand_id, second_bike_model_name, second_bike_mileage, second_bike_displacement, second_bike_engine_type, second_bike_no_of_cylinders, second_bike_max_power, second_bike_max_torque, second_bike_front_brake, second_bike_rear_brake, second_bike_fuel_capacity, second_bike_body_type, second_bike_price, second_bike_description, second_bike_bike_photo, second_bike_brand_logo, second_bike_brand_name, second_bike_video_link, second_bike_bike_color);
                            bikesModelArrayList2.add(bikesModel2);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler_view_compare_bikes.setLayoutManager(linearLayoutManager);
                        recycler_view_compare_bikes.setAdapter(comparebikeAdapter);
                        recycler_view_compare_bikes.addItemDecoration(new VerticalSpacesItemDecoration(40));
                        comparebikeAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_compare_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                BikesModel bike_model = bikesModelArrayList.get(position);
                                BikesModel bike_model2 = bikesModelArrayList2.get(position);
                                bundle.putSerializable("first_bike", bike_model);
                                bundle.putSerializable("second_bike", bike_model2);
                                Fragment compareBikeDetailsFragment = new CompareBikeDetailsFragment();
                                compareBikeDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, compareBikeDetailsFragment)
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
