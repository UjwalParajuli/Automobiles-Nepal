package com.example.automobilesnepal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.adapters.NewsAdapter;
import com.example.automobilesnepal.models.CarsModel;
import com.example.automobilesnepal.models.NewsModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.ItemClickSupport;
import com.example.automobilesnepal.utils.SpacesItemDecoration;
import com.example.automobilesnepal.utils.VerticalSpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment {
    private RecyclerView recycler_view_latest_news;
    private ArrayList<NewsModel> newsModelArrayList;
    private NewsAdapter newsAdapter;
    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recycler_view_latest_news = view.findViewById(R.id.recycler_view_news);
        newsModelArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsModelArrayList, getContext());

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

        getNewsData();
        return view;
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void getNewsData(){
        String url = "http://192.168.1.65:81/android/get_news.php";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("not_found")) {
                    Toast.makeText(getContext(), "No any news found", Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonResponse;

                        for (int i = 0; i < jsonArray.length(); i++){
                            jsonResponse = jsonArray.getJSONObject(i);

//                            JSONObject users = jsonResponse.getJSONObject("users");
                            int news_id = jsonResponse.getInt("id");
                            String title = jsonResponse.getString("title");
                            String photo = jsonResponse.getString("photo");
                            String description = jsonResponse.getString("description");
                            String published_date = jsonResponse.getString("published_date");

                            NewsModel newsModel = new NewsModel(photo, title, description, published_date);
                            newsModelArrayList.add(newsModel);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler_view_latest_news.setLayoutManager(linearLayoutManager);
                        recycler_view_latest_news.setAdapter(newsAdapter);
                        recycler_view_latest_news.addItemDecoration(new VerticalSpacesItemDecoration(40));
                        newsAdapter.notifyDataSetChanged();

                        ItemClickSupport.addTo(recycler_view_latest_news).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                NewsModel news_model = newsModelArrayList.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("news_details", news_model);
                                Fragment newsDetailsFragment = new NewsDetailsFragment();
                                newsDetailsFragment.setArguments(bundle);
                                getFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, newsDetailsFragment)
                                        .addToBackStack(null).commit();
                            }
                        });


                    }

                    catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        requestQueue.add(stringRequest);



    }
}
