package elamien.abdullah.notetaking.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import elamien.abdullah.notetaking.R;
import elamien.abdullah.notetaking.adapters.NotesAdapter;
import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.databinding.ActivityMainBinding;
import elamien.abdullah.notetaking.viewmodel.NotesViewModel;

public class MainActivity extends AppCompatActivity {

    private NotesViewModel mNotesViewModel;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
}
