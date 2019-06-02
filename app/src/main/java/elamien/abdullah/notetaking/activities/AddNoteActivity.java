package elamien.abdullah.notetaking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.databinding.DataBindingUtil;

import elamien.abdullah.notetaking.R;
import elamien.abdullah.notetaking.databinding.ActivityAddNoteBinding;
import elamien.abdullah.notetaking.utils.Constants;

public class AddNoteActivity extends AppCompatActivity {
    private ActivityAddNoteBinding mAddNoteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddNoteBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note);
        setupActionBar();
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            setTitle(getString(R.string.add_note_activity_label));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNoteMenuItem:
                addNote();
                return true;
            case android.R.id.home:
                NavigateBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void NavigateBack() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void addNote() {
        String title = mAddNoteBinding.noteTitleEditText.getText().toString();
        String description = mAddNoteBinding.noteDescriptionEditText.getText().toString();
        int priority = mAddNoteBinding.notePriorityPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "please type in something to save", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(Constants.NOTE_TITLE_EXTRA_KEY, title);
        data.putExtra(Constants.NOTE_DESCRIPTION_EXTRA_KEY, description);
        data.putExtra(Constants.NOTE_PRIORITY_EXTRA_KEY, priority);
        setResult(RESULT_OK, data);
        finish();
    }
}
