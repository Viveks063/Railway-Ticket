package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YourProfile extends AppCompatActivity {

    TextView showname, showphonenumber, showgender;

    FirebaseAuth authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        showname = findViewById(R.id.showname);
        showphonenumber = findViewById(R.id.showphonenumber);
        showgender = findViewById(R.id.showgender);

        authUser = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authUser.getCurrentUser();

        showProfile(firebaseUser);
    }

    private void showProfile(FirebaseUser firebaseUser) {
        if(firebaseUser != null)
        {
            String userIdRegistered = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered User").child(userIdRegistered);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        UserDetails userDetails = snapshot.getValue(UserDetails.class);

                        if(userDetails != null)
                        {
                            showname.setText("Name: "+userDetails.fname);
                            showgender.setText("Gender: "+userDetails.fgender);
                            showphonenumber.setText("Phone no.: "+userDetails.fphone);
                        }
                        else
                        {
                            Toast.makeText(YourProfile.this, "something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(YourProfile.this, "something went wrong", Toast.LENGTH_LONG).show();

                }
            });

        }

    }
}