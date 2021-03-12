package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class SecondActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    Button button,books,newbook,read;
    TextView textView;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        read = findViewById(R.id.read);
        books = findViewById(R.id.books);
        newbook = findViewById(R.id.button2);

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
                        textView.setText("SignedOut");
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
                Read(textView.getText().toString());
            }
        });

        books.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Books(textView.getText().toString());
            }
        });
        newbook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NewBook(textView.getText().toString());
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

            textView.setText(personId);
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