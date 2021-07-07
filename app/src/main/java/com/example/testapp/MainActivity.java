package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    TextView titleText;
    ListView listView;
    TextView totalAmountField;
    BigDecimal totalAmount = new BigDecimal("0.00");
    TextView manualEntryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        titleText = findViewById(R.id.textView);
        listView = findViewById(R.id.itemSelect);
        totalAmountField = findViewById(R.id.amountField);
        manualEntryText = findViewById(R.id.manualentry);

        titleText.setText("QUAC Pay");

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ItemName.values());
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);
        totalAmountField.setText(totalAmount.toString());

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(this);

        Button oneButton = findViewById(R.id.one);
        oneButton.setOnClickListener(this);
        Button twoButton = findViewById(R.id.two);
        twoButton.setOnClickListener(this);
        Button threeButton = findViewById(R.id.three);
        threeButton.setOnClickListener(this);
        Button fourButton = findViewById(R.id.four);
        fourButton.setOnClickListener(this);
        Button fiveButton = findViewById(R.id.five);
        fiveButton.setOnClickListener(this);
        Button sixButton = findViewById(R.id.six);
        sixButton.setOnClickListener(this);
        Button sevenButton = findViewById(R.id.seven);
        sevenButton.setOnClickListener(this);
        Button eightButton = findViewById(R.id.eight);
        eightButton.setOnClickListener(this);
        Button nineButton = findViewById(R.id.nine);
        nineButton.setOnClickListener(this);
        Button backButton = findViewById(R.id.backspace);
        backButton.setOnClickListener(this);
        Button zeroButton = findViewById(R.id.zero);
        zeroButton.setOnClickListener(this);
        Button periodButton = findViewById(R.id.period);
        periodButton.setOnClickListener(this);
        Button enterButton = findViewById(R.id.enter);
        enterButton.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        totalAmount = totalAmount.add(ItemName.fromId(id));
        totalAmountField.setText(totalAmount.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                manualEntryText.append("1");
                break;
            case R.id.two:
                manualEntryText.append("2");
                break;
            case R.id.three:
                manualEntryText.append("3");
                break;
            case R.id.four:
                manualEntryText.append("4");
                break;
            case R.id.five:
                manualEntryText.append("5");
                break;
            case R.id.six:
                manualEntryText.append("6");
                break;
            case R.id.seven:
                manualEntryText.append("7");
                break;
            case R.id.eight:
                manualEntryText.append("8");
                break;
            case R.id.nine:
                manualEntryText.append("9");
                break;
            case R.id.backspace:
                backspaceAction();
                break;
            case R.id.zero:
                manualEntryText.append("0");
                break;
            case R.id.period:
                manualEntryText.append(".");
                break;
            case R.id.enter:
                totalAmount = totalAmount.add(new BigDecimal(manualEntryText.getText().toString()));
                totalAmountField.setText(totalAmount.toString());
                manualEntryText.setText("");
                break;
            case R.id.payButton:
                sendPaymentToCustomer();
                break;
//            case R.id.scanButton:
//                getBarCode();
//                break;
        }

    }

    private void backspaceAction() {
        String word = manualEntryText.getText().toString();
        int input = word.length();
        if (input > 0){
            manualEntryText.setText(word.substring(0, input-1));
        }
    }

    private void sendPaymentToCustomer() {
        Intent intent = new Intent(MainActivity.this, PaymentActivity.class) {{
            putExtra("amount", String.valueOf(totalAmount));
            putExtra("merchantId", "000000502914");
            putExtra("terminalId", "2216");
        }};
        this.finish();
//        Toast.makeText(this, "Waiting for customer payment authorization", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void getBarCode() {

    }

}
