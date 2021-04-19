package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.fragments.NewBikeDetailsFragment;
import com.example.automobilesnepal.fragments.NewCarDetailsFragment;
import com.example.automobilesnepal.models.BikesModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BikeSliderAdapter extends
        SliderViewAdapter<BikeSliderAdapter.SliderAdapterVH> {

    private Context context;
    private ArrayList<BikesModel> mSliderItems = new ArrayList<>();

    public BikeSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(ArrayList<BikesModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(BikesModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public BikeSliderAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new BikeSliderAdapter.SliderAdapterVH(inflate);
    }


    @Override
    public void onBindViewHolder(BikeSliderAdapter.SliderAdapterVH viewHolder, final int position) {

        BikesModel sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getModel_name());
        viewHolder.textViewDescription.setTextSize(18);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.textViewExcerpt.setText(sliderItem.getDescription());
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getBike_photo())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("new_bike_details", sliderItem);
                Fragment newBikeDetailsFragment = new NewBikeDetailsFragment();
                newBikeDetailsFragment.setArguments(bundle);
                ((AppCompatActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, newBikeDetailsFragment)
                        .addToBackStack(null).commit();
            }
        });
    }


    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription, textViewExcerpt;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            textViewExcerpt = itemView.findViewById(R.id.tv_slider_car_description);
            this.itemView = itemView;
        }
    }
}
