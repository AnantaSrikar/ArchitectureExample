package com.learning.dev.srikar.architectureexample.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.dev.srikar.architectureexample.Data.Note;
import com.learning.dev.srikar.architectureexample.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.Title.setText(currentNote.getTitle());
        holder.Description.setText(currentNote.getDescription());
        holder.Priority.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void setNotes(List<Note> notes) {

        this.notes = notes;
        notifyDataSetChanged();

    }


    public Note getNoteAt(int position) {

        return notes.get(position);

    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView Title, Description, Priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.text_view_title);
            Description = itemView.findViewById(R.id.text_view_description);
            Priority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {

        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.listener = onItemClickListener;
    }


}
