package com.example.androidtp2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Protocol.BaseRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class Menu extends Activity {
    private Button logoutButton, otherButton,requestDecision;
    private static ObjectOutputStream objectOutputClient = null;
    private static ObjectInputStream objectInputClient = null;
    private static Socket serveurSocket = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        logoutButton = (Button) findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                threadServeur serv = new threadServeur();
                try {
                    serv.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent loginActivity = new Intent(Menu.this, Login.class);
                Bundle bundle = new Bundle();
                bundle.putString("Logout", "Logout");
                loginActivity.putExtras(bundle);
                startActivity(loginActivity);
            }
        });
        /*otherButton = (Button) findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });*/
        requestDecision = (Button) findViewById(R.id.requestDecisionButton);
        requestDecision.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mailActivity = new Intent(Menu.this, Request.class);
                startActivity(mailActivity);
            }
        });
    }

    private class threadServeur extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            contacteServeur();
            return null;
        }
    }

    private void contacteServeur() {
        objectInputClient = Login.getInStream();
        objectOutputClient = Login.getOutStream();
        try {
            BaseRequest requete = new BaseRequest();
            requete.setId(BaseRequest.LOGOUT);
            objectOutputClient.writeObject(requete);
            objectOutputClient.flush();
        } catch (Exception e) {
            System.err.println("Erreur : " + e);
        }
    }
}
