package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.CarBrandsModel;
import com.example.automobilesnepal.models.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class NewsDetailsFragment extends Fragment {
    private Bundle bundle;
    private NewsModel newsModel;
    private ImageView image_view_news;
    private TextView text_view_published_date;
    private TextView text_view_news_title, text_view_news_content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        bundle = getArguments();
        newsModel = (NewsModel) bundle.getSerializable("news_details");

        image_view_news = view.findViewById(R.id.image_view_news_details_photo);

        text_view_published_date = view.findViewById(R.id.text_view_news_details_published_date);
        text_view_news_title = view.findViewById(R.id.text_view_news_details_title);
        text_view_news_content = view.findViewById(R.id.text_view_news_details_description);

        Glide.with(getContext()).load(newsModel.getNews_image()).into(image_view_news);

        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        long finalDate = 0;

        try {
            date = format.parse(newsModel.getPublished_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finalDate = date.getTime();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", finalDate);
        String day = (String) DateFormat.format("dd", finalDate);
        String monthString = (String) DateFormat.format("MMMM", finalDate);

        text_view_published_date.setText(day + " " + monthString + "," + " " + dayOfTheWeek );
        text_view_news_title.setText(newsModel.getNews_title());
        text_view_news_content.setText(newsModel.getNews_content());

        return view;
    }
}
