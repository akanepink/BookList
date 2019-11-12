package com.example.booklist;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.booklist.BookListMainActivity;
import com.example.booklist.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    private BookListMainActivity.BookAdapter bookAdapter;

    public BookListFragment(BookListMainActivity.BookAdapter bookAdapter) {
        this.bookAdapter=bookAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewBooks=view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);
        this.registerForContextMenu(listViewBooks);

        return view;
    }

}
