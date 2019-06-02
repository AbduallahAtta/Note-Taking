package elamien.abdullah.notetaking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import elamien.abdullah.notetaking.R;
import elamien.abdullah.notetaking.adapters.NotesAdapter;
import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.databinding.ActivityMainBinding;
import elamien.abdullah.notetaking.utils.Constants;
import elamien.abdullah.notetaking.viewmodel.NotesViewModel;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteClickListener {

    private NotesViewModel mNotesViewModel;
    private ActivityMainBinding mMainBinding;
    private NotesAdapter mNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainBinding.setHandlers(this);
        loadNotes();
        attachItemTouchHelper();
    }

    private void attachItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNotesViewModel.deleteNote(mNotesAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mMainBinding.notesRecyclerView);
    }

    private void loadNotes() {
        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        mNotesViewModel.getmNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                setNotes(notes);
            }
        });
    }

    private void setNotes(List<Note> notes) {
        loadAnimation();
        mNotesAdapter = new NotesAdapter(notes, this, this);
        mMainBinding.notesRecyclerView.setAdapter(mNotesAdapter);
    }

    private void loadAnimation() {
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.item_down_animator);
        mMainBinding.notesRecyclerView.setLayoutAnimation(layoutAnimationController);
    }

    public void onAddNoteButtonClick(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, Constants.NEW_NOTE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == Constants.NEW_NOTE_REQUEST_CODE) {
            saveNote(data);
        } else if (data != null && resultCode == RESULT_OK && requestCode == Constants.EDIT_NOTE_REQUEST_CODE) {
            updateNote(data);
        }
    }

    private void updateNote(Intent data) {
        String title = data.getStringExtra(Constants.NOTE_TITLE_EXTRA_KEY);
        String description = data.getStringExtra(Constants.NOTE_DESCRIPTION_EXTRA_KEY);
        int priority = data.getIntExtra(Constants.NOTE_PRIORITY_EXTRA_KEY, -1);
        int id = data.getIntExtra(Constants.NOTE_ID_EXTRA_KEY, -1);
        if (id == -1) {
            return;
        }

        Note note = new Note(title, description, priority);
        note.setId(id);
        mNotesViewModel.updateNote(note);
    }

    private void saveNote(Intent data) {
        String title = data.getStringExtra(Constants.NOTE_TITLE_EXTRA_KEY);
        String description = data.getStringExtra(Constants.NOTE_DESCRIPTION_EXTRA_KEY);
        int priority = data.getIntExtra(Constants.NOTE_PRIORITY_EXTRA_KEY, -1);
        Note note = new Note(title, description, priority);
        mNotesViewModel.insertNote(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotesMenuItem:
                mNotesViewModel.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onNoteClickListener(Note note) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra(Constants.NOTE_TITLE_EXTRA_KEY, note.getTitle());
        intent.putExtra(Constants.NOTE_DESCRIPTION_EXTRA_KEY, note.getDescription());
        intent.putExtra(Constants.NOTE_PRIORITY_EXTRA_KEY, note.getPriority());
        intent.putExtra(Constants.NOTE_ID_EXTRA_KEY, note.getId());
        startActivityForResult(intent, Constants.EDIT_NOTE_REQUEST_CODE);
    }
}
