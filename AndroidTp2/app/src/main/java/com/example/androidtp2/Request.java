package com.example.androidtp2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import Protocol.BaseRequest;
import Protocol.RequestBigDataResult;
import Protocol.RequestDoBigData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class Request extends Activity {
    private CheckBox ACMButton, ANOVAButton, CAHButton, REG_CORRButton;
    private Button requestButton, smsButton, emailButton, retourMenuButton;
    private int traitement;
    private String itemOnListView,ligneList;
    private static ObjectOutputStream objectOutputClient = null;
    private static ObjectInputStream objectInputClient = null;
    private RequestDoBigData requestBigData = null;
    private RequestBigDataResult requestBigDataResult = null;
    private List<String> modeleList = new ArrayList<String>();
    private ArrayAdapter<String> controleurList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requete);
        controleurList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modeleList);
        ListView vueList = (ListView) findViewById(R.id.listTraitement);
        vueList.setAdapter(controleurList);
        vueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemOnListView = (String) parent.getItemAtPosition(position);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            ligneList = bundle.getString("liste");
            controleurList.add(ligneList);
        }
        ACMButton = (CheckBox) findViewById(R.id.ACMButton);
        ACMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ACMButton.isChecked()) {
                    requestButton.setEnabled(true);
                    traitement = 1;
                    ANOVAButton.setChecked(false);
                    CAHButton.setChecked(false);
                    REG_CORRButton.setChecked(false);
                }
                else
                {
                    requestButton.setEnabled(false);
                }
            }
        });
        ANOVAButton = (CheckBox) findViewById(R.id.AnovaButton);
        ANOVAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ANOVAButton.isChecked()) {
                    requestButton.setEnabled(true);
                    traitement = 3;
                    ACMButton.setChecked(false);
                    CAHButton.setChecked(false);
                    REG_CORRButton.setChecked(false);
                }
                else
                {
                    requestButton.setEnabled(false);
                }
            }
        });
        CAHButton = (CheckBox) findViewById(R.id.CAHButton);
        CAHButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CAHButton.isChecked()) {
                    requestButton.setEnabled(true);
                    traitement = 0;
                    ACMButton.setChecked(false);
                    ANOVAButton.setChecked(false);
                    REG_CORRButton.setChecked(false);
                }
                else
                {
                    requestButton.setEnabled(false);
                }
            }
        });
        REG_CORRButton = (CheckBox) findViewById(R.id.REGCORRButton);
        REG_CORRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (REG_CORRButton.isChecked()) {
                    requestButton.setEnabled(true);
                    traitement = 2;
                    ACMButton.setChecked(false);
                    ANOVAButton.setChecked(false);
                    CAHButton.setChecked(false);
                }
                else
                {
                    requestButton.setEnabled(false);
                }
            }
        });

        requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                threadServeur serv = new threadServeur();
                try {
                    serv.execute(traitement).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(requestBigDataResult.getStatus()) {
                    controleurList.clear();
                    smsButton.setEnabled(true);
                    emailButton.setEnabled(true);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                    String dateStr;
                    switch (requestBigData.getTypetraitement()) {
                        case 0:
                            dateStr = sdf.format(requestBigDataResult.getValue("CAH_DATE"));
                            ligneList = dateStr + " : " + requestBigDataResult.getValue("CAH_GLOBAL_TITRE");
                            controleurList.add(ligneList);
                            break;
                        case 1:
                            dateStr = sdf.format(requestBigDataResult.getValue("ACM_DATE"));
                            ligneList = dateStr + " : " + requestBigDataResult.getValue("ACM_GLOBAL_TITRE");
                            controleurList.add(ligneList);
                            break;
                        case 2:
                            dateStr = sdf.format(requestBigDataResult.getValue("REGCORR_DATE"));
                            ligneList = dateStr + " : " + requestBigDataResult.getValue("REGCORR_TITRE");
                            controleurList.add(ligneList);
                            break;
                        case 3:
                            dateStr = sdf.format(requestBigDataResult.getValue("ANOVA2_DATE"));
                            ligneList = dateStr + " : " + requestBigDataResult.getValue("ANOVA2_TITRE");
                            controleurList.add(ligneList);
                            break;
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "Pas de traitement retourné !", Toast.LENGTH_LONG).show();
            }
        });
        requestButton.setEnabled(false);
        smsButton = (Button) findViewById(R.id.SMSButton);
        smsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(itemOnListView != null) {
                    Intent smsActivity = new Intent(Request.this, SMS.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("item", itemOnListView);
                    smsActivity.putExtras(bundle);
                    startActivity(smsActivity);
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "Selectionner un traitement avant d'envoyer un sms !", Toast.LENGTH_LONG).show();
            }
        });
        smsButton.setEnabled(false);
        emailButton = (Button) findViewById(R.id.MailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    sendEmail();

                }
        });
        emailButton.setEnabled(false);
        retourMenuButton = (Button) findViewById(R.id.retourMenuButton);
        retourMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mailActivity = new Intent(Request.this, Menu.class);
                startActivity(mailActivity);
            }
        });
    }

    private class threadServeur extends AsyncTask<Integer, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            contacteServeur(ints[0]);
            return null;
        }
    }

    private void contacteServeur(int n) {
        objectInputClient = Login.getInStream();
        objectOutputClient = Login.getOutStream();
        try {
            requestBigData = new RequestDoBigData();
            requestBigData.setId(BaseRequest.BIG_DATA_RESULT);
            requestBigData.setTypetraitement(n);
            objectOutputClient.writeObject(requestBigData);
            objectOutputClient.flush();
        } catch (Exception e) {
            System.err.println("Erreur : " + e);
        }
        try {
            requestBigDataResult = new RequestBigDataResult();
            requestBigDataResult = (RequestBigDataResult) objectInputClient.readObject();
        } catch (Exception e) {
            System.err.println("Erreur lecture des données : " + e);
        }

    }
    protected void sendEmail() {
        Log.i("Send email", "");
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        File filelocation = new File(Request.this.getFilesDir(), "CAH_PLOT_ONE.png");
        Uri path = Uri.fromFile(filelocation);
        if(filelocation.exists())
        {
            filelocation.delete();
        }
        try {
            FileOutputStream fos=new FileOutputStream(filelocation);
            fos.write(((byte[]) requestBigDataResult.getValue("CAH_PLOT_ONE")));
            fos.close();
        }
        catch (Exception e) {
            Log.e("Creation_fichier", "Erreur création fichier", e);
        }
        /*InputStream is = new ByteArrayInputStream((byte[]) requestBigDataResult.getValue("CAH_PLOT_ONE"));
        Bitmap bmp = BitmapFactory.decodeStream(is);
        try {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(filelocation));
        }
        catch(Exception e)
        {
            Log.e("Compress", "Erreur compress byte array", e);
        }*/


        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");

        switch (requestBigData.getTypetraitement()) {
            case 0:
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, (String)requestBigDataResult.getValue("CAH_GLOBAL_TITRE"));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Description premier graphique : " + "\n" + (String)requestBigDataResult.getValue("CAH_PLOT_ONE_TEXT")+ "\n"
                        + "Description deuxieme graphique : "  + "\n" + (String)requestBigDataResult.getValue("CAH_PLOT_TWO_TEXT")+ "\n"
                        + "Conclusion CAH : "  + "\n" + (String)requestBigDataResult.getValue("CAH_GLOBAL_TEXT"));
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                break;
            case 1:
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, (String)requestBigDataResult.getValue("ACM_GLOBAL_TITRE"));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Description premier graphique : "  + "\n" + (String)requestBigDataResult.getValue("ACM_PLOT_ONE_TEXT")+ "\n"
                        +"Conclusion ACM : " + "\n" + (String)requestBigDataResult.getValue("ACM_GLOBAL_TEXT"));
                break;
            case 2:
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, (String)requestBigDataResult.getValue("REGCORR_GLOBAL_TITRE"));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Formule : " + "\n" + (String)requestBigDataResult.getValue("REGCORR_FORMULA")+ "\n"
                        + "Description premier graphique : "+ "\n"  + (String)requestBigDataResult.getValue("REGCORR_PLOT_ONE_TEXT")+ "\n"
                        + "Conclusion REGCORR : " + "\n" + (String)requestBigDataResult.getValue("REGCORR_GLOBAL_TEXT"));
                break;
            case 3:
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, (String)requestBigDataResult.getValue("ANOVA2_GLOBAL_TITRE"));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Description premier graphique : "  + "\n" + (String)requestBigDataResult.getValue("ANOVA2_PLOT_ONE_TEXT")+ "\n"
                        + "Conclusion ANOVA2 : " + "\n" + (String)requestBigDataResult.getValue("ANOVA2_GLOBAL_TEXT"));
                break;
        }
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Request.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

