package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth authUser;
    Toolbar toolbar;

    EditText source, destination, counttravel;
    Button proceed;
    RadioGroup classRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authUser = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        counttravel = findViewById(R.id.counttravel);
        proceed = findViewById(R.id.proceedbutton);
        classRadio = findViewById(R.id.classradio); // Corrected id to match XML

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourcetext = source.getText().toString();
                String desttext = destination.getText().toString();
                String noOftravel = counttravel.getText().toString();

                int selectedRadiobuttonId = classRadio.getCheckedRadioButtonId();
                RadioButton selectedButton = findViewById(selectedRadiobuttonId);
                String radioButtontext = selectedButton.getText().toString();

                if (!sourcetext.isEmpty() && !desttext.isEmpty() && !noOftravel.isEmpty() && selectedRadiobuttonId != -1) {
                    Intent intent = new Intent(LoginActivity.this, PaymentActivity.class);
                    intent.putExtra("Source", sourcetext);
                    intent.putExtra("Destination", desttext);
                    intent.putExtra("Count", noOftravel);
                    intent.putExtra("Radio", radioButtontext);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idSelected = item.getItemId();

        if (idSelected == R.id.menuupdate) {
            Intent intent = new Intent(LoginActivity.this, UpdateActivity.class);
            startActivity(intent);
        } else if (idSelected == R.id.yourprofile) {
            Intent intent = new Intent(LoginActivity.this, YourProfile.class);
            startActivity(intent);
        } else if (idSelected == R.id.deleteprofile) {
            Intent intent = new Intent(LoginActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        }
        else if(idSelected == R.id.camera)
        {
            Intent intent = new Intent(LoginActivity.this,CameraActivity.class);
            startActivity(intent);
        }
        else if(idSelected == R.id.map)
        {
            Intent intent = new Intent(LoginActivity.this, MapActivity.class);
            startActivity(intent);
        }
        else if (idSelected == R.id.logout) {
            authUser.signOut();
            Toast.makeText(LoginActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
