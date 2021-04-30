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
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.UsedBikesNotificationAdapter;
import com.example.automobilesnepal.adapters.UsedCarsNotificationAdapter;
import com.example.automobilesnepal.models.BikesModel;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationFragment extends Fragment {
    private User user;
    private LinearLayout search_layout_cars_notification, search_layout_bikes_notification;
    private RecyclerView recycler_view_bikes_notifications, recycler_view_cars_notifications;
    private ArrayList<UsedBikesModel> usedBikesModelArrayList;
    private ArrayList<UsedCarsModel> usedCarsModelArrayList;
    private UsedBikesNotificationAdapter usedBikesNotificationAdapter;
    private UsedCarsNotificationAdapter usedCarsNotificationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        search_layout_cars_notification = view.findViewById(R.id.search_layout_cars_notification);
        search_layout_bikes_notification = view.findViewById(R.id.search_layout_bikes_notification);
        recycler_view_bikes_notifications = view.findViewById(R.id.recycler_view_bikes_notifications);
        recycler_view_cars_notifications = view.findViewById(R.id.recycler_view_cars_notifications);

        usedBikesModelArrayList = new ArrayList<>();
        usedCarsModelArrayList = new ArrayList<>();

        usedBikesNotificationAdapter = new UsedBikesNotificationAdapter(usedBikesModelArrayList, getContext());
        usedCarsNotificationAdapter = new UsedCarsNotificationAdapter(usedCarsModelArrayList, getContext());

        getUsedCarsNotifications();
        getUsedBikesNotifications();


        return view;
    }

    private void getUsedCarsNotifications(){
        String url = "http://192.168.1.65:81/android/get_car_notifications.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    search_layout_cars_notification.setVisibility(View.GONE);

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

                        Collections.reverse(usedCarsModelArrayList);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler_view_cars_notifications.setLayoutManager(linearLayoutManager);
                        recycler_view_cars_notifications.setAdapter(usedCarsNotificationAdapter);
                        usedCarsNotificationAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_cars_notifications).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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

    private void getUsedBikesNotifications(){
        String url = "http://192.168.1.65:81/android/get_my_verified_bikes.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    search_layout_bikes_notification.setVisibility(View.GONE);

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

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler_view_bikes_notifications.setLayoutManager(linearLayoutManager);
                        recycler_view_bikes_notifications.setAdapter(usedBikesNotificationAdapter);
                        usedBikesNotificationAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_bikes_notifications).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
