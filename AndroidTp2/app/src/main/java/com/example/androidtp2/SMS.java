package com.example.androidtp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SMS extends Activity {
    private Button requestButton,sendSmsButton;
    private TextView numberPhoneView;
    private String texte,liste;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        numberPhoneView = (EditText) findViewById(R.id.numberPhone);
        Bundle bundle = getIntent().getExtras();
        texte = bundle.getString("item");
        requestButton = (Button) findViewById(R.id.menu);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString( "liste" ,texte);
                Intent requestActivity = new Intent(SMS.this, Request.class);
                requestActivity.putExtras(bundle);
                startActivity(requestActivity);
            }
        });
        sendSmsButton = (Button) findViewById(R.id.sendButton);
        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(numberPhoneView.getText().toString(),texte);
                }
        });
        sendSmsButton.setEnabled(false);
        numberPhoneView.addTextChangedListener(new TextWatcher() {
                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                           }
                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before, int count) {
                                               sendSmsButton.setEnabled(true);
                                           }
                                           @Override
                                           public void afterTextChanged(Editable s) {

                                           }
                                       }
        );

    }
    protected void sendSMS(String phoneNoP,String textP) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNoP, null, textP + " est intéressant à regarder", null, null);
    }
}
