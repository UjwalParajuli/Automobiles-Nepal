package com.example.automobilesnepal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.automobilesnepal.models.CarsModel;

public class CarDetailsActivity extends AppCompatActivity {
    private Bundle bundle;
    private CarsModel carsModel;
    private ImageView image_view_single_car;
    private TextView text_view_single_car_brand_name, text_view_single_car_name, text_view_single_car_description, text_view_single_car_engine,
            text_view_single_car_bhp, text_view_single_car_seat_capacity, text_view_single_car_mileage, text_view_single_car_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        image_view_single_car =(ImageView) findViewById(R.id.image_view_single_car);
        text_view_single_car_bhp = (TextView) findViewById(R.id.text_view_single_car_bhp);
        text_view_single_car_brand_name = (TextView) findViewById(R.id.text_view_single_car_brand_name);
        text_view_single_car_description = (TextView) findViewById(R.id.text_view_single_car_description);
        text_view_single_car_engine = (TextView) findViewById(R.id.text_view_single_car_engine);
        text_view_single_car_mileage = (TextView) findViewById(R.id.text_view_single_car_mileage);
        text_view_single_car_price = (TextView) findViewById(R.id.text_view_single_car_price);
        text_view_single_car_seat_capacity = (TextView) findViewById(R.id.text_view_single_car_seat_capacity);
        text_view_single_car_name = (TextView) findViewById(R.id.text_view_single_car_name);

        bundle = getIntent().getExtras();
        carsModel = (CarsModel) bundle.getSerializable("car_details");

        setData();

    }

    private void setData(){
        Resources res = getResources();
        int resourceId = res.getIdentifier(carsModel.getCar_image(), "drawable", getPackageName() );
        image_view_single_car.setImageResource(resourceId);
        text_view_single_car_bhp.setText(carsModel.getCar_bhp());
        text_view_single_car_seat_capacity.setText(carsModel.getCar_seat_capacity());
        text_view_single_car_price.setText(carsModel.getCar_price());
        text_view_single_car_mileage.setText(carsModel.getCar_mileage());
        text_view_single_car_engine.setText(carsModel.getCar_engine());
        text_view_single_car_description.setText(carsModel.getCar_description());
        text_view_single_car_brand_name.setText(carsModel.getCar_brand());
        text_view_single_car_name.setText(carsModel.getCar_name());
    }
}