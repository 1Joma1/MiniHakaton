package com.geektech.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geektech.notes.room.Note;

import java.util.ArrayList;
import java.util.List;

//Adapter with onLongClickListener X)
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> notes;
    OnNoteListener onNoteListener;

    public NoteAdapter(List<Note> notes, OnNoteListener onNoteListener) {
        this.notes = notes;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_value, viewGroup, false);
        return new ViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(notes.get(i));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView tvTitle, tvDesc;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);

            this.onNoteListener = onNoteListener;
            itemView.setOnLongClickListener(this);
        }

        public void bind(Note note) {
            tvTitle.setText(note.getTitle());
            tvDesc.setText(note.getDesc());

        }

        @Override
        public boolean onLongClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
            return true;
        }
    }

    //updates the list when searching
    public void updateList(List<Note> newNotes){
        notes = new ArrayList<>();
        notes.addAll(newNotes);
        notifyDataSetChanged();
    }

    //interface for onNoteClick
    public interface OnNoteListener{
        void onNoteClick(int pos);
    }
}
