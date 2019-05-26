package com.geektech.notes;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geektech.notes.room.Note;

import java.util.Calendar;

public class AddNotesActivity extends AppCompatActivity {

    TextView etTitle, etDes;
    long time;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        etTitle = findViewById(R.id.etTitle);
        etDes = findViewById(R.id.etDes);
        Button b = findViewById(R.id.btnSave);

        note = (Note) getIntent().getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etDes.setText(note.getDesc());
            b.setText("Обновить");
        } else b.setText("Сохранить");

    }

    public void onClickSave(View view) {
        String title = etTitle.getText().toString().trim();
        String desc = etDes.getText().toString().trim();
        if (time == 0) time = System.currentTimeMillis();
        if (note != null) {
            note.setTitle(title);
            note.setDesc(desc);
            App.getInstance().getDatabase().noteDao().update(note);
            finish();
            overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
        } else {
            Note note = new Note();
            note.setTitle(title);
            note.setDesc(desc);
            App.getInstance().getDatabase().noteDao().insert(note);
            finish();
            overridePendingTransition(R.anim.right_in_alpha, R.anim.left_out_alpha);
        }
    }
}
