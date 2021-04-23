package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
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
import androidx.fragment.app.FragmentManager;

public class NewsDetailsFragment extends Fragment {
    private Bundle bundle;
    private NewsModel newsModel;
    private ImageView image_view_news;
    private TextView text_view_published_date;
    private TextView text_view_news_title, text_view_news_content;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

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

        image_button_bar = view.findViewById(R.id.image_button_bar);
        image_button_heart = view.findViewById(R.id.image_button_heart);
        searchView = view.findViewById(R.id.search_view);

        image_button_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new ProfileFragment());
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("searchQuery", query);
                Fragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, searchFragment).addToBackStack(null).commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        image_button_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new MyFavouritesFragment());
            }
        });

        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
