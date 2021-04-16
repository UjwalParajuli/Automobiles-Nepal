package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.automobilesnepal.fragments.BrandCarsListFragment;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static java.security.AccessController.getContext;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    ArrayList<CarsModel> carsModelArrayList;
    Context context;

    public CarsAdapter(ArrayList<CarsModel> carsModelArrayList, Context context) {
        this.carsModelArrayList = carsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_new_cars, viewGroup, false);
        CarsAdapter.ViewHolder viewHolder =new CarsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ViewHolder holder, int position) {
        CarsModel carsModel = carsModelArrayList.get(position);

        TextView car_name = holder.car_name;
        TextView car_price = holder.car_price;
        ImageView car_image = holder.car_image;
        ImageView brand_logo = holder.brand_logo;
        ImageView not_bookmarked = holder.not_bookmarked;
        ImageView bookmarked = holder.bookmarked;

        User user = SharedPrefManager.getInstance(context).getUser();
        int user_id = user.getId();
        int car_model_id = carsModel.getCar_model_id();

        String url = "http://192.168.1.65:81/android/get_bookmarks.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    not_bookmarked.setVisibility(View.VISIBLE);
                    bookmarked.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bookmarked_car_model_id = jsonResponse.getInt("car_model_id");

                            if (bookmarked_car_model_id == carsModel.getCar_model_id()){
                                not_bookmarked.setVisibility(View.GONE);
                                bookmarked.setVisibility(View.VISIBLE);
                            }

                        }


                    }

                    catch (JSONException e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);


        Glide.with(context).load(carsModel.getBrand_logo()).into(brand_logo);
        Glide.with(context).load(carsModel.getCar_image()).into(car_image);
        car_name.setText(carsModel.getCar_name());
        car_price.setText("Rs " + carsModel.getCar_price());
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(carsModel.getCar_image(), "drawable", context.getPackageName() );
//        car_image.setImageResource(resourceId);

        bookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.65:81/android/unbookmark.php";

                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            bookmarked.setVisibility(View.GONE);
                            not_bookmarked.setVisibility(View.VISIBLE);
                            notifyItemChanged(position);
                            notifyDataSetChanged();

                        }
                        else if(response.trim().equals("failed")){
                            Toast.makeText(context, "Failed to unbookmark", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", String.valueOf(user_id));
                        params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                        return params;
                    }

                };
                requestQueue.add(stringRequest);
            }
        });

        not_bookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.65:81/android/bookmark.php";

                final RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            not_bookmarked.setVisibility(View.GONE);
                            bookmarked.setVisibility(View.VISIBLE);
                            notifyItemChanged(position);
                            notifyDataSetChanged();



                        }
                        else if(response.trim().equals("failed")){
                            Toast.makeText(context, "Failed to bookmark", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", String.valueOf(user_id));
                        params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return carsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView car_name, car_price;
        public ImageView car_image, brand_logo, not_bookmarked, bookmarked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.text_view_new_car_name);
            car_image = itemView.findViewById(R.id.image_view_cars);
            car_price = itemView.findViewById(R.id.text_view_new_car_price);
            brand_logo = itemView.findViewById(R.id.image_view_rv_logo);
            not_bookmarked = itemView.findViewById(R.id.image_view_not_bookmarked);
            bookmarked = itemView.findViewById(R.id.image_view_bookmarked);

        }
    }
}
