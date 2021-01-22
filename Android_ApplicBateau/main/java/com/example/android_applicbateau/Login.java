package com.example.android_applicbateau;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Protocol.BaseRequest;
import Protocol.RequestLogin;
import Protocol.RequestLoginInitiator;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


public class Login extends Activity {
    private Button checkBoutton,logoutBoutton;
    private EditText nomView;
    private EditText mdpView;
    private String nom, mdp,ipv4Adress,texte="";
    private int port;
    private RequestLoginInitiator requestLoginInitiator=null;
    private RequestLogin requestLogin=null;
    private BaseRequest request=null;
    private static ObjectOutputStream objectOutputClient = null;
    private static ObjectInputStream objectInputClient = null;
    private static Socket serveurSocket = null;
    private Properties properties;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            texte = bundle.getString("Logout");
            if(texte.equals("Logout"))
            {
                try {
                    objectInputClient.close();
                    objectOutputClient.close();
                    serveurSocket.close();
                    objectInputClient = null;
                    objectOutputClient = null;
                    serveurSocket = null;
                }
                catch(Exception e)
                {
                    System.err.println("err" + e);
                }
            }
        }
        try {
            InputStream is = getBaseContext().getAssets().open("config.properties");
            properties = new Properties();
            properties.load(is);
            ipv4Adress = properties.getProperty("IPV4_SERVER");
            port = Integer.parseInt(properties.getProperty("PORT_SERVER"));
            is.close();
        } catch (Exception e) {
        }
        nomView = (EditText) findViewById(R.id.Nom);
        mdpView = (EditText) findViewById(R.id.Mdp);
        checkBoutton = (Button) findViewById(R.id.checkButton);
        checkBoutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nom = nomView.getText().toString();
                mdp = mdpView.getText().toString();
                threadServeur serv = new threadServeur();
                try {
                    serv.execute(nom, mdp, texte).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!requestLoginInitiator.getStatus())
                    Toast.makeText(getApplicationContext(),
                            "Request Login failed, please try again : " + requestLoginInitiator.getError_msg(), Toast.LENGTH_LONG).show();
                else {
                    if (request.getStatus()) {
                        Intent menuActivity = new Intent(Login.this, Menu.class);
                        startActivity(menuActivity);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Request Login failed, please try again : " + requestLoginInitiator.getError_msg(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        checkBoutton.setEnabled(false);
        mdpView.setEnabled(false);
        nomView.addTextChangedListener(new TextWatcher() {
                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                           }

                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before, int count) {
                                               mdpView.setEnabled(true);
                                           }

                                           @Override
                                           public void afterTextChanged(Editable s) {

                                           }
                                       }
        );
        mdpView.addTextChangedListener(new TextWatcher() {
                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                           }

                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before, int count) {
                                               checkBoutton.setEnabled(true);
                                           }

                                           @Override
                                           public void afterTextChanged(Editable s) {

                                           }
                                       }
        );
    }
    private class threadServeur extends AsyncTask<String, String,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... strings) {
            contacteServeur(strings[0],strings[1],strings[2]);
            return null;
        }
    }
    private void contacteServeur(String n,String m,String test) {
        if (serveurSocket == null) {
            try {
                serveurSocket = new Socket(ipv4Adress, port);
            } catch (Exception e) {
                System.err.println("Erreur : " + e);
            }
            try {
                objectOutputClient = new ObjectOutputStream(serveurSocket.getOutputStream());
                objectInputClient = new ObjectInputStream(serveurSocket.getInputStream());
            } catch (IOException e) {
                System.err.println("Erreur : " + e);
            }
        }
        try {
            requestLoginInitiator = new RequestLoginInitiator();
            requestLoginInitiator.setId(BaseRequest.LOGIN_INITIATOR);
            requestLoginInitiator.setSaltChallenge(null);
            objectOutputClient.writeObject(requestLoginInitiator);
            objectOutputClient.flush();
        } catch (Exception e) {
            System.err.println("Erreur : " + e);
        }

        try {
            requestLoginInitiator = new RequestLoginInitiator();
            requestLoginInitiator = (RequestLoginInitiator) objectInputClient.readObject();
        } catch (Exception e) {
            System.err.println("Erreur lecture des données : " + e);
        }
        if(requestLoginInitiator.getStatus())
        {
            requestLogin = new RequestLogin();
            requestLogin.setId(BaseRequest.LOGIN_OTP);
            requestLogin.setError_msg(null);
            MessageDigest md = null;
            try{
                 md = MessageDigest.getInstance("SHA-256");
            }
            catch(Exception e)
            {
                System.err.println("Erreur : " + e);
            }
            Vector<String> component = new Vector<String>();
            component.add(requestLoginInitiator.getSaltChallenge());
            component.add(n);
            component.add(m);
            System.out.println("taille vector : " + component.size());
            requestLogin.CalculateDigest(md, component);
            requestLogin.setUsername(nom);
            try {
                objectOutputClient.writeObject(requestLogin);
                objectOutputClient.flush();
            } catch (Exception e) {
                System.err.println("Erreur lecture des données : " + e);
            }
            try {
                request = new BaseRequest();
                request = (BaseRequest) objectInputClient.readObject();
            } catch (Exception e) {
                System.err.println("Erreur lecture des données : " + e);
            }
        }
    }
    public static ObjectInputStream getInStream()
    {
        return objectInputClient;
    }
    public static ObjectOutputStream getOutStream()
    {
        return objectOutputClient;
    }
    public static Socket getServerSocket()
    {
        return serveurSocket;
    }
}
