package com.example.automobilesnepal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automobilesnepal.adapters.CarsAdapter;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.utils.GridSpacingItemDecoration;
import com.example.automobilesnepal.utils.ItemClickSupport;

import java.util.ArrayList;

public class BrandCarsListActivity extends AppCompatActivity {
    private Bundle bundle;
    private CarBrandsModel carBrandsModel;
    private TextView text_view_brand_title;
    private RecyclerView recycler_view_brand_cars_list;
    private ArrayList<CarsModel> brandCarsModelArrayList;
    private CarsAdapter brandCarsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_cars_list);

        bundle = getIntent().getExtras();
        carBrandsModel = (CarBrandsModel) bundle.getSerializable("brand_cars_list");

        text_view_brand_title = (TextView) findViewById(R.id.text_view_brand_title);
        recycler_view_brand_cars_list = (RecyclerView) findViewById(R.id.recycler_view_brand_cars_list);
        brandCarsModelArrayList = new ArrayList<>();
        brandCarsAdapter = new CarsAdapter(brandCarsModelArrayList, this);

        setData();
        getCarsData();

    }

    private void setData(){
        if (bundle.getSerializable("brand_cars_list") == "brand_cars_list"){
            Toast.makeText(this, "ALl good", Toast.LENGTH_SHORT).show();
        }
        text_view_brand_title.setText(carBrandsModel.getBrand_name() + " " + "Cars");
    }

    private void getCarsData(){
        CarsModel carsModel = new CarsModel(1, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra", "1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel2 = new CarsModel(2, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel3 = new CarsModel(3, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        CarsModel carsModel4 = new CarsModel(4, "thar","mahindra_thar", "Mahindra Thar", "Mahindra", "Mahindra","1997 cc - 2184 cc","130.0 - 150.0 Bhp", "4", "10Km/l", "NPR 90 Lakhs", "Diesel", "57 Litre", "Mahindra Thar is a 4 seater SUV available in a price range of ₹ 10.00 - 14.15 Lakh. It is available in 13 variants, 1 engine option and 2 transmission options : Manual and Automatic (Torque Converter). Other key specifications of the Thar include a Ground Clearance of 219 mm. The Thar is available in 6 colours.", "20", "12l", "diesel");
        brandCarsModelArrayList.add(carsModel);
        brandCarsModelArrayList.add(carsModel2);
        brandCarsModelArrayList.add(carsModel3);
        brandCarsModelArrayList.add(carsModel4);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recycler_view_brand_cars_list.setLayoutManager(gridLayoutManager);

        // Grid item spacing
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recycler_view_brand_cars_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        recycler_view_brand_cars_list.setAdapter(brandCarsAdapter);
        brandCarsAdapter.notifyDataSetChanged();

        ItemClickSupport.addTo(recycler_view_brand_cars_list).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CarsModel cars_model = brandCarsModelArrayList.get(position);
                Intent intent = new Intent(BrandCarsListActivity.this, CarDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_details", cars_model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}