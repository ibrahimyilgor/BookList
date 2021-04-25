package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


public class SecondActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    ImageButton settings,newbook,books,read;
    TextView hellomsg,stringnewbook,stringbooks,stringread,quote;
    AdView ad;
    String id;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Language lng = Language.getInstance();
        Locale locale;
        locale = new Locale(lng.getLang());
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("“There is no friend as loyal as a book.” \n - Ernest Hemingway ");
        quotes.add("“A great book should leave you with many experiences, and slightly exhausted at the end. You live several lives while reading.” \n - William Styron");
        quotes.add("“When I have a little money, I buy books; and if I have any left, I buy food and clothes.”  \n - Desiderius Erasmus Roterodamus ");
        quotes.add("“Books are my friends, my companions. They make me laugh and cry and find meaning in life.”  \n -  Christopher Paolini ");
        quotes.add("“Books are like mirrors: if a fool looks in, you cannot expect a genius to look out.”  \n -  J.K. Rowling  ");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ad = findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-4943937138677405/2497004317");

        ad.loadAd(adRequest);

        stringnewbook = findViewById(R.id.stringnewbook);
        stringbooks = findViewById(R.id.stringbooks);
        stringread = findViewById(R.id.stringread);

        settings = (ImageButton) findViewById(R.id.settingsbutton);
        read = findViewById(R.id.read);
        books = findViewById(R.id.books);
        newbook = findViewById(R.id.button2);

        hellomsg = findViewById(R.id.textView);
        quote = findViewById(R.id.textView13);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        Random rand = new Random();
        int n = rand.nextInt(quotes.size());
        quote.setText(quotes.get(n));

        /*settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.settingsbutton:
                        newbook.setEnabled(false);
                        books.setEnabled(false);
                        read.setEnabled(false);
                        finish();
                        signOut();
                        break;
                    // ...
                }
            }
        });*/
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Settings(id);
            }
        });

        read.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Read(id);
            }
        });

        books.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Books(id);
            }
        });
        newbook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NewBook(id);
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            id=(personId);
            String mystring = getResources().getString(R.string.hi);
            hellomsg.setText(mystring + " "+personName);
        }


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SecondActivity.this,"Signed Out Successfully.",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Books(String personId) {
        Intent intent2 = new Intent(this,BooksActivity.class);
        intent2.putExtra("personid", personId);
        startActivityForResult(intent2,0);
    }

    private void Read(String personId) {
        Intent intent2 = new Intent(this,ReadActivity.class);
        intent2.putExtra("personid", personId);
        startActivityForResult(intent2,0);
    }

    private void NewBook(String personId) {
        Intent intent2 = new Intent(this,NewBookActivity.class);
        intent2.putExtra("personid", personId);
        startActivityForResult(intent2,0);
    }

    private void Settings(String personId) {
        Intent intent2 = new Intent(this,SettingsActivity.class);
        intent2.putExtra("personid", personId);
        startActivityForResult(intent2,0);
    }
}