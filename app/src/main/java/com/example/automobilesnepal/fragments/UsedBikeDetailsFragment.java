package com.example.automobilesnepal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.automobilesnepal.models.UsedBikesModel;
import com.example.automobilesnepal.models.UsedCarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

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

public class UsedBikeDetailsFragment extends Fragment {
    private Bundle bundle;
    private UsedBikesModel usedBikesModel;
    private User user;
    private ImageView image_view_used_bike_details;
    private TextView text_view_bike_name_used_bike_details, text_view_price_used_bike_details, text_view_registered_year_used_bike_details, text_view_color_used_bike_details,
            text_view_no_of_ex_owners_used_bike_details, text_view_total_km_used_bike_details, text_view_selling_location_used_bike_details, text_view_posted_date_used_bike_details;
    private Button button_book_used_bike, button_edit_used_bike, button_delete_used_bike;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_used_bike_details, container, false);

        bundle = getArguments();
        usedBikesModel = (UsedBikesModel) bundle.getSerializable("used_bike_details");
        user = SharedPrefManager.getInstance(getContext()).getUser();

        image_view_used_bike_details = view.findViewById(R.id.image_view_used_bike_details);
        text_view_bike_name_used_bike_details = view.findViewById(R.id.text_view_bike_name_used_bike_details);
        text_view_price_used_bike_details = view.findViewById(R.id.text_view_price_used_bike_details);
        text_view_registered_year_used_bike_details = view.findViewById(R.id.text_view_registered_year_used_bike_details);
        text_view_color_used_bike_details = view.findViewById(R.id.text_view_color_used_bike_details);
        text_view_no_of_ex_owners_used_bike_details = view.findViewById(R.id.text_view_no_of_ex_owners_used_bike_details);
        text_view_total_km_used_bike_details = view.findViewById(R.id.text_view_total_km_used_bike_details);
        text_view_selling_location_used_bike_details = view.findViewById(R.id.text_view_selling_location_used_bike_details);
        text_view_posted_date_used_bike_details = view.findViewById(R.id.text_view_posted_date_used_bike_details);
        button_book_used_bike = view.findViewById(R.id.button_book_used_bike);
        button_edit_used_bike = view.findViewById(R.id.button_edit_used_bike);
        button_delete_used_bike = view.findViewById(R.id.button_delete_used_bike);

        setData();

        if (user.getId() == usedBikesModel.getPosted_by()){
            button_book_used_bike.setVisibility(View.GONE);
            button_edit_used_bike.setVisibility(View.VISIBLE);
            button_delete_used_bike.setVisibility(View.VISIBLE);
        }

        button_delete_used_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBike();
            }
        });

        button_edit_used_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBike();
            }
        });

        button_book_used_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookBike();
            }
        });

        return view;
    }

    private void setData(){
        Glide.with(getContext()).load(usedBikesModel.getUsed_bike_photo()).into(image_view_used_bike_details);

        text_view_bike_name_used_bike_details.setText(usedBikesModel.getBike_model_name());
        text_view_price_used_bike_details.setText("Rs. " + String.valueOf(usedBikesModel.getSelling_price()));
        text_view_registered_year_used_bike_details.setText(usedBikesModel.getRegistered_year());
        text_view_color_used_bike_details.setText(usedBikesModel.getUsed_bike_color());
        text_view_no_of_ex_owners_used_bike_details.setText(String.valueOf(usedBikesModel.getNo_of_previous_owner()));
        text_view_total_km_used_bike_details.setText(String.valueOf(usedBikesModel.getTotal_kilometers()));
        text_view_selling_location_used_bike_details.setText(usedBikesModel.getSelling_location());

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        long finalDate = 0;

        try {
            date = format.parse(usedBikesModel.getPosted_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalDate = date.getTime();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", finalDate);
        String day = (String) DateFormat.format("dd", finalDate);
        String monthString = (String) DateFormat.format("MMMM", finalDate);

        text_view_posted_date_used_bike_details.setText(day + " " + monthString + "," + " " + dayOfTheWeek);
    }

    private void deleteBike(){
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Delete Bike?")
                .setMessage("Are you sure want to delete this bike?")
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
                        String url = "http://192.168.1.65:81/android/delete_used_bike.php";

                        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")) {
                                    Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.fragment_container, new BikesFragment()).addToBackStack(null).commit();
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
                                params.put("used_bike_id", String.valueOf(usedBikesModel.getUsed_bike_id()));
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

    private void editBike(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("used_bike_details", usedBikesModel);
        Fragment editUsedBikeFragment = new EditUsedBikeFragment();
        editUsedBikeFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, editUsedBikeFragment)
                .addToBackStack(null).commit();

    }

    private void bookBike(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("used_bike_details", usedBikesModel);
        Fragment checkoutUsedBikeFragment = new CheckoutUsedBikeFragment();
        checkoutUsedBikeFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, checkoutUsedBikeFragment)
                .addToBackStack(null).commit();

    }
}
