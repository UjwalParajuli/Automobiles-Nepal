package com.example.automobilesnepal.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.UsedCarsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class EditUsedCarFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Bundle bundle;
    private UsedCarsModel usedCarsModel;
    private User user;
    private EditText edit_text_edit_car_color, edit_text_edit_car_previous_owners, edit_text_edit_car_total_kilometers, edit_text_edit_car_selling_price,
            edit_text_edit_car_selling_location;
    private ImageView image_view_edit_upload_sell_car;
    private Spinner spinner_edit_car_brand_list, spinner_edit_car_model_list, spinner_edit_car_registered_year;
    private Button button_update_car;
    private ProgressBar progress_bar_edit_car;
    private ArrayList<CarBrandsModel> carBrandsModelArrayList;
    private ArrayList<CarsModel> carsModelArrayList;
    private ArrayList<String> years;
    private ArrayAdapter<CarBrandsModel> carBrandsModelArrayAdapter;
    private ArrayAdapter<CarsModel> carsModelArrayAdapter;
    private ArrayAdapter<String> yearsAdapter;
    private int car_model_id;
    private Bitmap bitmap;
    Bitmap bitmap1;

    private static final int PERMISSION_REQUEST = 1;
    private static final int IMAGE_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_used_car, container, false);

        bundle = getArguments();
        user = SharedPrefManager.getInstance(getContext()).getUser();
        usedCarsModel = (UsedCarsModel) bundle.getSerializable("used_car_details");

        edit_text_edit_car_color = view.findViewById(R.id.edit_text_edit_car_color);
        edit_text_edit_car_previous_owners = view.findViewById(R.id.edit_text_edit_car_previous_owners);
        edit_text_edit_car_total_kilometers = view.findViewById(R.id.edit_text_edit_car_total_kilometers);
        edit_text_edit_car_selling_price = view.findViewById(R.id.edit_text_edit_car_selling_price);
        edit_text_edit_car_selling_location = view.findViewById(R.id.edit_text_edit_car_selling_location);
        image_view_edit_upload_sell_car = view.findViewById(R.id.image_view_edit_upload_sell_car);
        spinner_edit_car_brand_list = view.findViewById(R.id.spinner_edit_car_brand_list);
        spinner_edit_car_model_list = view.findViewById(R.id.spinner_edit_car_model_list);
        spinner_edit_car_registered_year = view.findViewById(R.id.spinner_edit_car_registered_year);
        button_update_car = view.findViewById(R.id.button_update_car);
        progress_bar_edit_car = view.findViewById(R.id.progress_bar_edit_car);

        carBrandsModelArrayList = new ArrayList<>();
        carsModelArrayList = new ArrayList<>();
        years = new ArrayList<>();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        yearsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_car_registered_year.setAdapter(yearsAdapter);

        spinner_edit_car_model_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CarsModel carBrandsModel = (CarsModel) parent.getSelectedItem();
                car_model_id = carBrandsModel.getCar_model_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getBrands();
        setData();

        image_view_edit_upload_sell_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFileChoose();
            }
        });

        if (Build.VERSION.SDK_INT >= 23){
            if (checkPermission()){

            }
            else {
                requestPermission();
            }
        }

        button_update_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        return view;
    }

    private void setData(){
        Glide.with(getContext()).load(usedCarsModel.getUsed_car_photo()).into(image_view_edit_upload_sell_car);

        int spinnerPosition = yearsAdapter.getPosition(usedCarsModel.getRegistered_year());
        spinner_edit_car_registered_year.setSelection(spinnerPosition);
        yearsAdapter.notifyDataSetChanged();

        edit_text_edit_car_color.setText(usedCarsModel.getUsed_car_color());
        edit_text_edit_car_previous_owners.setText(String.valueOf(usedCarsModel.getNo_of_previous_owners()));
        edit_text_edit_car_selling_location.setText(usedCarsModel.getSelling_location());
        edit_text_edit_car_total_kilometers.setText(String.valueOf(usedCarsModel.getTotal_kilometers()));
        edit_text_edit_car_selling_price.setText(String.valueOf(usedCarsModel.getSelling_car_price()));

    }

    private void getBrands(){
        String url = "http://192.168.1.65:81/android/get_car_brands.php";

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
                            int brand_id = jsonResponse.getInt("car_brand_id");
                            String brand_name = jsonResponse.getString("brand_name");
                            String brand_logo = jsonResponse.getString("brand_logo");

                            CarBrandsModel carBrandsModel = new CarBrandsModel(brand_logo, brand_name, brand_id);
                            carBrandsModelArrayList.add(carBrandsModel);
                            carBrandsModelArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, carBrandsModelArrayList);
                            carBrandsModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_edit_car_brand_list.setAdapter(carBrandsModelArrayAdapter);
                        }

                        String strCompare = usedCarsModel.getBrand_name();

                        for(int i = 0; i < spinner_edit_car_brand_list.getCount(); i++) {
                            if (spinner_edit_car_brand_list.getItemAtPosition(i).toString().equals(strCompare)) {
                                spinner_edit_car_brand_list.setSelection(i);
                                carBrandsModelArrayAdapter.notifyDataSetChanged();
                                break;
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

        };
        requestQueue.add(stringRequest);
        spinner_edit_car_brand_list.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CarBrandsModel carBrandsModel = (CarBrandsModel) parent.getSelectedItem();
        if (parent.getId() == R.id.spinner_edit_car_brand_list){
            carsModelArrayList.clear();
            String url = "http://192.168.1.65:81/android/get_cars.php";

            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equals("not_found")) {
                        Toast.makeText(getContext(), "Fetch Database Error", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        carsModelArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonResponse;

                            for (int i = 0; i < jsonArray.length(); i++){
                                jsonResponse = jsonArray.getJSONObject(i);
                                int car_model_id = jsonResponse.getInt("car_model_id");
                                String model_name = jsonResponse.getString("model_name");
                                String brand_name = jsonResponse.getString("brand_name");
                                String brand_logo = jsonResponse.getString("brand_logo");
                                String mileage = jsonResponse.getString("mileage");
                                String fuel_type = jsonResponse.getString("fuel_type");
                                String displacement = jsonResponse.getString("displacement");
                                String max_power = jsonResponse.getString("max_power");
                                String max_torque = jsonResponse.getString("max_torque");
                                String seat_capacity = jsonResponse.getString("seat_capacity");
                                String transmission_type = jsonResponse.getString("transmission_type");
                                String boot_space = jsonResponse.getString("boot_space");
                                String fuel_capacity = jsonResponse.getString("fuel_capacity");
                                String body_type = jsonResponse.getString("body_type");
                                String image = jsonResponse.getString("image");
                                String price = jsonResponse.getString("price");
                                String description = jsonResponse.getString("description");
                                String video_link = "na";
                                String car_color = "na";

                                CarsModel carsModel = new CarsModel(car_model_id, brand_logo, image, model_name, brand_name, description, mileage, fuel_type, displacement, max_power, price, max_torque, seat_capacity, transmission_type, boot_space, fuel_capacity, body_type, video_link, car_color);
                                carsModelArrayList.add(carsModel);
                                carsModelArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, carsModelArrayList);
                                carsModelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_edit_car_model_list.setAdapter(carsModelArrayAdapter);
                            }

                            String strCompare = usedCarsModel.getCar_model_name();

                            for(int i = 0; i < spinner_edit_car_model_list.getCount(); i++) {
                                if (spinner_edit_car_model_list.getItemAtPosition(i).toString().equals(strCompare)) {
                                    spinner_edit_car_model_list.setSelection(i);
                                    carsModelArrayAdapter.notifyDataSetChanged();
                                    break;
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
                    params.put("brand_id", String.valueOf(carBrandsModel.getBrand_id()));
                    return params;
                }

            };
            requestQueue.add(stringRequest);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateData(){
        String selling_car_image, registered_year, car_color, previous_owners, running_km, selling_location, selling_price;
        boolean error = false;
        String url = "http://192.168.1.65:81/android/update_car.php";

        String image1 = null;
        String imageUrl = usedCarsModel.getUsed_car_photo();
        try {
            int check = bitmap.getWidth();
            image1 = getStringImage(bitmap);
        }catch (Exception ex){
            bitmap1 = getBitmapFromURL(imageUrl);
            image1 = getStringImage(bitmap1);
        }

        Toast.makeText(getContext(), usedCarsModel.getUsed_car_photo(), Toast.LENGTH_SHORT).show();

        selling_car_image = image1;

        registered_year = spinner_edit_car_registered_year.getSelectedItem().toString().trim();
        car_color = edit_text_edit_car_color.getText().toString().trim();
        previous_owners = edit_text_edit_car_previous_owners.getText().toString().trim();
        running_km = edit_text_edit_car_total_kilometers.getText().toString().trim();
        selling_location = edit_text_edit_car_selling_location.getText().toString().trim();
        selling_price = edit_text_edit_car_selling_price.getText().toString().trim();

        if (selling_car_image.isEmpty()){
            Toast.makeText(getContext(), "Please Choose Image", Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (car_color.isEmpty()){
            edit_text_edit_car_color.setError("Please insert car color");
            error = true;
        }

        if (previous_owners.isEmpty()){
            edit_text_edit_car_previous_owners.setError("Please insert number of previous owners");
            error = true;
        }

        if (running_km.isEmpty()){
            edit_text_edit_car_total_kilometers.setError("Please insert total running kilometers");
            error = true;
        }

        if (selling_location.isEmpty()){
            edit_text_edit_car_selling_location.setError("Please insert selling location");
            error = true;
        }

        if (selling_price.isEmpty()){
            edit_text_edit_car_selling_price.setError("Please insert selling price");
            error = true;
        }

        if (!car_color.matches("[a-zA-Z\\s]+")){
            edit_text_edit_car_color.setError("Invalid Color Name");
            error = true;
        }

        if (!error){
            progress_bar_edit_car.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progress_bar_edit_car.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (response.trim().equals("success")) {
                        Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.fragment_container, new CarsFragment()).addToBackStack(null).commit();
                    } else if (response.trim().equals("photo_error")) {
                        Toast.makeText(getContext(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                    } else if (response.trim().equals("db_error")) {
                        Toast.makeText(getContext(), "Database Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar_edit_car.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("car_model_id", String.valueOf(car_model_id));
                    params.put("registered_year", registered_year);
                    params.put("car_color", car_color);
                    params.put("previous_owners", previous_owners);
                    params.put("running_km", running_km);
                    params.put("selling_location", selling_location);
                    params.put("selling_car_image", selling_car_image);
                    params.put("selling_car_price", selling_price);
                    params.put("user_id", String.valueOf(user.getId()));
                    params.put("used_car_id", String.valueOf(usedCarsModel.getUsed_car_id()));
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
                image_view_edit_upload_sell_car.setImageBitmap(bitmap);
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

    public static Bitmap getBitmapFromURL(String src) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
