package com.example.pizzapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> reviewList;

    // Constructor to initialize the list
    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    // ViewHolder class to hold the views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUserEmail;
        public TextView textViewTimestamp;
        public TextView textViewReviewText;
        public TextView textViewRating;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewUserEmail = itemView.findViewById(R.id.textViewUserEmail);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            textViewReviewText = itemView.findViewById(R.id.textViewReviewText);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.textViewUserEmail.setText(review.getUserEmail());
        holder.textViewTimestamp.setText(review.getFormattedTimestamp());
        holder.textViewReviewText.setText(review.getReviewText());
        holder.textViewRating.setText(String.valueOf(review.getRating())+"/5");
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
