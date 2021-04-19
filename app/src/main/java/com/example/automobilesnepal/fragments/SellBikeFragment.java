package com.example.automobilesnepal.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.BikeBrandsModel;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static android.app.Activity.RESULT_OK;

public class SellBikeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner_brand, spinner_model, spinner_year;
    private ArrayList<BikeBrandsModel> bikeBrandsModelArrayList;
    private ArrayList<BikesModel> bikesModelArrayList;
    private ArrayList<String> years;
    private ArrayAdapter<BikeBrandsModel> bikeBrandsModelArrayAdapter;
    private ArrayAdapter<BikesModel> bikesModelArrayAdapter;
    private ArrayAdapter<String> yearsAdapter;
    private EditText edit_text_bike_color, edit_text_running_km, edit_text_previous_owners, edit_text_selling_location, edit_text_bike_selling_price;
    private int bike_model_id;
    private Button button_upload_bike;
    private Bitmap bitmap;
    private ImageView image_view_selling_bike_image;
    private User user;
    private ProgressBar progress_bar_sell_bike;

    private static final int PERMISSION_REQUEST = 1;
    private static final int IMAGE_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_bike, container, false);

        user = SharedPrefManager.getInstance(getContext()).getUser();
        button_upload_bike = view.findViewById(R.id.button_upload_bike);
        progress_bar_sell_bike = view.findViewById(R.id.progress_bar_sell_bike);

        edit_text_bike_color = view.findViewById(R.id.edit_text_bike_color);
        edit_text_running_km = view.findViewById(R.id.edit_text_bike_total_kilometers);
        edit_text_previous_owners = view.findViewById(R.id.edit_text_bike_previous_owners);
        edit_text_selling_location = view.findViewById(R.id.edit_text_bike_selling_location);
        edit_text_bike_selling_price = view.findViewById(R.id.edit_text_bike_selling_price);

        image_view_selling_bike_image = view.findViewById(R.id.image_view_upload_sell_bike);

        spinner_brand = view.findViewById(R.id.spinner_bike_brand_list);
        spinner_model = view.findViewById(R.id.spinner_bike_model_list);
        spinner_year = view.findViewById(R.id.spinner_bike_registered_year);

        bikeBrandsModelArrayList = new ArrayList<>();
        bikesModelArrayList = new ArrayList<>();
        getBrands();

        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        yearsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(yearsAdapter);

        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BikesModel bikeBrandsModel = (BikesModel) parent.getSelectedItem();
                bike_model_id = bikeBrandsModel.getBike_model_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_upload_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBike();
            }
        });

        image_view_selling_bike_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFileChoose();
            }
        });

        if (checkPermission()){

        }
        else {
            requestPermission();
        }


        return view;
    }

    private void getBrands(){
        String url = "http://192.168.1.65:81/android/get_bike_brands.php";

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
                            int brand_id = jsonResponse.getInt("bike_brand_id");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");

                            BikeBrandsModel bikeBrandsModel = new BikeBrandsModel(brand_id, brand_name, brand_logo);
                            bikeBrandsModelArrayList.add(bikeBrandsModel);
                            bikeBrandsModelArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bikeBrandsModelArrayList);
                            bikeBrandsModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_brand.setAdapter(bikeBrandsModelArrayAdapter);
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

        };
        requestQueue.add(stringRequest);
        spinner_brand.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        BikeBrandsModel bikeBrandsModel = (BikeBrandsModel) parent.getSelectedItem();
        if (parent.getId() == R.id.spinner_bike_brand_list){
            bikesModelArrayList.clear();
            String url = "http://192.168.1.65:81/android/get_bikes.php";

            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("not_found")) {
                        Toast.makeText(getContext(), "Fetch Database Error", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        bikesModelArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse;

                            for (int i = 0; i < jsonArray.length(); i++){
                                jsonResponse = jsonArray.getJSONObject(i);
                                int bike_model_id = jsonResponse.getInt("bike_model_id");
                                int bike_brands_id = jsonResponse.getInt("bike_brands_id");
                                String model_name = jsonResponse.getString("model_name");
                                String brand_name = jsonResponse.getString("brand_name");
                                String brand_logo = jsonResponse.getString("brand_logo");
                                String mileage = jsonResponse.getString("mileage");
                                String displacement = jsonResponse.getString("displacement");
                                String front_brake = jsonResponse.getString("front_brake");
                                String rear_brake = jsonResponse.getString("rear_brake");
                                String max_power = jsonResponse.getString("max_power");
                                String max_torque = jsonResponse.getString("max_torque");
                                String engine_type = jsonResponse.getString("engine_type");
                                String no_of_cylinders = jsonResponse.getString("no_of_cylinders");
                                String fuel_capacity = jsonResponse.getString("fuel_capacity");
                                String body_type = jsonResponse.getString("body_type");
                                String bike_photo = jsonResponse.getString("bike_photo");
                                String price = jsonResponse.getString("price");
                                String description = jsonResponse.getString("description");
                                String video_link = jsonResponse.getString("bike_review_video_link");
                                String bike_color = jsonResponse.getString("new_bike_color");

                                BikesModel bikesModel = new BikesModel(bike_model_id, bike_brands_id, model_name, mileage, displacement, engine_type, no_of_cylinders, max_power, max_torque, front_brake, rear_brake, fuel_capacity, body_type, price, description, bike_photo, brand_logo, brand_name, video_link, bike_color);
                                bikesModelArrayList.add(bikesModel);
                                bikesModelArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, bikesModelArrayList);
                                bikesModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_model.setAdapter(bikesModelArrayAdapter);
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
                    params.put("brand_id", String.valueOf(bikeBrandsModel.getBike_brand_id()));
                    return params;
                }

            };
            requestQueue.add(stringRequest);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void uploadBike(){
        String selling_bike_image, registered_year, bike_color, previous_owners, running_km, selling_location, selling_price;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/upload_bike.php";

        selling_bike_image = getStringImage(bitmap);

        registered_year = spinner_year.getSelectedItem().toString().trim();
        bike_color = edit_text_bike_color.getText().toString().trim();
        previous_owners = edit_text_previous_owners.getText().toString().trim();
        running_km = edit_text_running_km.getText().toString().trim();
        selling_location = edit_text_selling_location.getText().toString().trim();
        selling_price = edit_text_bike_selling_price.getText().toString().trim();

        if (selling_bike_image.isEmpty()){
            Toast.makeText(getContext(), "Please Choose Image", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (bike_color.isEmpty()){
            edit_text_bike_color.setError("Please insert bike color");
            error = true;
        }

        if (previous_owners.isEmpty()){
            edit_text_previous_owners.setError("Please insert number of previous owners");
            error = true;
        }

        if (running_km.isEmpty()){
            edit_text_running_km.setError("Please insert total running kilometers");
            error = true;
        }

        if (selling_location.isEmpty()){
            edit_text_selling_location.setError("Please insert selling location");
            error = true;
        }

        if (selling_price.isEmpty()){
            edit_text_bike_selling_price.setError("Please insert selling price");
            error = true;
        }

        if (!bike_color.matches("[a-zA-Z\\s]+")){
            edit_text_bike_color.setError("Invalid Color Name");
            error = true;
        }

        if (!error){
            progress_bar_sell_bike.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress_bar_sell_bike.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(getContext(), "Successfully Posted", Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, new BikesFragment()).addToBackStack(null).commit();
                    } else if (response.trim().equals("photo_error")) {
                        Toast.makeText(getContext(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("db_error")) {
                        Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar_sell_bike.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("bike_model_id", String.valueOf(bike_model_id));
                    params.put("registered_year", registered_year);
                    params.put("bike_color", bike_color);
                    params.put("previous_owners", previous_owners);
                    params.put("running_km", running_km);
                    params.put("selling_location", selling_location);
                    params.put("selling_bike_image", selling_bike_image);
                    params.put("selling_bike_price", selling_price);
                    params.put("user_id", String.valueOf(user.getId()));
                    return params;
                }
            };
            requestQueue.add(stringRequest);

        }

    }

    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(getContext(), "Please allow this permission in App Setting", Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }
    }

    public boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_DENIED){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void displayFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imgPath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgPath);
                image_view_selling_bike_image.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        }catch (Exception ex){
            Toast.makeText(getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
        }
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);

    }
}
