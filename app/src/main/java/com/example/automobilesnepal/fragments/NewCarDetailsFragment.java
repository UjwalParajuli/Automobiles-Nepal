package com.example.automobilesnepal.fragments;

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
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.NewsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewCarDetailsFragment extends Fragment {
    private Bundle bundle;
    private CarsModel carsModel;
    private ImageView image_view_new_car_details, image_view_bookmarked_new_car_details, image_view_not_bookmarked_new_car_details;
    private TextView text_view_brand_name_new_car_details, text_view_price_new_car_details, text_view_description_new_car_details, text_view_mileage_new_car_details,
            text_view_fuel_type_new_car_details, text_view_displacement_new_car_details, text_view_max_power_new_car_details, text_view_max_torque_new_car_details,
            text_view_seat_capacity_new_car_details, text_view_transmission_type_new_car_details, text_view_boot_space_new_car_details, text_view_fuel_capacity_new_car_details,
            text_view_body_type_new_car_details, text_view_available_colors_new_car_details;
    private User user;
    private YouTubePlayerView youTubePlayerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_car_details, container, false);

        bundle = getArguments();
        carsModel = (CarsModel) bundle.getSerializable("new_car_details");
        user = SharedPrefManager.getInstance(getContext()).getUser();

        image_view_new_car_details = view.findViewById(R.id.image_view_new_car_details);
        image_view_bookmarked_new_car_details = view.findViewById(R.id.image_view_bookmarked_new_car_details);
        image_view_not_bookmarked_new_car_details = view.findViewById(R.id.image_view_not_bookmarked_new_car_details);

        text_view_brand_name_new_car_details = view.findViewById(R.id.text_view_brand_name_new_car_details);
        text_view_price_new_car_details = view.findViewById(R.id.text_view_price_new_car_details);
        text_view_description_new_car_details = view.findViewById(R.id.text_view_description_new_car_details);
        text_view_mileage_new_car_details = view.findViewById(R.id.text_view_mileage_new_car_details);
        text_view_fuel_type_new_car_details = view.findViewById(R.id.text_view_fuel_type_new_car_details);
        text_view_displacement_new_car_details = view.findViewById(R.id.text_view_displacement_new_car_details);
        text_view_max_power_new_car_details = view.findViewById(R.id.text_view_max_power_new_car_details);
        text_view_max_torque_new_car_details = view.findViewById(R.id.text_view_max_torque_new_car_details);
        text_view_seat_capacity_new_car_details = view.findViewById(R.id.text_view_seat_capacity_new_car_details);
        text_view_transmission_type_new_car_details = view.findViewById(R.id.text_view_transmission_type_new_car_details);
        text_view_boot_space_new_car_details = view.findViewById(R.id.text_view_boot_space_new_car_details);
        text_view_fuel_capacity_new_car_details = view.findViewById(R.id.text_view_fuel_capacity_new_car_details);
        text_view_body_type_new_car_details = view.findViewById(R.id.text_view_body_type_new_car_details);
        text_view_available_colors_new_car_details = view.findViewById(R.id.text_view_available_colors_new_car_details);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        checkBookmark();

        Glide.with(getContext()).load(carsModel.getCar_image()).into(image_view_new_car_details);

        text_view_brand_name_new_car_details.setText(carsModel.getCar_brand() + " " + "|" + " " + carsModel.getCar_name());
        text_view_price_new_car_details.setText("Rs. " + carsModel.getCar_price());
        text_view_description_new_car_details.setText(carsModel.getCar_description());
        text_view_mileage_new_car_details.setText(carsModel.getCar_mileage());
        text_view_fuel_type_new_car_details.setText(carsModel.getCar_fuel_type());
        text_view_displacement_new_car_details.setText(carsModel.getCar_displacement());
        text_view_max_power_new_car_details.setText(carsModel.getCar_max_power());
        text_view_max_torque_new_car_details.setText(carsModel.getCar_max_torque());
        text_view_seat_capacity_new_car_details.setText(carsModel.getCar_seat_capacity());
        text_view_transmission_type_new_car_details.setText(carsModel.getCar_transmission_type());
        text_view_boot_space_new_car_details.setText(carsModel.getCar_boot_space());
        text_view_fuel_capacity_new_car_details.setText(carsModel.getCar_fuel_capacity());
        text_view_body_type_new_car_details.setText(carsModel.getCar_body_type());
        text_view_available_colors_new_car_details.setText(carsModel.getCar_color());

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(getVideoId(carsModel.getCar_review_video_link()), 0);
            }
        });

        image_view_bookmarked_new_car_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBookmark();
            }
        });

        image_view_not_bookmarked_new_car_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookmark();
            }
        });


        return view;
    }

    private void checkBookmark(){
        int user_id = user.getId();
        int car_model_id = carsModel.getCar_model_id();

        String url = "http://192.168.1.65:81/android/get_bookmarks.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    image_view_not_bookmarked_new_car_details.setVisibility(View.VISIBLE);
                    image_view_bookmarked_new_car_details.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bookmarked_car_model_id = jsonResponse.getInt("car_model_id");

                            if (bookmarked_car_model_id == carsModel.getCar_model_id()){
                                image_view_not_bookmarked_new_car_details.setVisibility(View.GONE);
                                image_view_bookmarked_new_car_details.setVisibility(View.VISIBLE);
                            }

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
                params.put("user_id", String.valueOf(user_id));
                params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void removeBookmark(){
        String url = "http://192.168.1.65:81/android/unbookmark.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    image_view_bookmarked_new_car_details.setVisibility(View.GONE);
                    image_view_not_bookmarked_new_car_details.setVisibility(View.VISIBLE);

                }
                else if(response.trim().equals("failed")){
                    Toast.makeText(getContext(), "Failed to unbookmark", Toast.LENGTH_SHORT).show();
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
                params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }

    private void addBookmark(){
        String url = "http://192.168.1.65:81/android/bookmark.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    image_view_not_bookmarked_new_car_details.setVisibility(View.GONE);
                    image_view_bookmarked_new_car_details.setVisibility(View.VISIBLE);

                }
                else if(response.trim().equals("failed")){
                    Toast.makeText(getContext(), "Failed to bookmark", Toast.LENGTH_SHORT).show();
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
                params.put("car_model_id", String.valueOf(carsModel.getCar_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }

    public static String getVideoId(@NonNull String videoUrl) {
        String videoId = "";
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if(matcher.find()){
            videoId = matcher.group(1);
        }
        return videoId;
    }
}
