package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView showprice;
    Button payButton;
    int totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        showprice = findViewById(R.id.paymenttext);
        payButton = findViewById(R.id.paymentbutton);

        /*totalprice = getIntent().getIntExtra("Int_Result", 0);

        showprice.setText("Your total fare is " + totalprice);*/


        String sourcetext = getIntent().getStringExtra("Source");
        String desttext = getIntent().getStringExtra("Destination");
        String noOftravel = getIntent().getStringExtra("Count");
        String radioButtontext = getIntent().getStringExtra("Radio");
        showprice.setText("You can now pay the fare");

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
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