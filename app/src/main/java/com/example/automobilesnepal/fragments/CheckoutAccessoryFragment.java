package com.example.automobilesnepal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.AccessoriesModel;
import com.example.automobilesnepal.utils.ErrorUtils;
import com.example.automobilesnepal.utils.SharedPrefManager;
import com.example.automobilesnepal.utils.User;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.widget.KhaltiButton;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class CheckoutAccessoryFragment extends Fragment {
    private Bundle bundle;
    private AccessoriesModel accessoriesModel;
    private EditText edit_text_accessory_quantity, edit_text_accessory_total_cost;
    private KhaltiButton button_open_khalti_accessory;
    private ProgressBar progress_bar_accessory_checkout;
    private long total_cost = 0;
    private User user;

    private ImageButton image_button_bar, image_button_heart;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_accessory, container, false);

        bundle = getArguments();
        accessoriesModel = (AccessoriesModel) bundle.getSerializable("accessory_details");
        user = SharedPrefManager.getInstance(getContext()).getUser();

        edit_text_accessory_quantity = view.findViewById(R.id.edit_text_accessory_quantity);
        edit_text_accessory_total_cost = view.findViewById(R.id.edit_text_accessory_total_cost);

        button_open_khalti_accessory = view.findViewById(R.id.button_open_khalti_accessory);

        progress_bar_accessory_checkout = view.findViewById(R.id.progress_bar_accessory_checkout);

        edit_text_accessory_total_cost.setEnabled(false);
        edit_text_accessory_total_cost.setInputType(InputType.TYPE_NULL);
        edit_text_accessory_total_cost.setFocusableInTouchMode(false);

        edit_text_accessory_quantity.addTextChangedListener(quantityTextWatcher);

        button_open_khalti_accessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed();

            }
        });

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

    private TextWatcher quantityTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String quantity  = edit_text_accessory_quantity.getText().toString().trim();
            try{
                if (Integer.parseInt(quantity) > accessoriesModel.getAvailable_quantity()){
                    edit_text_accessory_quantity.setError("Only" + " " + String.valueOf(accessoriesModel.getAvailable_quantity()) + " " +
                            "items available");
                    button_open_khalti_accessory.setEnabled(false);
                }

                if (Integer.parseInt(quantity) <= accessoriesModel.getAvailable_quantity()){
                    edit_text_accessory_quantity.setError(null);
                    button_open_khalti_accessory.setEnabled(true);
                    int quantity2 = Integer.parseInt(edit_text_accessory_quantity.getText().toString().trim());
                    double cost_per_item = accessoriesModel.getAccessory_price() ;
                    double total_amount = quantity2 * cost_per_item;
                    total_cost = (long) total_amount;
                    edit_text_accessory_total_cost.setText(String.valueOf(total_cost));
                }
            }catch (NumberFormatException ex){
                edit_text_accessory_quantity.setError(null);
                edit_text_accessory_total_cost.setText("");
                button_open_khalti_accessory.setEnabled(false);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void proceed(){
        String quantity;
        boolean error = false;

        quantity = edit_text_accessory_quantity.getText().toString().trim();

        try {
            if (quantity.isEmpty()){
                edit_text_accessory_quantity.setError("Please fill this field");
                error = true;
            }

            if (Integer.parseInt(quantity) > accessoriesModel.getAvailable_quantity()){
                edit_text_accessory_quantity.setError("Tickets not available");
                error = true;
            }
            if (Integer.parseInt(quantity) == 0){
                edit_text_accessory_quantity.setError("Cannot purchase 0 ticket");
                error = true;
            }
        }catch (Exception ex){
            edit_text_accessory_quantity.setError("Please fill the field");
            error = true;
        }


        if (error == false){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Note");
            builder.setMessage("Once the payment is done, it will not be refunded. Are you sure you want to proceed?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    openKhalti();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }


    }

    private void openKhalti() {
        long amount = Long.parseLong(edit_text_accessory_total_cost.getText().toString().trim());
        openKhaltiApp(amount);
    }

    private void openKhaltiApp(long amount) {
        amount *= 100;
        Config.Builder builder = new Config.Builder("test_public_key_6239eac0ae384e8a874a3514b2f294a8", "Product Id", "Product Name",
                amount, new OnCheckOutListener() {

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
                addOrder();

            }

            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.e("hello", errorMap.toString());
                Toast.makeText(getContext(), "Khalti Error", Toast.LENGTH_SHORT).show();

            }

        });

        Config config = builder.build();

        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(getContext(), config);
        khaltiCheckOut.show();
    }

    private void addOrder(){
        String url = "http://192.168.1.65:81/android/accessory_order.php";
        progress_bar_accessory_checkout.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress_bar_accessory_checkout.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.trim().equals("success")) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                } else if (response.trim().equals("error")) {
                    Toast.makeText(getContext(), "First DB Error", Toast.LENGTH_SHORT).show();
                } else if (response.trim().equals("error2")) {
                    Toast.makeText(getContext(), "Second DB Error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar_accessory_checkout.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accessory_id", String.valueOf(accessoriesModel.getAccessory_id()));
                params.put("user_id", String.valueOf(user.getId()));
                params.put("ordered_quantity", edit_text_accessory_quantity.getText().toString());
                params.put("total_price", edit_text_accessory_total_cost.getText().toString());
                params.put("total_quantity", String.valueOf(accessoriesModel.getAvailable_quantity()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }



}
