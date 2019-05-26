package com.geektech.notes.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

//Note class is our model we made is serializable to write it to database
@Entity
public class Note implements Serializable {

    //id will be auto generated
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String Title;
    private String Desc;

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
