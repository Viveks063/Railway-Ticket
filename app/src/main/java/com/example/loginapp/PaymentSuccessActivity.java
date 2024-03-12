package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentSuccessActivity extends AppCompatActivity {

    Button viewTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        viewTicket = findViewById(R.id.ticketshowed);
        String sourcetext = getIntent().getStringExtra("Source");
        String desttext = getIntent().getStringExtra("Destination");
        String noOftravel = getIntent().getStringExtra("Count");
        String radioButtontext = getIntent().getStringExtra("Radio");

        viewTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentSuccessActivity.this, TicketActivity.class);
                intent.putExtra("Source", sourcetext);
                intent.putExtra("Destination", desttext);
                intent.putExtra("Count", noOftravel);
                intent.putExtra("Radio", radioButtontext);
                startActivity(intent);
                finish();


            }
        });


    }
}