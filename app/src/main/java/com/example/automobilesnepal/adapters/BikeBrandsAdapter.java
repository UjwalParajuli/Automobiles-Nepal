package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.BikeBrandsModel;
import com.example.automobilesnepal.models.CarBrandsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BikeBrandsAdapter extends RecyclerView.Adapter<BikeBrandsAdapter.ViewHolder> {
    ArrayList<BikeBrandsModel> bikeBrandsModelArrayList;
    Context context;

    public BikeBrandsAdapter(ArrayList<BikeBrandsModel> bikeBrandsModelArrayList, Context context) {
        this.bikeBrandsModelArrayList = bikeBrandsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public BikeBrandsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_brands, viewGroup, false);
        BikeBrandsAdapter.ViewHolder viewHolder =new BikeBrandsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BikeBrandsAdapter.ViewHolder holder, int position) {
        BikeBrandsModel bikeBrandsModel = bikeBrandsModelArrayList.get(position);

        ImageView brand_logo = holder.brand_logo;

        Glide.with(context).load(bikeBrandsModel.getBrand_logo()).into(brand_logo);

    }

    @Override
    public int getItemCount() {
        return bikeBrandsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView brand_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            brand_logo = itemView.findViewById(R.id.image_view_brands);

        }
    }
}
