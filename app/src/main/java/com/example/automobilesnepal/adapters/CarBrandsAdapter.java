package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarBrandsAdapter extends RecyclerView.Adapter<CarBrandsAdapter.ViewHolder> {
    ArrayList<CarBrandsModel> carBrandsModelArrayList;
    Context context;

    public CarBrandsAdapter(ArrayList<CarBrandsModel> carBrandsModelArrayList, Context context) {
        this.carBrandsModelArrayList = carBrandsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarBrandsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_brands, viewGroup, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarBrandsAdapter.ViewHolder holder, int position) {
        CarBrandsModel carBrandsModel = carBrandsModelArrayList.get(position);

        ImageView brand_logo = holder.brand_logo;

        Glide.with(context).load(carBrandsModel.getBrand_logo()).into(holder.brand_logo);

    }

    @Override
    public int getItemCount() {
        return carBrandsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView brand_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            brand_logo = itemView.findViewById(R.id.image_view_brands);

        }
    }
}
