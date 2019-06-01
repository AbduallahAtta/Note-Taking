package elamien.abdullah.notetaking.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by AbdullahAtta on 6/1/2019.
 */

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase sDatabase;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateNotes(sDatabase).execute();
        }
    };

    public static synchronized NoteDatabase getInstance(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return sDatabase;
    }

    public abstract NoteDao noteDao();

    private static class PopulateNotes extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        public PopulateNotes(NoteDatabase database) {
            this.mNoteDao = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insertNote(new Note("Hello N1", "This is note no 1", 5));
            mNoteDao.insertNote(new Note("Hello N3", "This is note no 3", 8));
            mNoteDao.insertNote(new Note("Hello N4", "This is note no 4", 2));

            return null;
        }
    }
}
