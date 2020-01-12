package com.learning.dev.srikar.architectureexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.dev.srikar.architectureexample.Adapter.NoteAdapter;
import com.learning.dev.srikar.architectureexample.Data.Note;
import com.learning.dev.srikar.architectureexample.Data.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    private FloatingActionButton floatingActionButton;

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                noteAdapter.setNotes(notes);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getID());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);

            noteViewModel.insert(note);

            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_LONG).show();
        }

        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){

            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            if (id == -1){

                Toast.makeText(MainActivity.this, "Note can't be updated", Toast.LENGTH_LONG).show();
                return;
            }

            else {

                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                Note note = new Note(title, description, priority);
                note.setID(id);
                noteViewModel.update(note);

                Toast.makeText(MainActivity.this, "Note updated", Toast.LENGTH_LONG).show();

            }

        }

        else{

            Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.delete_all_notes :{
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this, "All notes deleted", Toast.LENGTH_LONG).show();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}