package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.automobilesnepal.R;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CarValuationFragment extends Fragment {
    private LinearLayout linear_layout_car_calculation;
    private Spinner spinner_car_purchased_year;
    private EditText edit_text_car_purchased_price;
    private Button button_calculate_car_value;
    private TextView text_view_car_resale_value;
    private ArrayList<String> years;
    private ArrayAdapter<String> yearsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_valuation, container, false);

        linear_layout_car_calculation = view.findViewById(R.id.linear_layout_car_calculation);
        spinner_car_purchased_year = view.findViewById(R.id.spinner_car_purchased_year);
        edit_text_car_purchased_price = view.findViewById(R.id.edit_text_car_purchased_price);
        button_calculate_car_value = view.findViewById(R.id.button_calculate_car_value);
        text_view_car_resale_value = view.findViewById(R.id.text_view_car_resale_value);

        years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        yearsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_car_purchased_year.setAdapter(yearsAdapter);

        button_calculate_car_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCarValue();

            }
        });


        return view;
    }

    private void calculateCarValue(){
        String purchased_year, purchased_price;
        double _purchased_price, depreciate_amount, _adjusted_price, final_price = 0;
        int _purchased_year, depreciate_percent;
        boolean error = false;

        purchased_year = spinner_car_purchased_year.getSelectedItem().toString().trim();
        purchased_price = edit_text_car_purchased_price.getText().toString().trim();

        if (purchased_price.isEmpty()){
            edit_text_car_purchased_price.setError("Please fill this field");
            error = true;
        }

        if (!error){
            depreciate_percent = 5;
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            _purchased_year = Integer.parseInt(purchased_year);
            int j = thisYear - _purchased_year;
            _purchased_price = Double.parseDouble(purchased_price);
            //_adjusted_price = _purchased_price;

            for (int i = 1; i <= j; i++){
                depreciate_amount = (depreciate_percent/100f) * _purchased_price ;
                final_price = _purchased_price - depreciate_amount;
                //_adjusted_price = final_price;
                depreciate_percent = depreciate_percent + 10;
            }
            int _final_price = (int) final_price;
            if (_final_price < 0){
                text_view_car_resale_value.setText("This model is obsolete i.e. the resale value for this model couldn't be calculated.");
            }
            else{
                text_view_car_resale_value.setText("Rs. " + String.valueOf(_final_price));
            }
            linear_layout_car_calculation.setVisibility(View.VISIBLE);
        }
    }
}
