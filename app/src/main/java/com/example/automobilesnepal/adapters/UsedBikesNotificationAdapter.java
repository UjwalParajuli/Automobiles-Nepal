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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsedBikesNotificationAdapter extends RecyclerView.Adapter<UsedBikesNotificationAdapter.ViewHolder> {
    ArrayList<UsedBikesModel> usedBikesModelArrayList;
    Context context;

    public UsedBikesNotificationAdapter(ArrayList<UsedBikesModel> usedBikesModelArrayList, Context context) {
        this.usedBikesModelArrayList = usedBikesModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsedBikesNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_used_car_notification, parent, false);
        UsedBikesNotificationAdapter.ViewHolder viewHolder =new UsedBikesNotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsedBikesNotificationAdapter.ViewHolder holder, int position) {
        UsedBikesModel usedBikesModel = usedBikesModelArrayList.get(position);

        Glide.with(context).load(usedBikesModel.getUsed_bike_photo()).into(holder.image_view_notification);
        holder.text_view_notification_title.setText("Have a look at this bike.");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date past = sdf.parse(usedBikesModel.getPosted_date());
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
                holder.text_view_notification_message.setText(usedBikesModel.getBike_model_name() + " " + "has been recently added to used bikes." + " " + seconds + " " + "seconds ago.");
            }
            else if(minutes<60)
            {
                holder.text_view_notification_message.setText(usedBikesModel.getBike_model_name() + " " + "has been recently added to used bikes." + " " + minutes + " " + "minutes ago.");
            }
            else if(hours<24)
            {
                holder.text_view_notification_message.setText(usedBikesModel.getBike_model_name() + " " + "has been recently added to used bikes." + " " + hours + " " + "hours ago.");
            }
            else
            {
                holder.text_view_notification_message.setText(usedBikesModel.getBike_model_name() + " " + "was added to used bikes." + " " + days + " " + "days ago.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return usedBikesModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text_view_notification_message, text_view_notification_title;
        public ImageView image_view_notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_view_notification_message = itemView.findViewById(R.id.text_view_notification_message);
            text_view_notification_title = itemView.findViewById(R.id.text_view_notification_title);
            image_view_notification = itemView.findViewById(R.id.image_view_notification);

        }
    }
}
