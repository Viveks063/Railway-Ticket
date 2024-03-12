package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText Email, Password;

    Button login;
    FirebaseAuth authUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Password = findViewById(R.id.Password);
        Email = findViewById(R.id.Email);

        authUser = FirebaseAuth.getInstance();
        login = findViewById(R.id.Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail = Email.getText().toString();
                String textPassword = Password.getText().toString();


                if(TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(MainActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    Email.setError("Email required");
                    Email.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(MainActivity.this, "Please enter Valid Email", Toast.LENGTH_SHORT).show();
                    Email.setError("Email is not Valid");
                    Email.requestFocus();
                }
                else if(TextUtils.isEmpty(textPassword))
                {
                    Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    Password.setError("Password required");
                    Password.requestFocus();
                }
                else
                {
                    loginUser(textEmail, textPassword);
                }

            }
        });


        Button buttonR = findViewById(R.id.register);
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser(String Email, String Password) {
        authUser.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, "You are Logged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authUser.getCurrentUser() != null)
        {
            Toast.makeText(MainActivity.this, "You are Already logged in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        else
        {
            Toast.makeText(MainActivity.this, "You can log in now", Toast.LENGTH_SHORT).show();
        }
    }
}