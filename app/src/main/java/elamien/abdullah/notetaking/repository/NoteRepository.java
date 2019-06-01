package elamien.abdullah.notetaking.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.database.NoteDao;
import elamien.abdullah.notetaking.database.NoteDatabase;

/**
 * Created by AbdullahAtta on 6/1/2019.
 */
public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application.getApplicationContext());
        mNoteDao = database.noteDao();
        mNotes = mNoteDao.getAllNotes();
    }

    public void insetNote(Note note) {
        new InsertNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(mNoteDao).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(mNoteDao).execute();
    }

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDao;

        public InsertNoteAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDao;

        public DeleteNoteAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDao;

        public UpdateNoteAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao mNoteDao;

        public DeleteAllNotesAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }
}
