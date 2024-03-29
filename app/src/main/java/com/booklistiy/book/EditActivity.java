package com.booklistiy.book;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;


public class EditActivity extends AppCompatActivity {
    TextView t15,updatestring,deletestring;
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


        ad4 = findViewById(R.id.ad4);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-4943937138677405/2497004317");

        ad4.loadAd(adRequest);

        updatestring = findViewById(R.id.textView7);
        deletestring = findViewById(R.id.textView8);

        bookname = findViewById(R.id.bookname2);
        bookauthor = findViewById(R.id.bookauthor2);
        bookpage = findViewById(R.id.bookpage2);
        bookprice = findViewById(R.id.bookprice2);

        dp = findViewById(R.id.dp12);
        dp.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        Language lng = Language.getInstance();

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
                String updateinfo = String.format(getResources().getString(R.string.updateinfo),splitted[1]);
                builder.setTitle(updateinfo);
                builder.setMessage(getResources().getString(R.string.areyousureupdate));
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!book.getName().toString().contains("~") && !book.getAuthor().toString().contains("~")){
                            reff.child(splitted[0]).setValue(book);
                            Toast.makeText(EditActivity.this, getResources().getString(R.string.updatesuccess), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(EditActivity.this, getResources().getString(R.string.tildesignerror), Toast.LENGTH_SHORT).show();
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
                String deleteinfo = String.format(getResources().getString(R.string.deleteinfo),splitted[1]);
                builder.setTitle(deleteinfo);
                builder.setMessage(getResources().getString(R.string.areyousuredelete));
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reff2.setValue(null);
                        finish();
                        Toast.makeText(EditActivity.this, getResources().getString(R.string.deletesuccess), Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
}