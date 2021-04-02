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
import com.example.automobilesnepal.models.CarsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    ArrayList<CarsModel> carsModelArrayList;
    Context context;

    public CarsAdapter(ArrayList<CarsModel> carsModelArrayList, Context context) {
        this.carsModelArrayList = carsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_new_cars, viewGroup, false);
        CarsAdapter.ViewHolder viewHolder =new CarsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ViewHolder holder, int position) {
        CarsModel carsModel = carsModelArrayList.get(position);

        TextView car_name = holder.car_name;
        TextView car_price = holder.car_price;
        ImageView car_image = holder.car_image;
        ImageView brand_logo = holder.brand_logo;
        ImageView not_bookmarked = holder.not_bookmarked;
        ImageView bookmarked = holder.bookmarked;

        Glide.with(context).load(carsModel.getBrand_logo()).into(holder.brand_logo);
        Glide.with(context).load(carsModel.getCar_image()).into(holder.car_image);
        car_name.setText(carsModel.getCar_name());
        car_price.setText("Rs " + carsModel.getCar_price());
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(carsModel.getCar_image(), "drawable", context.getPackageName() );
//        car_image.setImageResource(resourceId);

    }

    @Override
    public int getItemCount() {
        return carsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView car_name, car_price;
        public ImageView car_image, brand_logo, not_bookmarked, bookmarked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_name = itemView.findViewById(R.id.text_view_new_car_name);
            car_image = itemView.findViewById(R.id.image_view_cars);
            car_price = itemView.findViewById(R.id.text_view_new_car_price);
            brand_logo = itemView.findViewById(R.id.image_view_rv_logo);
            not_bookmarked = itemView.findViewById(R.id.image_view_not_bookmarked);
            bookmarked = itemView.findViewById(R.id.image_view_bookmarked);


        }
    }
}
