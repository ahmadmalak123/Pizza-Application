package com.example.pizzapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private TextInputEditText editTextName, editTextPhoneNumber, editTextEmail,
            editTextPassword, editTextConfirmPassword;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        editTextName = view.findViewById(R.id.editTextName);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);

        Button signUpButton = view.findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate input fields
                if (isValidInput()) {
                    // Initialize Firebase
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    // Get user input
                    String name = editTextName.getText().toString().trim();
                    String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();

                    // Create a unique user ID using push
                    String userId = reference.push().getKey();

                    // Create a UserClass object
                    UserClass user = new UserClass(name, email, phoneNumber, password);

                    // Save user data to the Realtime Database
                    reference.child(userId).setValue(user);

                    Toast.makeText(getActivity(), "You have signed up successfully!", Toast.LENGTH_SHORT).show();

                    // Start the next activity
                    Intent intent = new Intent(getActivity(), CustomerActivity.class);
                    intent.putExtra("userEmail", email);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private boolean isValidInput() {
        String name = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Perform your validation logic here
        // For example, you can check if the fields are not empty and if the passwords match

        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
