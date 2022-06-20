package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.customer.CustomerFrag;
import com.example.app.customer.CustomerFragOrders;
import com.example.app.customer.CustomerFragTracking;
import com.example.app.farmer.FarmerFrag;
import com.example.app.farmer.FarmerFragAddItem;
import com.example.app.farmer.FarmerFragOrders;
import com.example.app.farmer.FarmerFragPending;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationFarmer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_farmer);
        BottomNavigationView navigationView =  findViewById(R.id.farmernavigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("Page");
       // FragmentManager fragmentManager = getSupportFragmentManager();
       // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name!= null){
            if (name.equalsIgnoreCase("OrderPage")){
                loadfarmerrfragment(new FarmerFragPending());
            } else if(name.equalsIgnoreCase("ConfirmPage")){
                loadfarmerrfragment(new FarmerFragOrders());
            } else if(name.equalsIgnoreCase("AcceptOrder")){
                loadfarmerrfragment(new FarmerFragOrders());
            } else if(name.equalsIgnoreCase("ThankYou")){
                loadfarmerrfragment(new FarmerFragOrders());
            }
        }else{
            loadfarmerrfragment(new FarmerFrag());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.farmerHome:
                fragment = new FarmerFrag();
                break;
            case R.id.Pending:
                fragment = new FarmerFragPending();
                break;
            case R.id.Orders:
                fragment = new FarmerFragOrders();
                break;
            case R.id.farmerProfile:
                fragment = new FarmerFragAddItem();
                break;
        }
        return loadfarmerrfragment(fragment);


    }

    private boolean loadfarmerrfragment(Fragment fragment) {
        if (fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
            return true;
        }
        return false;
    }
}