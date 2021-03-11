package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarBrandsAdapter extends RecyclerView.Adapter<CarBrandsAdapter.ViewHolder> {
    public static String PACKAGE_NAME;
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

        TextView brand_name = holder.brand_name;
        ImageView brand_logo = holder.brand_logo;

        brand_name.setText(carBrandsModel.getBrand_name());
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(
                carBrandsModel.getBrand_logo(), "drawable", context.getPackageName() );
        brand_logo.setImageResource(resourceId);


    }

    @Override
    public int getItemCount() {
        return carBrandsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView brand_name;
        public ImageView brand_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            brand_name = itemView.findViewById(R.id.text_view_brand_name);
            brand_logo = itemView.findViewById(R.id.image_view_brands);

        }
    }
}
