package com.example.automobilesnepal.adapters;

import android.content.Context;
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
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BikesAdapter extends RecyclerView.Adapter<BikesAdapter.ViewHolder> {
    ArrayList<BikesModel> bikesModelArrayList;
    Context context;

    public BikesAdapter(ArrayList<BikesModel> bikesModelArrayList, Context context) {
        this.bikesModelArrayList = bikesModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BikesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_new_cars, viewGroup, false);
        BikesAdapter.ViewHolder viewHolder =new BikesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BikesAdapter.ViewHolder holder, int position) {
        BikesModel bikesModel = bikesModelArrayList.get(position);

        TextView car_name = holder.car_name;
        TextView car_price = holder.car_price;
        ImageView car_image = holder.car_image;
        ImageView brand_logo = holder.brand_logo;
        ImageView not_bookmarked = holder.not_bookmarked;
        ImageView bookmarked = holder.bookmarked;

        User user = SharedPrefManager.getInstance(context).getUser();
        int user_id = user.getId();
        int car_model_id = bikesModel.getBike_model_id();

        String url = "http://192.168.1.65:81/android/get_bike_bookmarks.php";

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
                            int bookmarked_bike_model_id = jsonResponse.getInt("bike_model_id");

                            if (bookmarked_bike_model_id == bikesModel.getBike_model_id()){
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
                params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);


        Glide.with(context).load(bikesModel.getBrand_logo()).into(brand_logo);
        Glide.with(context).load(bikesModel.getBike_photo()).into(car_image);
        car_name.setText(bikesModel.getModel_name());
        car_price.setText("Rs " + bikesModel.getPrice());
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(carsModel.getCar_image(), "drawable", context.getPackageName() );
//        car_image.setImageResource(resourceId);

        bookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.65:81/android/unbookmark_bike.php";

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
                        params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                        return params;
                    }

                };
                requestQueue.add(stringRequest);
            }
        });

        not_bookmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.65:81/android/bookmark_bike.php";

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
                        params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                        return params;
                    }

                };
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return bikesModelArrayList.size();
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
