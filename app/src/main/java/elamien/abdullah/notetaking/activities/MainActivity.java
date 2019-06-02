package elamien.abdullah.notetaking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import elamien.abdullah.notetaking.R;
import elamien.abdullah.notetaking.adapters.NotesAdapter;
import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.databinding.ActivityMainBinding;
import elamien.abdullah.notetaking.utils.Constants;
import elamien.abdullah.notetaking.viewmodel.NotesViewModel;

public class MainActivity extends AppCompatActivity {

    private NotesViewModel mNotesViewModel;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setHandlers(this);
        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        mNotesViewModel.getmNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                setNotes(notes);
            }
        });
    }

    private void setNotes(List<Note> notes) {
        NotesAdapter adapter = new NotesAdapter(notes, this);
        mMainBinding.notesRecyclerView.setAdapter(adapter);
    }

    public void onAddNoteButtonClick(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE) {
            saveNote(data);
        }
    }

    private void saveNote(Intent data) {
        String title = data.getStringExtra(Constants.NOTE_TITLE_EXTRA_KEY);
        String description = data.getStringExtra(Constants.NOTE_DESCRIPTION_EXTRA_KEY);
        int priority = data.getIntExtra(Constants.NOTE_PRIORITY_EXTRA_KEY, -1);
        Note note = new Note(title, description, priority);
        mNotesViewModel.insertNote(note);
    }
}
