package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketActivity extends AppCompatActivity {

    TextView ticketshow;
    Button Done;

    FirebaseAuth authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ticketshow = findViewById(R.id.ticketshow);
        Done = findViewById(R.id.Done);

        authUser = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = authUser.getCurrentUser();

        showProfile(firebaseUser);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String userIdRegistered = firebaseUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered User").child(userIdRegistered);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserDetails userDetails = snapshot.getValue(UserDetails.class);

                        if (userDetails != null) {
                            String userInfo = "Name: " + userDetails.fname +
                                    "\nGender: " + userDetails.fgender +
                                    "\nPhone no.: " + userDetails.fphone;

                            ticketshow.setText(userInfo);
                        }

                        if (getIntent() != null && getIntent().getExtras() != null) {
                            String sourcetext = getIntent().getStringExtra("Source");
                            String desttext = getIntent().getStringExtra("Destination");
                            String noOftravel = getIntent().getStringExtra("Count");
                            String radioButtontext = getIntent().getStringExtra("Radio");

                            if (sourcetext != null && desttext != null && noOftravel != null) {
                                String ticketInfo = ticketshow.getText().toString() +
                                        "\nFrom: " + sourcetext +
                                        "\nDestination: " + desttext +
                                        "\nNumber of travelers: " + noOftravel +
                                        "\nClass: "+radioButtontext;

                                ticketshow.setText(ticketInfo);
                            }
                        } else {
                            Toast.makeText(TicketActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TicketActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
