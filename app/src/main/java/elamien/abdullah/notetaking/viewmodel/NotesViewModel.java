package elamien.abdullah.notetaking.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.repository.NoteRepository;

/**
 * Created by AbdullahAtta on 6/1/2019.
 */
public class NotesViewModel extends AndroidViewModel {

    private NoteRepository mNoteRepository;
    private LiveData<List<Note>> mNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        mNoteRepository = new NoteRepository(application);
        mNotes = mNoteRepository.getNotes();
    }

    public void insetNote(Note note) {
        mNoteRepository.insetNote(note);
    }

    public void updateNote(Note note) {
        mNoteRepository.updateNote(note);
    }

    public void deleteNote(Note note) {
        mNoteRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        mNoteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getmNotes() {
        return mNotes;
    }
}
