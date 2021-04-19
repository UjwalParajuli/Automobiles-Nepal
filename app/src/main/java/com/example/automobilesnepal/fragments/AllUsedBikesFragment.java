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
import com.example.automobilesnepal.adapters.UsedBikesAdapter;
import com.example.automobilesnepal.adapters.UsedCarsAdapter;
import com.example.automobilesnepal.models.UsedBikesModel;
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

public class AllUsedBikesFragment extends Fragment {
    private RecyclerView recycler_view_fragment_all_used_bikes;
    private ArrayList<UsedBikesModel> usedBikesModelArrayList;
    private UsedBikesAdapter usedBikesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_used_bikes, container, false);

        recycler_view_fragment_all_used_bikes = view.findViewById(R.id.recycler_view_fragment_all_used_bikes);
        usedBikesModelArrayList = new ArrayList<>();
        usedBikesAdapter = new UsedBikesAdapter(usedBikesModelArrayList, getContext());

        getAllUsedCars();
        return view;
    }

    private void getAllUsedCars(){
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

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_fragment_all_used_bikes.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_fragment_all_used_bikes.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_fragment_all_used_bikes.setAdapter(usedBikesAdapter);
                        usedBikesAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_fragment_all_used_bikes).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
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
}
