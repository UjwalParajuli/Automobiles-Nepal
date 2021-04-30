package com.example.automobilesnepal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.UsedCarsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsedCarsNotificationAdapter extends RecyclerView.Adapter<UsedCarsNotificationAdapter.ViewHolder> {
    ArrayList<UsedCarsModel> usedCarsModelArrayList;
    Context context;

    public UsedCarsNotificationAdapter(ArrayList<UsedCarsModel> usedCarsModelArrayList, Context context) {
        this.usedCarsModelArrayList = usedCarsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsedCarsNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_used_car_notification, parent, false);
        UsedCarsNotificationAdapter.ViewHolder viewHolder =new UsedCarsNotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsedCarsNotificationAdapter.ViewHolder holder, int position) {
        UsedCarsModel usedCarsModel = usedCarsModelArrayList.get(position);

        Glide.with(context).load(usedCarsModel.getUsed_car_photo()).into(holder.image_view_notification);
        holder.text_view_notification_title.setText("Have a look at this car.");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date past = sdf.parse(usedCarsModel.getPosted_date());
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

            if(seconds<60)
            {
                holder.text_view_notification_message.setText(usedCarsModel.getCar_model_name() + " " + "has been recently added to used cars." + " " + seconds + " " + "seconds ago.");
            }
            else if(minutes<60)
            {
                holder.text_view_notification_message.setText(usedCarsModel.getCar_model_name() + " " + "has been recently added to used cars." + " " + minutes + " " + "minutes ago.");
            }
            else if(hours<24)
            {
                holder.text_view_notification_message.setText(usedCarsModel.getCar_model_name() + " " + "has been recently added to used cars." + " " + hours + " " + "hours ago.");
            }
            else
            {
                holder.text_view_notification_message.setText(usedCarsModel.getCar_model_name() + " " + "was added to used cars." + " " + days + " " + "days ago.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return usedCarsModelArrayList.size();
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
