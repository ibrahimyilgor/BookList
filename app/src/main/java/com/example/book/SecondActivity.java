package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class SecondActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    ImageButton button,newbook,books,read;
    TextView hellomsg,stringnewbook,stringbooks,stringread,stringsignout;
    AdView ad;
    String id;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ad = findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        ad.loadAd(adRequest);

        stringnewbook = findViewById(R.id.stringnewbook);
        stringbooks = findViewById(R.id.stringbooks);
        stringread = findViewById(R.id.stringread);
        stringsignout = findViewById(R.id.stringsignout);

        button = findViewById(R.id.button);
        read = findViewById(R.id.read);
        books = findViewById(R.id.books);
        newbook = findViewById(R.id.button2);

        hellomsg = findViewById(R.id.textView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.button:
                        newbook.setEnabled(false);
                        books.setEnabled(false);
                        read.setEnabled(false);
                        finish();
                        signOut();
                        break;
                    // ...
                }
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
            hellomsg.setText("WELCOME TO BOOKLIST\n"+personName);
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
}