package com.example.automobilesnepal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.automobilesnepal.fragments.BikesFragment;
import com.example.automobilesnepal.fragments.BrandCarsListFragment;
import com.example.automobilesnepal.fragments.CarsFragment;
import com.example.automobilesnepal.fragments.DealersFragment;
import com.example.automobilesnepal.fragments.NewCarDetailsFragment;
import com.example.automobilesnepal.fragments.NewsFragment;
import com.example.automobilesnepal.models.CarsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottom_navigation_view =(BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setOnNavigationItemSelectedListener(nav_listener);
        hideBottomBar(false); // to have it visible by default


        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CarsFragment()).commit();
        }

    }

    public void hideBottomBar(boolean isHidden){
        bottom_navigation_view.setVisibility(isHidden ? View.GONE : View.VISIBLE);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener nav_listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                    Fragment selected_fragment = null;
                    getSupportFragmentManager().addOnBackStackChangedListener(
                            new FragmentManager.OnBackStackChangedListener() {
                                @Override
                                public void onBackStackChanged() {
                                    Fragment current = getCurrentFragment();
                                    if (current instanceof CarsFragment){
                                        bottom_navigation_view.getMenu().findItem(R.id.nav_cars).setChecked(true);
                                    }
                                    else if (current instanceof BikesFragment){
                                        bottom_navigation_view.getMenu().findItem(R.id.nav_bikes).setChecked(true);
                                    }
                                    else if (current instanceof NewsFragment){
                                        bottom_navigation_view.getMenu().findItem(R.id.nav_news).setChecked(true);
                                    }
                                    else if (current instanceof DealersFragment){
                                        bottom_navigation_view.getMenu().findItem(R.id.nav_dealers).setChecked(true);
                                    }
                                }
                            });

                    switch (item.getItemId()){
                        case R.id.nav_cars:
                            selected_fragment = new CarsFragment();
                            break;
                        case R.id.nav_bikes:
                            selected_fragment = new BikesFragment();
                            break;
                        case R.id.nav_news:
                            selected_fragment = new NewsFragment();
                            break;
                        case R.id.nav_dealers:
                            selected_fragment = new DealersFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).addToBackStack(null).commit();
                    return true;

                }
            };

    public Fragment getCurrentFragment() {
        return this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment current = getCurrentFragment();
        if (manager.getBackStackEntryCount() >= 1 && !(current instanceof CarsFragment)) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.

            manager.popBackStack(); // clear backstack first
            //FragmentTransaction transaction = manager.beginTransaction();
            if (current instanceof CarsFragment) {
                bottom_navigation_view.getMenu().findItem(R.id.nav_cars).setChecked(true);
            }
            else if(current instanceof BikesFragment) {
                bottom_navigation_view.getMenu().findItem(R.id.nav_bikes).setChecked(true);
            }
            else if (current instanceof NewsFragment){
                bottom_navigation_view.getMenu().findItem(R.id.nav_news).setChecked(true);
            }
            else if (current instanceof DealersFragment){
                bottom_navigation_view.getMenu().findItem(R.id.nav_dealers).setChecked(true);
            }

//            transaction.replace(R.id.fragment_container, new CarsFragment());
//            transaction.commit();

        } else {
            //super.onBackPressed();
            // Otherwise, ask user if he wants to leave :)
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure want to exit the app?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }).create().show();
        }

    }
}