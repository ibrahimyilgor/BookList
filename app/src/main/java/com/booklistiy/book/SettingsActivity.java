package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Context;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    String personId;
    ImageButton languageButton, deleteAccountButton, deleteDataButton, signOutButton;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        personId = getIntent().getStringExtra("personid");

        languageButton = findViewById(R.id.languagebutton);
        deleteAccountButton = findViewById(R.id.deleteaccount);
        deleteDataButton = findViewById(R.id.deletedata);
        signOutButton = findViewById(R.id.signoutbutton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLanguage();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.signoutbutton:
                        /*newbook.setEnabled(false);
                        books.setEnabled(false);
                        read.setEnabled(false);*/
                        //finish();
                        signOut();
                        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    // ...
                }
            }
        });
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SettingsActivity.this,"Signed Out Successfully.",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void changeLanguage() {
        Language lng = Language.getInstance();

        final String[] langlist= {"English","Türkçe"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(langlist, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    lng.setLang("en");

                    Locale locale;
                    locale = new Locale(lng.getLang());
                    Locale.setDefault(locale);
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());


                    recreate();
                }
                else if (i==1){
                    lng.setLang("tr");

                    Locale locale;
                    locale = new Locale(lng.getLang());
                    Locale.setDefault(locale);
                    Configuration config = getBaseContext().getResources().getConfiguration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());

                    recreate();
                }

                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}