package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.UsedBikesModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsedBikesAdapter extends RecyclerView.Adapter<UsedBikesAdapter.ViewHolder> {
    ArrayList<UsedBikesModel> usedBikesModelArrayList;
    Context context;

    public UsedBikesAdapter(ArrayList<UsedBikesModel> usedBikesModelArrayList, Context context) {
        this.usedBikesModelArrayList = usedBikesModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsedBikesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_used_cars, parent, false);
        UsedBikesAdapter.ViewHolder viewHolder =new UsedBikesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsedBikesAdapter.ViewHolder holder, int position) {
        UsedBikesModel usedBikesModel = usedBikesModelArrayList.get(position);

        TextView car_name = holder.car_name;
        TextView car_price = holder.car_price;
        ImageView car_image = holder.car_image;

        Glide.with(context).load(usedBikesModel.getUsed_bike_photo()).into(car_image);
        car_name.setText(usedBikesModel.getBike_model_name());
        car_price.setText("Rs " + usedBikesModel.getSelling_price());

    }

    @Override
    public int getItemCount() {
        return usedBikesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView car_name, car_price;
        public ImageView car_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.text_view_used_car_name);
            car_image = itemView.findViewById(R.id.image_view_used_car);
            car_price = itemView.findViewById(R.id.text_view_used_car_price);

        }
    }
}
