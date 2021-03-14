package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.NewsAdapter;
import com.example.automobilesnepal.models.NewsModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment {
    private RecyclerView recycler_view_latest_news;
    private ArrayList<NewsModel> newsModelArrayList;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recycler_view_latest_news = view.findViewById(R.id.recycler_view_latest_news);
        newsModelArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsModelArrayList, getContext());

        getNewsData();
        return view;
    }

    private void getNewsData(){
        NewsModel newsModel = new NewsModel("maruti_swift", "Maruti Swift", "Maruti Suzuki Swift is a 5 seater Hatchback available in a price range of ₹ 5.73 - 8.41 Lakh. It is available in 9 variants, 1 engine option and 2 transmission options : Manual and AMT. Other key specifications of the Swift include a Kerb Weight of 875 kg and Bootspace of 268 litres.");
        NewsModel newsModel2 = new NewsModel("mahindra_thar", "Mahindra Thar", "Maruti Suzuki Swift is a 5 seater Hatchback available in a price range of ₹ 5.73 - 8.41 Lakh. It is available in 9 variants, 1 engine option and 2 transmission options : Manual and AMT. Other key specifications of the Swift include a Kerb Weight of 875 kg and Bootspace of 268 litres.");
        NewsModel newsModel3 = new NewsModel("lamborghini", "Lamborghini", "Maruti Suzuki Swift is a 5 seater Hatchback available in a price range of ₹ 5.73 - 8.41 Lakh. It is available in 9 variants, 1 engine option and 2 transmission options : Manual and AMT. Other key specifications of the Swift include a Kerb Weight of 875 kg and Bootspace of 268 litres.");
        NewsModel newsModel4 = new NewsModel("hyundai_i20", "Hyundai I20", "Maruti Suzuki Swift is a 5 seater Hatchback available in a price range of ₹ 5.73 - 8.41 Lakh. It is available in 9 variants, 1 engine option and 2 transmission options : Manual and AMT. Other key specifications of the Swift include a Kerb Weight of 875 kg and Bootspace of 268 litres.");

        newsModelArrayList.add(newsModel);
        newsModelArrayList.add(newsModel2);
        newsModelArrayList.add(newsModel3);
        newsModelArrayList.add(newsModel4);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view_latest_news.setLayoutManager(linearLayoutManager);
        recycler_view_latest_news.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();

    }
}
