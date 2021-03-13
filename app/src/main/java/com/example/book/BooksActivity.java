package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        listView = findViewById(R.id.listview);
        String personId = getIntent().getStringExtra("personid");

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        List<String> dynamic = new ArrayList<String>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Books");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add("Id: "+snapshot.child("id").getValue().toString()+" Name: "+snapshot.child("name").getValue().toString() + " Author: "+snapshot.child("author").getValue().toString() + " Page: "+snapshot.child("page").getValue().toString() + " Price: "+snapshot.child("price").getValue().toString() + " Date: "+snapshot.child("date").getValue().toString());
                    dynamic.add(snapshot.child("id").getValue().toString()+":"+snapshot.child("name").getValue().toString() + ":"+snapshot.child("author").getValue().toString() + ":"+snapshot.child("page").getValue().toString() + ":"+snapshot.child("price").getValue().toString() + ":"+snapshot.child("date").getValue().toString());
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
        startActivityForResult(intent,0);
    }
}