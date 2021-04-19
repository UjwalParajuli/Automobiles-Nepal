package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.BikesModel;
import com.example.automobilesnepal.models.CarsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompareBikeAdapter extends RecyclerView.Adapter<CompareBikeAdapter.ViewHolder> {

    ArrayList<BikesModel> bikesModelArrayList;
    ArrayList<BikesModel> bikesModelArrayList2;
    Context context;

    public CompareBikeAdapter(ArrayList<BikesModel> bikesModelArrayList, ArrayList<BikesModel> bikesModelArrayList2, Context context) {
        this.bikesModelArrayList = bikesModelArrayList;
        this.bikesModelArrayList2 = bikesModelArrayList2;
        this.context = context;
    }

    @NonNull
    @Override
    public CompareBikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_comparison, viewGroup, false);
        CompareBikeAdapter.ViewHolder viewHolder =new CompareBikeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompareBikeAdapter.ViewHolder holder, int position) {
        BikesModel BikesModel = bikesModelArrayList.get(position);
        BikesModel BikesModel2 = bikesModelArrayList2.get(position);

        TextView first_car_name = holder.first_car_name;
        TextView first_car_price = holder.first_car_price;
        ImageView first_car_image = holder.first_car_image;
        ImageView first_brand_logo = holder.first_brand_logo;
        TextView second_car_name = holder.second_car_name;
        TextView second_car_price = holder.second_car_price;
        ImageView second_car_image = holder.second_car_image;
        ImageView second_brand_logo = holder.second_brand_logo;


        Glide.with(context).load(BikesModel.getBrand_logo()).into(first_brand_logo);
        Glide.with(context).load(BikesModel.getBike_photo()).into(first_car_image);
        first_car_name.setText(BikesModel.getModel_name());
        first_car_price.setText("Rs " + BikesModel.getPrice());

        Glide.with(context).load(BikesModel2.getBrand_logo()).into(second_brand_logo);
        Glide.with(context).load(BikesModel2.getBike_photo()).into(second_car_image);
        second_car_name.setText(BikesModel2.getModel_name());
        second_car_price.setText("Rs " + BikesModel2.getPrice());
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(BikesModel.getCar_image(), "drawable", context.getPackageName() );
//        car_image.setImageResource(resourceId);

    }

    @Override
    public int getItemCount() {
        return bikesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView first_car_name, first_car_price, second_car_name, second_car_price;
        public ImageView first_car_image, first_brand_logo, second_car_image, second_brand_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            first_car_name = itemView.findViewById(R.id.text_view_comparison_car_1_name);
            first_car_price = itemView.findViewById(R.id.text_view_comparison_car_1_price);
            first_car_image = itemView.findViewById(R.id.image_view_comparison_car_1_photo);
            first_brand_logo = itemView.findViewById(R.id.image_view_comparison_car_1_logo);
            second_car_name = itemView.findViewById(R.id.text_view_comparison_car_2_name);
            second_car_price = itemView.findViewById(R.id.text_view_comparison_car_2_price);
            second_car_image = itemView.findViewById(R.id.image_view_comparison_car_2_photo);
            second_brand_logo = itemView.findViewById(R.id.image_view_comparison_car_2_logo);

        }
    }
}
