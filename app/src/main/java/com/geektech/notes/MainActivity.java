package com.geektech.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.geektech.notes.room.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private List<Note> note = new ArrayList<>();
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().getDatabase().noteDao().getAll().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                note.clear();
                note.addAll(notes);
            }
        });
        recyclerView = findViewById(R.id.my_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NoteAdapter(note));
    }

    public void addNote(View view) {
        startActivity(new Intent(this, AddNotesActivity.class));
        overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Note> newNotes = new ArrayList<>();
        for (Note notes : note){
            if (notes.getTitle().contains(userInput)){
                newNotes.add(notes);
            }
        }
        adapter.updateList(newNotes);
        return true;
    }
}
