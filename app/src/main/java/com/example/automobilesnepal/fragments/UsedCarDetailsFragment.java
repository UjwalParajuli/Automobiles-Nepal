package com.example.automobilesnepal.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.UsedCarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UsedCarDetailsFragment extends Fragment {
    private Bundle bundle;
    private UsedCarsModel usedCarsModel;
    private User user;
    private ImageView image_view_used_car_details;
    private TextView text_view_car_name_used_car_details, text_view_price_used_car_details, text_view_registered_year_used_car_details, text_view_color_used_car_details,
            text_view_no_of_ex_owners_used_car_details, text_view_total_km_used_car_details, text_view_selling_location_used_car_details, text_view_posted_date_used_car_details;
    private Button button_book_used_car, button_edit_used_car, button_delete_used_car;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_used_car_details, container, false);

        bundle = getArguments();
        usedCarsModel = (UsedCarsModel) bundle.getSerializable("used_car_details");
        user = SharedPrefManager.getInstance(getContext()).getUser();

        image_view_used_car_details = view.findViewById(R.id.image_view_used_car_details);
        text_view_car_name_used_car_details = view.findViewById(R.id.text_view_car_name_used_car_details);
        text_view_price_used_car_details = view.findViewById(R.id.text_view_price_used_car_details);
        text_view_registered_year_used_car_details = view.findViewById(R.id.text_view_registered_year_used_car_details);
        text_view_color_used_car_details = view.findViewById(R.id.text_view_color_used_car_details);
        text_view_no_of_ex_owners_used_car_details = view.findViewById(R.id.text_view_no_of_ex_owners_used_car_details);
        text_view_total_km_used_car_details = view.findViewById(R.id.text_view_total_km_used_car_details);
        text_view_selling_location_used_car_details = view.findViewById(R.id.text_view_selling_location_used_car_details);
        text_view_posted_date_used_car_details = view.findViewById(R.id.text_view_posted_date_used_car_details);
        button_book_used_car = view.findViewById(R.id.button_book_used_car);
        button_edit_used_car = view.findViewById(R.id.button_edit_used_car);
        button_delete_used_car = view.findViewById(R.id.button_delete_used_car);

        setData();

        if (user.getId() == usedCarsModel.getPosted_by()){
            button_book_used_car.setVisibility(View.GONE);
            button_edit_used_car.setVisibility(View.VISIBLE);
            button_delete_used_car.setVisibility(View.VISIBLE);
        }

        button_delete_used_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar();
            }
        });

        button_edit_used_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCar();
            }
        });

        button_book_used_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookCar();
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

    private void setData(){
        Glide.with(getContext()).load(usedCarsModel.getUsed_car_photo()).into(image_view_used_car_details);

        text_view_car_name_used_car_details.setText(usedCarsModel.getCar_model_name());
        text_view_price_used_car_details.setText("Rs. " + String.valueOf(usedCarsModel.getSelling_car_price()));
        text_view_registered_year_used_car_details.setText(usedCarsModel.getRegistered_year());
        text_view_color_used_car_details.setText(usedCarsModel.getUsed_car_color());
        text_view_no_of_ex_owners_used_car_details.setText(String.valueOf(usedCarsModel.getNo_of_previous_owners()));
        text_view_total_km_used_car_details.setText(String.valueOf(usedCarsModel.getTotal_kilometers()));
        text_view_selling_location_used_car_details.setText(usedCarsModel.getSelling_location());

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        long finalDate = 0;

        try {
            date = format.parse(usedCarsModel.getPosted_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalDate = date.getTime();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", finalDate);
        String day = (String) DateFormat.format("dd", finalDate);
        String monthString = (String) DateFormat.format("MMMM", finalDate);

        text_view_posted_date_used_car_details.setText(day + " " + monthString + "," + " " + dayOfTheWeek);
    }

    private void deleteCar(){
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Delete Car?")
                .setMessage("Are you sure want to delete this car?")
                .setPositiveButton(android.R.string.yes, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.no, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String url = "http://192.168.1.65:81/android/delete_used_car.php";

                        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")) {
                                    Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.fragment_container, new CarsFragment()).addToBackStack(null).commit();
                                }
                                else{
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Cannot be Deleted", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_id", String.valueOf(user.getId()));
                                params.put("used_car_id", String.valueOf(usedCarsModel.getUsed_car_id()));
                                return params;
                            }

                        };
                        requestQueue.add(stringRequest);


                    }
                });
            }
        });
        dialog.show();

    }

    private void editCar(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("used_car_details", usedCarsModel);
        Fragment editUsedCarFragment = new EditUsedCarFragment();
        editUsedCarFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, editUsedCarFragment)
                .addToBackStack(null).commit();

    }

    private void bookCar(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("used_car_details", usedCarsModel);
        Fragment checkoutUsedCarFragment = new CheckoutUsedCarFragment();
        checkoutUsedCarFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, checkoutUsedCarFragment)
                .addToBackStack(null).commit();

    }
}
