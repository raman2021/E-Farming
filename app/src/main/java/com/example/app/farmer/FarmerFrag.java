package com.example.app.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.app.R;
import com.example.app.ShopPage;
import com.example.app.UpdateProduct;
import com.example.app.customer.HomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FarmerFrag extends Fragment {


    RecyclerView recyclerView;
    private List<UpdateProduct> updateProductList;
    private FHomeAdapter adapter;
    DatabaseReference databaseReference, data;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmerfrag, null);
        getActivity().setTitle("Home");


       /* setHasOptionsMenu(true);

         recyclerView = view.findViewById(R.id.recycler);
         recyclerView.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom);
         recyclerView.startAnimation(animation);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
          updateProductList = new ArrayList<>();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        data = FirebaseDatabase.getInstance().getReference("Farmer").child(userid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Farmer farmerr = snapshot.getValue(Farmer.class);
                fProducts();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */
        // swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        //swipeRefreshLayout.setOnRefreshListener(this);
        // swipeRefreshLayout.setColorSchemeResources(R.color.darkgrey, R.color.green);
        return view;
    }

   /* private void fProducts() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ItemDetails").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateProductList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){

                    UpdateProduct updateProduct = snapshot1.getValue(UpdateProduct.class);
                    updateProductList.add(updateProduct);

                }
                adapter = new FHomeAdapter(getContext(),updateProductList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
   // }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int app = item.getItemId();
        if (app == R.id.Logout) {
            LogOut();
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    private void LogOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), ShopPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}









