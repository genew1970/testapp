package com.example.testapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import pl.droidsonroids.gif.GifImageView;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    public String postUrl = "https://gateway-sb.clearent.net/quacrequest/create";
    public String getUrl= "https://gateway-sb.clearent.net/quacrequest/get/payment-status/000000502914/2216";
    PaymentInfo paymentInfo;
    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    TextView statusText;
    GifImageView waitingView;
    GifImageView completedView;
    Button homeButton;
    PaymentInfo orderInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle paymentInfo = getIntent().getExtras();
        this.paymentInfo = (PaymentInfo) paymentInfo.getSerializable("paymentInfo");
        statusText = findViewById(R.id.statusView);
        statusText.setText("Sending payment to customer");
        String mid = (String) getIntent().getSerializableExtra("merchantId");
        String tid = (String) getIntent().getSerializableExtra("terminalId");
        String amount = (String) getIntent().getSerializableExtra("amount");
        orderInfo = new PaymentInfo(mid, tid, amount);

        completedView = findViewById(R.id.completedView);
        completedView.setVisibility(View.INVISIBLE);

        waitingView = findViewById(R.id.waitingView);
        waitingView.setVisibility(View.INVISIBLE);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);
        homeButton.setVisibility(View.INVISIBLE);
//        progressBar.setVisibility(View.VISIBLE);
//        processPaymentButton = findViewById(R.id.processOrderButton);
//        processPaymentButton.setOnClickListener(this);

        sendPaymentToCustomer();
    }



    public void sendPaymentToCustomer() {
      boolean isSuccess = true;
        try {
            isSuccess = submitPayment();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isSuccess) {
            getCustomerPaymentStatus();
        }
    }

    private void getCustomerPaymentStatus() {
        boolean isWaitingForTransaction = true;
        while (isWaitingForTransaction) {
            try {
                isWaitingForTransaction = getPaymentStatus();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isWaitingForTransaction) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean getPaymentStatus() throws Exception {
        Request request = new Request.Builder()
                .url(getUrl)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return false;
            } else if (response.code() == 204) {
//                titleText.setText("Waiting for the customer to authorize the payment");
                return true;
            } else {
                homeButton.setVisibility(View.VISIBLE);
                waitingView.setVisibility(View.GONE);
                completedView.setVisibility(View.VISIBLE);
                statusText.setText("Payment completed!");
                MediaPlayer mp = MediaPlayer.create(this, R.raw.duckquack);
                mp.start();
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MediaPlayer mp2 = MediaPlayer.create(this, R.raw.duckquack);
                mp2.start();
                return false;
            }

        }

    }

    public boolean submitPayment() throws Exception {
        String postBody = "{" +
                "    \"merchantID\": \"000000502914\",\n" +
                "    \"terminalID\": \"2216\",\n" +
                "    \"amount\": " + orderInfo.getAmount() + "\n" +
                "}";

        Request request = new Request.Builder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .url(postUrl)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return false;
            } else {
                statusText.setText("Waiting for customer to authorize payment");
                return true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.processOrderButton:
//                findViewById(R.id.processOrderButton).setVisibility(View.INVISIBLE);
//                findViewById(R.id.waitingView).setVisibility(View.VISIBLE);
//                statusText.setText("Waiting for customer authorization");
////                try {
////                    Thread.sleep(700);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                sendPaymentToCustomer();
//                break;
            case R.id.homeButton:
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                this.finish();
                startActivity(intent);
                break;
        }
    }
}
