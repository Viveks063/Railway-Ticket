package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteProfileActivity extends AppCompatActivity {

    Button deleteButton;
    FirebaseAuth authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);

        deleteButton = findViewById(R.id.deleteButtton);
        authUser = FirebaseAuth.getInstance();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });
    }

    private void deleteProfile() {
        FirebaseUser firebaseUser = authUser.getCurrentUser();

        if (firebaseUser != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered User");
            String userId = firebaseUser.getUid();

            databaseReference.child(userId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(DeleteProfileActivity.this, "Entry deleted successfully", Toast.LENGTH_SHORT).show();

                    authUser.signOut();

                    Intent intent = new Intent(DeleteProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DeleteProfileActivity.this, "Error deleting entry", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DeleteProfileActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
