package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.adapters.UsedCarsAdapter;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.UsedCarsModel;
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

public class AllUsedCarsFragment extends Fragment {
    private RecyclerView recycler_view_all_used_cars;
    private ArrayList<UsedCarsModel> usedCarsModelArrayList;
    private UsedCarsAdapter usedCarsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_used_cars, container, false);

        recycler_view_all_used_cars = view.findViewById(R.id.recycler_view_fragment_all_used_cars);
        usedCarsModelArrayList = new ArrayList<>();
        usedCarsAdapter = new UsedCarsAdapter(usedCarsModelArrayList, getContext());

        getAllUsedCars();
        return view;
    }

    private void getAllUsedCars(){
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
                            String brand_name = jsonResponse.getString("brand_name");

                            UsedCarsModel usedCarsModel = new UsedCarsModel(used_car_id, car_model_id, posted_by, no_of_previous_owner, total_kilometers, selling_price, registered_year, used_car_color, is_verified, selling_location, used_car_photo, posted_date, model_name, brand_name);
                            usedCarsModelArrayList.add(usedCarsModel);
                        }

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_all_used_cars.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_all_used_cars.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_all_used_cars.setAdapter(usedCarsAdapter);
                        usedCarsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_all_used_cars).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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

        };
        requestQueue.add(stringRequest);

    }
}
