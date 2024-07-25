package com.example.pizzapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzapp.AdminActivity;
import com.example.pizzapp.CustomerActivity;
import com.example.pizzapp.UserClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private TextInputEditText editTextEmail, editTextPassword;
    private DatabaseReference usersReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        Button loginButton = view.findViewById(R.id.buttonLogin);

        // Initialize the DatabaseReference for "users"
        usersReference = FirebaseDatabase.getInstance().getReference("users");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = editTextEmail.getText().toString().trim();
                String textPassword = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(getActivity(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the entered credentials match admin credentials
                if (textEmail.equals("admin@gmail.com") && textPassword.equals("pass")) {
                    startActivity(new Intent(getActivity(), AdminActivity.class));
                    return;
                }

                // Check user credentials in the database
                checkUserCredentials(textEmail, textPassword);
            }
        });

        return view;
    }

    private void checkUserCredentials(final String email, final String password) {
        usersReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                UserClass user = userSnapshot.getValue(UserClass.class);
                                if (user != null && user.getPassword().equals(password)) {
                                    // Correct credentials, start corresponding activity
                                    Intent intent = new Intent(getActivity(), CustomerActivity.class);
                                    intent.putExtra("userEmail", user.getEmail());
                                    startActivity(intent);

                                    return;
                                }
                            }
                            // Password doesn't match
                            Toast.makeText(getActivity(), "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else {
                            // User not found
                            Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("DatabaseError", databaseError.getMessage());
                    }
                });
    }
}
