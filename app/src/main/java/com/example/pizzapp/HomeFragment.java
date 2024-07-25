package com.example.pizzapp;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private DatabaseReference databaseReference;
    private String userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMenu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerAdapter = new CustomerAdapter(getActivity(), new ArrayList<>());
        recyclerView.setAdapter(customerAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Food");

        ImageView Location = view.findViewById(R.id.location);

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("google.navigation:q=33.565071,35.388834&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        // Assuming you have an ImageView for your Instagram logo
        ImageView instagramLogo = view.findViewById(R.id.instagramLogo);

        // Set an onClickListener for the Instagram logo
        instagramLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagramPage();
            }
        });

        ImageView phoneLogo = view.findViewById(R.id.phoneLogo);

        // Set an onClickListener for the phone logo
        phoneLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoneApp("+96178975999");
            }
        });
        // Fetch data from Firebase based on categories
        fetchMenuItems();

        return view;
    }

    private void fetchMenuItems() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FoodClass> foodItemList = new ArrayList<>();
                for (String category : getCategoriesInOrder()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodClass foodItem = snapshot.getValue(FoodClass.class);
                        if (foodItem.getFoodcategory().equals(category)) {
                            foodItemList.add(foodItem);
                        }
                    }
                }

                // Update the customer adapter with the new data
                customerAdapter.addData(foodItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(getActivity(), "Failed to load menu items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getCategoriesInOrder() {
        List<String> categoriesInOrder = new ArrayList<>();
        // Add your categories in the desired order
        categoriesInOrder.add("Pizza");
        categoriesInOrder.add("Pasta");
        categoriesInOrder.add("Salad");
        categoriesInOrder.add("Appetizer");
        categoriesInOrder.add("Drinks");
        return categoriesInOrder;
    }

    private void openInstagramPage() {
        // Replace "your_instagram_username" with the actual Instagram username
        String instagramUsername = "yamanispizza";

        // Create an Intent to open the Instagram app or website
        Uri uri = Uri.parse("https://instagram.com/yamanispizza?igshid=MmVlMjlkMTBhMg==" + instagramUsername);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Check if Instagram app is installed, if not, open in a browser
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + instagramUsername)));
        }
    }
    private void openPhoneApp(String phoneNumber) {
        // Create an Intent to open the phone app with the specified number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        // Start the phone app
        startActivity(intent);
    }
}
