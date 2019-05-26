package com.geektech.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.geektech.notes.room.Note;

import java.util.ArrayList;
import java.util.List;

//all the comments were written by me at MolBulak minihackathon, (I hardly speak russian)
public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, NoteAdapter.OnNoteListener {

    private RecyclerView recyclerView;
    private List<Note> notes = new ArrayList<>();
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show onboard for the first time
        SharedPreferences sharedPreferences = getSharedPreferences("firstTime", MODE_PRIVATE);
        boolean First = sharedPreferences.getBoolean("First", true);
        if (First) {
            startActivity(new Intent(this, OnBoardActivity.class));
            //animation
            overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
            finish();
            return;
        }

        //setting adapter and recyclerview
        adapter = new NoteAdapter(notes, this);
        recyclerView = findViewById(R.id.my_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        notes.clear();
        notes.addAll(App.getInstance().getDatabase().noteDao().getAll());
        if (notes.isEmpty()) Toast.makeText(this, "Нет заметки", Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }

    //method onClick which adds new note
    public void addNote(View view) {
        startActivity(new Intent(this, AddNotesActivity.class));
        //animation
        overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
    }

    //searching notes from menu
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
        for (Note note : notes) {
            if (note.getTitle().contains(userInput)) {
                newNotes.add(note);
            }
        }
        adapter.updateList(newNotes);
        return true;
    }

    // on long click show alert
    @Override
    public void onNoteClick(int pos) {
        Note clickedNote = notes.get(pos);
        showAlert(clickedNote);
    }

    private void showAlert(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(note.getTitle());
        builder.setMessage("Вы хотите удалить это задание?");
        builder.setNeutralButton("Отменить", null);
        //opens add notes activity in editing mode
        builder.setNegativeButton("Изменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, AddNotesActivity.class);
                intent.putExtra("note", note);
                startActivity(intent);
                finish();
                //animation
                overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
            }
        });
        //delete note from database and from the list
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getInstance()
                        .getDatabase().noteDao().delete(note);
                notes.clear();
                notes.addAll(App.getInstance().getDatabase().noteDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
    }
}
