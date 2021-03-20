package com.example.book;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditActivity extends AppCompatActivity {
    TextView t11,t12,t13,t14,t15,updatestring,deletestring;
    EditText bookname,bookauthor,bookpage,bookprice;
    DatePicker dp;
    ImageButton update,delete;
    AdView ad4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String personId = getIntent().getStringExtra("personid");
        String info = getIntent().getStringExtra("info");

        Book book;
        book = new Book();

        t11 = findViewById(R.id.t11);
        t12 = findViewById(R.id.t12);
        t13 = findViewById(R.id.t13);
        t14 = findViewById(R.id.t14);
        t15 = findViewById(R.id.t15);

        ad4 = findViewById(R.id.ad4);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        updatestring = findViewById(R.id.textView7);
        deletestring = findViewById(R.id.textView8);

        bookname = findViewById(R.id.bookname2);
        bookauthor = findViewById(R.id.bookauthor2);
        bookpage = findViewById(R.id.bookpage2);
        bookprice = findViewById(R.id.bookprice2);

        dp = findViewById(R.id.dp12);
        dp.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);


        String splitted[] =info.split("~");
        bookname.setText(splitted[1]);
        bookauthor.setText(splitted[2]);
        bookpage.setText(splitted[3]);
        bookprice.setText(splitted[4]);

        String datesplitted[] =splitted[5].split("\\.");

        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(datesplitted[2]),Integer.parseInt(datesplitted[1])-1,Integer.parseInt(datesplitted[0]));

        dp.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),null);

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseReference reff;
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Books");

                book.setId(splitted[0]);
                book.setName(bookname.getText().toString());
                book.setAuthor(bookauthor.getText().toString());
                book.setPage(bookpage.getText().toString());
                book.setPrice(bookprice.getText().toString());

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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setTitle("Book named "+splitted[1]+ " will be updated.");
                builder.setMessage("Are you sure you want to update the book?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!book.getName().toString().contains("~") && !book.getAuthor().toString().contains("~")){
                            reff.child(splitted[0]).setValue(book);
                            Toast.makeText(EditActivity.this, "Book updated successfully.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(EditActivity.this, "Character '~' is not allowed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Books").child(splitted[0]);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setTitle("Book named "+splitted[1]+ " will be deleted.");
                builder.setMessage("Are you sure you want to delete the book?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reff2.setValue(null);
                        finish();
                        Toast.makeText(EditActivity.this, "Book deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
}