package com.example.book;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    private ArrayList<Book> books;
    private int resource;

    public BookAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Book> objects) {
        super(context, resource, objects);
        this.resource=resource;
        this.books=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        LayoutInflater layoutinflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutinflater.inflate(resource,null);

        TextView tv8 = (TextView)row.findViewById(R.id.textView8);
        TextView tv9 = (TextView)row.findViewById(R.id.textView9);
        TextView tv10 = (TextView)row.findViewById(R.id.textView10);
        TextView tv11 = (TextView)row.findViewById(R.id.textView11);
        TextView tv12 = (TextView)row.findViewById(R.id.textView12);

        Book book = books.get(position);

        tv8.setText(book.getName());
        tv9.setText(book.getAuthor());
        tv10.setText(book.getPage());
        tv11.setText(book.getPrice());
        tv12.setText(book.getDate());

        return row;

    }
}
