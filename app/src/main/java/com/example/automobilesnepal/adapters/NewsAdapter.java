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
import com.example.automobilesnepal.models.NewsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<NewsModel> newsModelArrayList;
    Context context;

    public NewsAdapter(ArrayList<NewsModel> newsModelArrayList, Context context) {
        this.newsModelArrayList = newsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_news, viewGroup, false);
        NewsAdapter.ViewHolder viewHolder =new NewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsModel newsModel = newsModelArrayList.get(position);

        TextView news_title = holder.news_title;
        TextView news_excerpt = holder.news_excerpt;
        ImageView news_image = holder.news_image;

        Glide.with(context).load(newsModel.getNews_image()).into(news_image);
        news_title.setText(newsModel.getNews_title());
        news_excerpt.setText(newsModel.getNews_content());

//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier(newsModel.getNews_image(), "drawable", context.getPackageName() );
//        news_image.setImageResource(resourceId);

    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView news_title, news_excerpt, news_read_more;
        public ImageView news_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            news_title = itemView.findViewById(R.id.text_view_news_title);
            news_excerpt = itemView.findViewById(R.id.text_view_news_description);
            news_image = itemView.findViewById(R.id.image_view_news);

        }
    }
}
