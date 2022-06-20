package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.customer.CustomerFrag;
import com.example.app.customer.CustomerFragCart;
import com.example.app.customer.CustomerFragOrders;
import com.example.app.customer.CustomerFragProfile;
import com.example.app.customer.CustomerFragTracking;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationCustomer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_customer);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("Page");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name!= null){
            if (name.equalsIgnoreCase("HomePage")){
                loadFragment(new CustomerFrag());
            } else if(name.equalsIgnoreCase("Wait")){
                loadFragment(new CustomerFragTracking());
            } else if(name.equalsIgnoreCase("Delivery")){
                loadFragment(new CustomerFragTracking());
            } else if(name.equalsIgnoreCase("ThankYou")){
                loadFragment(new CustomerFrag());
            }
        }else{
            loadFragment(new CustomerFrag());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.cHome:
                fragment = new CustomerFrag();
                break;


            case R.id.cCart:
                fragment = new CustomerFragCart();
                break;

            case R.id.cOrder:
                fragment = new CustomerFragOrders();
                break;

            case R.id.cTrack:
                fragment = new CustomerFragTracking();
                break;

            case R.id.cProfile:
                fragment = new CustomerFragProfile();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
            return false;
        }
    }
