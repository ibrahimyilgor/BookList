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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        deleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle( getResources().getString(R.string.deletedata));
                builder.setMessage(getResources().getString(R.string.areyousuredeletedata));
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId);
                        reff.setValue(null);
                        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.deletedatasuccess), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle( getResources().getString(R.string.deleteacc));
                builder.setMessage(getResources().getString(R.string.areyousuredeleteacc));
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId);
                        reff.setValue(null);
                        final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.deleteaccountsuccess), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
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
                        String signedout = getResources().getString(R.string.signedout);
                        Toast.makeText(SettingsActivity.this,signedout,Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void changeLanguage() {
        Language lng = Language.getInstance();

        final String[] langlist= {"English","Türkçe"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        String selectlang = getResources().getString(R.string.selectlang);
        mBuilder.setTitle(selectlang);
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