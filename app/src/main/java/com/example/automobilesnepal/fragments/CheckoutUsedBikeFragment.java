package com.example.automobilesnepal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automobilesnepal.R;
import com.example.automobilesnepal.models.UsedBikesModel;
import com.example.automobilesnepal.models.UsedCarsModel;
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

public class CheckoutUsedBikeFragment extends Fragment {
    private Bundle bundle;
    private UsedBikesModel usedBikesModel;
    private KhaltiButton button_open_khalti_used_bike;
    private ProgressBar progress_bar_used_bike_checkout;
    private long total_cost = 0;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_used_bike, container, false);

        bundle = getArguments();
        usedBikesModel = (UsedBikesModel) bundle.getSerializable("used_bike_details");

        user = SharedPrefManager.getInstance(getContext()).getUser();

        button_open_khalti_used_bike = view.findViewById(R.id.button_open_khalti_used_bike);
        progress_bar_used_bike_checkout = view.findViewById(R.id.progress_bar_used_bike_checkout);

        button_open_khalti_used_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed();
            }
        });


        return view;
    }

    private void proceed(){
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

    private void openKhalti() {
        long amount = 1000;
        openKhaltiApp(amount);
    }

    private void openKhaltiApp(long amount) {
        amount *= 100;
        Config.Builder builder = new Config.Builder("test_public_key_6239eac0ae384e8a874a3514b2f294a8", String.valueOf(usedBikesModel.getUsed_bike_id()), usedBikesModel.getBike_model_name(),
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
        String url = "http://192.168.1.65:81/android/used_bike_order.php";
        progress_bar_used_bike_checkout.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress_bar_used_bike_checkout.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if (response.trim().equals("success")) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                } else if (response.trim().equals("error")) {
                    Toast.makeText(getContext(), "First DB Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar_used_bike_checkout.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(getContext(), ErrorUtils.getVolleyError(error), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("used_bike_id", String.valueOf(usedBikesModel.getUsed_bike_id()));
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
