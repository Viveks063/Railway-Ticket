package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    EditText updateName, updatePhone;
    RadioGroup radioGroupupdate;
    RadioButton radioButtonupdate;
    Button updateButton;
    FirebaseAuth authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateName = findViewById(R.id.name1);
        updatePhone = findViewById(R.id.phonenumber1);
        radioGroupupdate = findViewById(R.id.radioGroupupdate);
        updateButton = findViewById(R.id.updatebutton);
        authUser = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authUser.getCurrentUser();
        showProfile(firebaseUser);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String userIdRegistered = firebaseUser.getUid();
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User").child(userIdRegistered);

            referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserDetails userDetails = snapshot.getValue(UserDetails.class);
                        if (userDetails != null) {
                            updateName.setText(userDetails.fname);
                            updatePhone.setText(userDetails.fphone);
                            String textgender = userDetails.fgender;
                            textgender.toString();

                            if(textgender.equals("Male"))
                            {
                                radioButtonupdate = findViewById(R.id.maleupdate);
                            }
                            else
                            {
                                radioButtonupdate = findViewById(R.id.femaleupdate);
                            }
                            radioButtonupdate.setChecked(true);

                        }
                    } else {
                        Toast.makeText(UpdateActivity.this, "User details not found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UpdateActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(UpdateActivity.this, "User not authenticated", Toast.LENGTH_LONG).show();
        }
    }

    private void updateProfile(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Registered User").child(userId);

            String newName = updateName.getText().toString();
            String newPhone = updatePhone.getText().toString();
            int selectedRadioButtonId = radioGroupupdate.getCheckedRadioButtonId();
            radioButtonupdate = findViewById(selectedRadioButtonId);
            String newGender = radioButtonupdate.getText().toString();

            if (TextUtils.isEmpty(newName)) {
                Toast.makeText(UpdateActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(newPhone)) {
                Toast.makeText(UpdateActivity.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
            } else {
                UserDetails userDetails = new UserDetails(newName, newPhone, newGender);

                userRef.setValue(userDetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(UpdateActivity.this, "User not authenticated", Toast.LENGTH_LONG).show();
        }
    }
}
