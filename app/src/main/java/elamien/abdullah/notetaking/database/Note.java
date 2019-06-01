package elamien.abdullah.notetaking.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by AbdullahAtta on 6/1/2019.
 */
@Entity(tableName = "notes_table")
public class Note {
    public String title;
    public String description;
    public int priority;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }
}
