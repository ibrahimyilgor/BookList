package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class NewBookActivity extends AppCompatActivity {

    EditText bookname,bookauthor,bookpage,bookprice;
    ImageButton add;
    Book book;
    DatabaseReference reff;
    DatePicker dp;
    TextView text;
    AdView ad3;

    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        bookname = findViewById(R.id.bookname);
        bookauthor = findViewById(R.id.bookauthor);
        bookpage = findViewById(R.id.bookpage);
        bookprice = findViewById(R.id.bookprice);
        dp = findViewById(R.id.dp2);
        dp.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        text = findViewById(R.id.textView6);

        ad3 = findViewById(R.id.ad3);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-4943937138677405/2497004317");

        ad3.loadAd(adRequest);

        bookname.setText("");
        bookauthor.setText("");
        bookpage.setText("");
        bookprice.setText("");

        add = findViewById(R.id.add);

        book = new Book();

        String personId = getIntent().getStringExtra("personid");

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Books");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                book.setName(bookname.getText().toString());
                book.setAuthor(bookauthor.getText().toString());
                book.setPage(bookpage.getText().toString());
                book.setPrice(bookprice.getText().toString());
                book.setId(String.valueOf(maxid+1));

                String datee= "";
                String d;
                if(dp.getDayOfMonth()<10) {
                    d = "0"+String.valueOf(dp.getDayOfMonth());
                }
                else{
                    d = String.valueOf(dp.getDayOfMonth());
                }

                if(dp.getMonth()+1<10) {
                    datee = d + ".0" + String.valueOf(dp.getMonth() + 1) + "." + String.valueOf(dp.getYear());
                }
                else{
                    datee = d + "." + String.valueOf(dp.getMonth() + 1) + "." + String.valueOf(dp.getYear());
                }
                book.setDate(datee);

                if(!book.getName().toString().contains("~") && !book.getAuthor().toString().contains("~")){
                    reff.child(String.valueOf(maxid+1)).setValue(book);
                    Toast.makeText(NewBookActivity.this, "Book added successfully.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewBookActivity.this, "Character '~' is not allowed.", Toast.LENGTH_SHORT).show();
                }

                reff.child(String.valueOf(maxid+1)).setValue(book);

                bookname.setText("");
                bookauthor.setText("");
                bookpage.setText("");
                bookprice.setText("");
                Calendar now = Calendar.getInstance();
                dp.init(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),null);

            }
        });

        Calendar now = Calendar.getInstance();
        dp.init(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),null);

        bookname.setText("");
        bookauthor.setText("");
        bookpage.setText("");
        bookprice.setText("");
    }
}