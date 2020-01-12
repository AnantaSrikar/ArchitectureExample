package com.learning.dev.srikar.architectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText Title, Description;
    private NumberPicker Priority;

    public static final String EXTRA_TITLE = "com.learning.dev.srikar.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.learning.dev.srikar.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.learning.dev.srikar.architectureexample.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.learning.dev.srikar.architectureexample.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setupUIV();

        Priority.setMinValue(1);
        Priority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)){

            setTitle("Edit Note");
            Title.setText(intent.getStringExtra(EXTRA_TITLE));
            Description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            Priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }

        else{
            setTitle("Add Note");
        }


    }

    private void saveNote(){

        if (!(Title.getText().toString().isEmpty()||Description.getText().toString().isEmpty())){

            String title = Title.getText().toString().trim();
            String description = Description.getText().toString().trim();
            int priority = Priority.getValue();

            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_PRIORITY, priority);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);

            if (id != -1){
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);

            finish();

        }

        else{

            Toast.makeText(AddEditNoteActivity.this, "Enter both title and description", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_note: {
                saveNote();
                break;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void setupUIV() {

        Title = findViewById(R.id.edit_text_title);
        Description = findViewById(R.id.edit_text_description);
        Priority = findViewById(R.id.number_picker_priority);

    }
}
