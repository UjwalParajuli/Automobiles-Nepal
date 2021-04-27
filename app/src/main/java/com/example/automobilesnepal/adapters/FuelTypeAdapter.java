package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FuelTypeAdapter extends RecyclerView.Adapter<FuelTypeAdapter.ViewHolder> {
    ArrayList<CarsModel> carsModelArrayList;
    Context context;

    public FuelTypeAdapter(ArrayList<CarsModel> carsModelArrayList, Context context) {
        this.carsModelArrayList = carsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FuelTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_body_type, viewGroup, false);
        FuelTypeAdapter.ViewHolder viewHolder =new FuelTypeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FuelTypeAdapter.ViewHolder holder, int position) {
        CarsModel carsModel = carsModelArrayList.get(position);

        ImageView image_view_body_type = holder.image_view_body_type;
        TextView text_view_body_type_name = holder.text_view_body_type_name;

        Glide.with(context).load(carsModel.getCar_image()).into(image_view_body_type);
        text_view_body_type_name.setText(carsModel.getCar_fuel_type());

    }

    @Override
    public int getItemCount() {
        return carsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_view_body_type;
        public TextView text_view_body_type_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_view_body_type = itemView.findViewById(R.id.image_view_body_type);
            text_view_body_type_name = itemView.findViewById(R.id.text_view_body_type_name);

        }
    }
}
