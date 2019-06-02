package elamien.abdullah.notetaking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import elamien.abdullah.notetaking.R;
import elamien.abdullah.notetaking.database.Note;
import elamien.abdullah.notetaking.databinding.ListItemNoteBinding;

/**
 * Created by AbdullahAtta on 6/1/2019.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> mNotes;
    private Context mContext;

    public NotesAdapter(List<Note> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemNoteBinding binding = ListItemNoteBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new NotesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.bind(mNotes.get(position));
        switch (note.priority) {
            case 0:
            case 1:
            case 2:
            case 3:
                holder.dataBinding.listItemNotePriority.setBackground(ContextCompat.getDrawable(mContext, R.drawable.low_priority));
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                holder.dataBinding.listItemNotePriority.setBackground(ContextCompat.getDrawable(mContext, R.drawable.average_priority));
                break;
            default:
                holder.dataBinding.listItemNotePriority.setBackground(ContextCompat.getDrawable(mContext, R.drawable.high_priority));
                break;
        }
    }

    public Note getNote(int position) {
        return mNotes.get(position);
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private ListItemNoteBinding dataBinding;

        public NotesViewHolder(@NonNull ListItemNoteBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(Object note) {
            dataBinding.setNote((Note) note);
            dataBinding.executePendingBindings();
        }
    }
}
