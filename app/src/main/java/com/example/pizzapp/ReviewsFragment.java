package com.example.pizzapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewsFragment extends Fragment {

    private String userEmail;
    private List<Review> reviewList;
    private ReviewAdapter reviewAdapter;
    private EditText editTextReview;
    private EditText editTextRating;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        // Initialize RecyclerView and set its layout manager
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize and set up the adapter
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(reviewAdapter);

        editTextReview = view.findViewById(R.id.editTextReview);
        editTextRating = view.findViewById(R.id.editTextRating);

        Button buttonAddReview = view.findViewById(R.id.buttonAddReview);
        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddReviewButtonClick();
            }
        });

        // Fetch reviews from the database
        fetchReviewsFromDatabase();

        return view;
    }

    // Method to add a review to the database
    private void addReviewToDatabase(String reviewText, float rating) {
        // Get the current timestamp
        Date timestamp = new Date();

        // Create a new Review object
        Review review = new Review(userEmail, timestamp, reviewText, rating);

        // Save the review to the database (you need to implement your database logic here)
        // For example, using Firebase Realtime Database:
        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference("reviews");
        String reviewId = reviewsRef.push().getKey();
        reviewsRef.child(reviewId).setValue(review);

        // Notify the adapter that the dataset has changed
        reviewList.add(review);
        reviewAdapter.notifyDataSetChanged();
    }

    // Method to fetch reviews from the database
    private void fetchReviewsFromDatabase() {
        // Implement your logic to fetch reviews from the database
        // and update the reviewList

        // For example, using Firebase Realtime Database:
        DatabaseReference reviewsRef = FirebaseDatabase.getInstance().getReference("reviews");
        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviewList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
                    if (review != null) {
                        reviewList.add(review);
                    }
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }

    // Method to be called when the "Add Review" button is clicked
    private void onAddReviewButtonClick() {
        String reviewText = editTextReview.getText().toString();
        String ratingString = editTextRating.getText().toString();

        if (!reviewText.isEmpty() && !ratingString.isEmpty()) {
            float rating = Float.parseFloat(ratingString);

            // Validate that the rating is between 0 and 5
            if (isValidRating(rating)) {
                addReviewToDatabase(reviewText, rating);
            } else {
                // Show a toast or handle the error appropriately
                Toast.makeText(getActivity(), "Invalid rating. Please enter a rating between 0 and 5.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidRating(float rating) {
        return rating >= 0 && rating <= 5;
    }

}
