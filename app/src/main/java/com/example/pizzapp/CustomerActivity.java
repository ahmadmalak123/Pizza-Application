package com.example.pizzapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.pizzapp.databinding.CustomerMainBinding;
import com.example.pizzapp.R;


public class CustomerActivity extends AppCompatActivity {

    CustomerMainBinding binding;

    String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userEmail = getIntent().getStringExtra("userEmail");

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.menu_reviews) {
                replaceFragment(new ReviewsFragment());
            }  else if (itemId == R.id.menu_settings) {
                replaceFragment(new SettingsFragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).setUserEmail(userEmail);
        } else if (fragment instanceof ReviewsFragment) {
            ((ReviewsFragment) fragment).setUserEmail(userEmail);
        } else if (fragment instanceof SettingsFragment) {
            ((SettingsFragment) fragment).setUserEmail(userEmail);
        }
    }

}
