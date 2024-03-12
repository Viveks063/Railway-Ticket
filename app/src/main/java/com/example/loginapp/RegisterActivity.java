package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText fullname1, email1, password1, phonenumber1, confirmpassword1;
    RadioGroup radioGroup1;
    RadioButton radiobuttonselectedgender;
    Button submit1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname1 = findViewById(R.id.name);
        email1 = findViewById(R.id.email);
        password1 = findViewById(R.id.password);
        phonenumber1 = findViewById(R.id.phonenumber);
        confirmpassword1 = findViewById(R.id.confirmpassword);
        radioGroup1 = findViewById(R.id.radioGroup);
        radioGroup1.clearCheck();


        submit1 = findViewById(R.id.submit);
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = radioGroup1.getCheckedRadioButtonId();
                radiobuttonselectedgender = findViewById(selectedGenderId);


                String textname = fullname1.getText().toString();
                String textemail = email1.getText().toString();
                String textphone = phonenumber1.getText().toString();
                String textpassword = password1.getText().toString();
                String textconfirmpass = confirmpassword1.getText().toString();
                String textgender;



                if(TextUtils.isEmpty(textname))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_LONG).show();
                    fullname1.setError("Name required");
                    fullname1.requestFocus();

                }
                else if(TextUtils.isEmpty(textemail))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    email1.setError("Email required");
                    email1.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textemail).matches())
                {
                    Toast.makeText(RegisterActivity.this, "Please enter valid email address", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(textphone))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your phone no.", Toast.LENGTH_LONG).show();
                    phonenumber1.setError("Phone no. required");
                    phonenumber1.requestFocus();
                }
                else if(textphone.length()!=10)
                {
                    Toast.makeText(RegisterActivity.this, "Please enter valid phone no.", Toast.LENGTH_LONG).show();
                    phonenumber1.setError("Enter Valid  phone no.");
                    phonenumber1.requestFocus();
                }
                else if(TextUtils.isEmpty(textpassword))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    password1.setError("Password required");
                    password1.requestFocus();
                }
                else if(textpassword.length() < 8)
                {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 8 digits", Toast.LENGTH_SHORT).show();
                    password1.setError("Password is weak");
                    password1.requestFocus();
                }
                else if(TextUtils.isEmpty(textconfirmpass))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your confirm password", Toast.LENGTH_LONG).show();
                    confirmpassword1.setError("Confirm Password required");
                    confirmpassword1.requestFocus();
                }
                else if(!textpassword.equals(textconfirmpass))
                {
                    Toast.makeText(RegisterActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                    confirmpassword1.setError("Password confirmation failed");
                    confirmpassword1.requestFocus();

                    password1.clearComposingText();
                    confirmpassword1.clearComposingText();

                }
                else if(radioGroup1.getCheckedRadioButtonId()== -1)
                {
                    Toast.makeText(RegisterActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                }
                else
                {
                    textgender = radiobuttonselectedgender.getText().toString();
                    registered(textname, textemail, textphone, textgender, textpassword, textconfirmpass);
                }



            }
        });
    }

    private void registered(String textname, String textemail, String textphone, String textgender, String textpassword, String textconfirmpass) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textemail, textpassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    UserDetails detail = new UserDetails(textname, textphone, textgender);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");

                    referenceProfile.child(firebaseUser.getUid()).setValue(detail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Verify your email", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

    }
}