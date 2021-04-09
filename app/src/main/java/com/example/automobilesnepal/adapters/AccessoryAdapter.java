package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.AccessoriesModel;
import com.example.automobilesnepal.models.CarsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.ViewHolder> {
    ArrayList<AccessoriesModel> accessoriesModelArrayList;
    Context context;

    public AccessoryAdapter(ArrayList<AccessoriesModel> accessoriesModelArrayList, Context context) {
        this.accessoriesModelArrayList = accessoriesModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AccessoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_accessories, viewGroup, false);
        AccessoryAdapter.ViewHolder viewHolder =new AccessoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryAdapter.ViewHolder holder, int position) {
        AccessoriesModel accessoriesModel = accessoriesModelArrayList.get(position);

        TextView accessory_name = holder.accessory_name;
        TextView accessory_price = holder.accessory_price;
        ImageView accessory_image = holder.accessory_image;

        Glide.with(context).load(accessoriesModel.getImage()).into(holder.accessory_image);
        accessory_name.setText(accessoriesModel.getTitle());
        accessory_price.setText("Rs " + accessoriesModel.getAccessory_price());
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(carsModel.getCar_image(), "drawable", context.getPackageName() );
//        car_image.setImageResource(resourceId);

    }

    @Override
    public int getItemCount() {
        return accessoriesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView accessory_name, accessory_price;
        public ImageView accessory_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accessory_name = itemView.findViewById(R.id.text_view_accessory_name);
            accessory_image = itemView.findViewById(R.id.image_view_accessory);
            accessory_price = itemView.findViewById(R.id.text_view_accessory_price);

        }
    }
}
