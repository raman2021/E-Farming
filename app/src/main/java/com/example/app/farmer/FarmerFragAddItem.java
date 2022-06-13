package com.example.app.farmer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.app.R;

public class FarmerFragAddItem extends Fragment {


    Button addItem;
    ConstraintLayout bimg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmerfragadditem, null);
        getActivity().setTitle("Add Product");


        AnimationDrawable animationDrawable= new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmer), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmer2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmers), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.tractor), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.veg), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1500);

        bimg = view.findViewById(R.id.back);
        bimg.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        addItem = (Button) view.findViewById(R.id.add_product);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProduct.class));
            }
        });
        return view;
    }
}
