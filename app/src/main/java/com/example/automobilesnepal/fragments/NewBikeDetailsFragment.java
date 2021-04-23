package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarsModel;
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
import androidx.fragment.app.FragmentManager;

import static com.example.automobilesnepal.fragments.NewCarDetailsFragment.getVideoId;

public class NewBikeDetailsFragment extends Fragment {
    private Bundle bundle;
    private BikesModel bikesModel;
    private ImageView image_view_new_bike_details, image_view_bookmarked_new_bike_details, image_view_not_bookmarked_new_bike_details;
    private TextView text_view_brand_name_new_bike_details, text_view_price_new_bike_details, text_view_description_new_bike_details, text_view_mileage_new_bike_details,
            text_view_front_brake_new_bike_details, text_view_displacement_new_bike_details, text_view_max_power_new_bike_details, text_view_max_torque_new_bike_details,
            text_view_rear_brake_new_bike_details, text_view_engine_type_new_bike_details, text_view_no_of_cylinders_new_bike_details, text_view_fuel_capacity_new_bike_details,
            text_view_body_type_new_bike_details, text_view_available_colors_new_bike_details;
    private User user;
    private YouTubePlayerView youTubePlayerView;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_bike_details, container, false);

        bundle = getArguments();
        bikesModel = (BikesModel) bundle.getSerializable("new_bike_details");
        user = SharedPrefManager.getInstance(getContext()).getUser();

        image_view_new_bike_details = view.findViewById(R.id.image_view_new_bike_details);
        image_view_bookmarked_new_bike_details = view.findViewById(R.id.image_view_bookmarked_new_bike_details);
        image_view_not_bookmarked_new_bike_details = view.findViewById(R.id.image_view_not_bookmarked_new_bike_details);

        text_view_brand_name_new_bike_details = view.findViewById(R.id.text_view_brand_name_new_bike_details);
        text_view_price_new_bike_details = view.findViewById(R.id.text_view_price_new_bike_details);
        text_view_description_new_bike_details = view.findViewById(R.id.text_view_description_new_bike_details);
        text_view_mileage_new_bike_details = view.findViewById(R.id.text_view_mileage_new_bike_details);
        text_view_front_brake_new_bike_details = view.findViewById(R.id.text_view_front_brake_new_bike_details);
        text_view_displacement_new_bike_details = view.findViewById(R.id.text_view_displacement_new_bike_details);
        text_view_max_power_new_bike_details = view.findViewById(R.id.text_view_max_power_new_bike_details);
        text_view_max_torque_new_bike_details = view.findViewById(R.id.text_view_max_torque_new_bike_details);
        text_view_rear_brake_new_bike_details = view.findViewById(R.id.text_view_rear_brake_new_bike_details);
        text_view_engine_type_new_bike_details = view.findViewById(R.id.text_view_engine_type_new_bike_details);
        text_view_no_of_cylinders_new_bike_details = view.findViewById(R.id.text_view_no_of_cylinders_new_bike_details);
        text_view_fuel_capacity_new_bike_details = view.findViewById(R.id.text_view_fuel_capacity_new_bike_details);
        text_view_body_type_new_bike_details = view.findViewById(R.id.text_view_body_type_new_bike_details);
        text_view_available_colors_new_bike_details = view.findViewById(R.id.text_view_available_colors_new_bike_details);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view_new_bike_details);
        getLifecycle().addObserver(youTubePlayerView);

        checkBookmark();

        Glide.with(getContext()).load(bikesModel.getBike_photo()).into(image_view_new_bike_details);

        text_view_brand_name_new_bike_details.setText(bikesModel.getBrand_name() + " " + "|" + " " + bikesModel.getModel_name());
        text_view_price_new_bike_details.setText("Rs. " + bikesModel.getPrice());
        text_view_description_new_bike_details.setText(bikesModel.getDescription());
        text_view_mileage_new_bike_details.setText(bikesModel.getMileage());
        text_view_front_brake_new_bike_details.setText(bikesModel.getFront_brake());
        text_view_displacement_new_bike_details.setText(bikesModel.getDisplacement());
        text_view_max_power_new_bike_details.setText(bikesModel.getMax_power());
        text_view_max_torque_new_bike_details.setText(bikesModel.getMax_torque());
        text_view_rear_brake_new_bike_details.setText(bikesModel.getRear_brake());
        text_view_engine_type_new_bike_details.setText(bikesModel.getEngine_type());
        text_view_no_of_cylinders_new_bike_details.setText(bikesModel.getNo_of_cylinders());
        text_view_fuel_capacity_new_bike_details.setText(bikesModel.getFuel_capacity());
        text_view_body_type_new_bike_details.setText(bikesModel.getBody_type());
        text_view_available_colors_new_bike_details.setText(bikesModel.getBike_color());

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(getVideoId(bikesModel.getBike_review_video_link()), 0);
            }
        });

        image_view_bookmarked_new_bike_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBookmark();
            }
        });

        image_view_not_bookmarked_new_bike_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookmark();
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

    private void checkBookmark(){
        int user_id = user.getId();
        int bike_model_id = bikesModel.getBike_model_id();

        String url = "http://192.168.1.65:81/android/get_bike_bookmarks.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    image_view_not_bookmarked_new_bike_details.setVisibility(View.VISIBLE);
                    image_view_bookmarked_new_bike_details.setVisibility(View.GONE);

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);
                            int bookmarked_bike_model_id = jsonResponse.getInt("bike_model_id");

                            if (bookmarked_bike_model_id == bikesModel.getBike_model_id()){
                                image_view_not_bookmarked_new_bike_details.setVisibility(View.GONE);
                                image_view_bookmarked_new_bike_details.setVisibility(View.VISIBLE);
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
                params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void removeBookmark(){
        String url = "http://192.168.1.65:81/android/unbookmark_bike.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    image_view_bookmarked_new_bike_details.setVisibility(View.GONE);
                    image_view_not_bookmarked_new_bike_details.setVisibility(View.VISIBLE);

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
                params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }

    private void addBookmark(){
        String url = "http://192.168.1.65:81/android/bookmark_bike.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    image_view_not_bookmarked_new_bike_details.setVisibility(View.GONE);
                    image_view_bookmarked_new_bike_details.setVisibility(View.VISIBLE);

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
                params.put("bike_model_id", String.valueOf(bikesModel.getBike_model_id()));
                return params;
            }

        };
        requestQueue.add(stringRequest);

    }
}
