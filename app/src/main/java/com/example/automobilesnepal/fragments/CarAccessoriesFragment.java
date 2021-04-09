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
import com.example.automobilesnepal.adapters.AccessoryAdapter;
import com.example.automobilesnepal.models.AccessoriesModel;
import com.example.automobilesnepal.models.CarBrandsModel;
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

public class CarAccessoriesFragment extends Fragment {
    private RecyclerView recycler_view_all_car_accessories;
    private ArrayList<AccessoriesModel> accessoriesModelArrayList;
    private AccessoryAdapter accessoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_accessories, container, false);

        recycler_view_all_car_accessories = view.findViewById(R.id.recycler_view_all_car_accessories);
        accessoriesModelArrayList = new ArrayList<>();
        accessoryAdapter = new AccessoryAdapter(accessoriesModelArrayList, getContext());

        getCarAccessories();

        return view;
    }

    private void getCarAccessories(){
        String url = "https://automobiles-nepal.000webhostapp.com/android/get_car_accessories.php";

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
                            int accessory_id = jsonResponse.getInt("accessory_id");
                            String title = jsonResponse.getString("title");
                            String type = jsonResponse.getString("type");
                            String description = jsonResponse.getString("description");
                            String image = jsonResponse.getString("image");
                            String vehicle = jsonResponse.getString("vehicle");
                            double price = jsonResponse.getDouble("price");
                            int available_quantity = jsonResponse.getInt("available_quantity");

                            AccessoriesModel accessoriesModel = new AccessoriesModel(accessory_id, available_quantity, price, title, type, description, vehicle, image);
                            accessoriesModelArrayList.add(accessoriesModel);
                        }

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                        recycler_view_all_car_accessories.setLayoutManager(gridLayoutManager);

                        // Grid item spacing
                        int spanCount = 2; // 3 columns
                        int spacing = 50; // 50px
                        boolean includeEdge = false;
                        recycler_view_all_car_accessories.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
                        recycler_view_all_car_accessories.setAdapter(accessoryAdapter);
                        accessoryAdapter.notifyDataSetChanged();

//                        ItemClickSupport.addTo(recycler_view_all_car_accessories).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                            @Override
//                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                                CarBrandsModel car_brands_model = carBrandsModelArrayList.get(position);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("brand_cars_list", car_brands_model);
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
}
