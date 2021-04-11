package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    ListView listView;
    String personId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_books);

        personId = getIntent().getStringExtra("personid");
        listView = findViewById(R.id.listview);

        ArrayList<Book> booklist = new ArrayList<>();
        BookAdapter adapter = new BookAdapter(this,R.layout.list_item,booklist);
        listView.setAdapter(adapter);

        List<String> dynamic = new ArrayList<String>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booklist.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Book addn = new Book();
                    addn.setName(snapshot.child("name").getValue().toString());
                    addn.setAuthor(snapshot.child("author").getValue().toString());
                    addn.setPage(snapshot.child("page").getValue().toString());
                    addn.setPrice(snapshot.child("price").getValue().toString());
                    addn.setDate(snapshot.child("date").getValue().toString());
                    booklist.add(addn);
                    dynamic.add(snapshot.child("id").getValue().toString()+"~"+snapshot.child("name").getValue().toString() + "~"+snapshot.child("author").getValue().toString() + "~"+snapshot.child("page").getValue().toString() + "~"+snapshot.child("price").getValue().toString() + "~"+snapshot.child("date").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                Object listItem = listView.getItemAtPosition(position);
                // Toast.makeText(BooksActivity.this, String.valueOf(listItem), Toast.LENGTH_SHORT).show();
                Edit(dynamic.get(position));
            }
        });
    }

    private void Edit(String info){
        Intent intent = new Intent(this,EditActivity.class);
        intent.putExtra("info", info);
        intent.putExtra("personid",personId);
        startActivityForResult(intent,0);
    }
}