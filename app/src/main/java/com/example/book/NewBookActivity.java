package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class NewBookActivity extends AppCompatActivity {

    EditText bookname,bookauthor,bookpage,bookprice,bookdate;
    Button add;
    Book book;
    DatabaseReference reff;
    DatePicker dp;

    long maxid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        bookname = findViewById(R.id.bookname);
        bookauthor = findViewById(R.id.bookauthor);
        bookpage = findViewById(R.id.bookpage);
        bookprice = findViewById(R.id.bookprice);
        bookdate = findViewById(R.id.bookdate);
        dp = findViewById(R.id.dp2);

        bookname.setText("");
        bookauthor.setText("");
        bookpage.setText("");
        bookprice.setText("");
        bookdate.setText("");

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

                String datee= "";
                datee = String.valueOf(dp.getDayOfMonth())+"."+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                book.setDate(datee);

                reff.child(String.valueOf(maxid+1)).setValue(book);

                bookname.setText("");
                bookauthor.setText("");
                bookpage.setText("");
                bookprice.setText("");
                bookdate.setText("");
                Calendar now = Calendar.getInstance();
                dp.init(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),null);

                Toast.makeText(NewBookActivity.this, "Book added successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        Calendar now = Calendar.getInstance();
        dp.init(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),null);

        bookname.setText("");
        bookauthor.setText("");
        bookpage.setText("");
        bookprice.setText("");
        bookdate.setText("");
    }
}