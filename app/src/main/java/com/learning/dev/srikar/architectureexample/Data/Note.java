package com.learning.dev.srikar.architectureexample.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    private String Title;

    private String Description;

    private int Priority;

    public Note() {
    }

    public Note(String title, String description, int priority) {

        Title = title;
        Description = description;
        Priority = priority;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }
}
