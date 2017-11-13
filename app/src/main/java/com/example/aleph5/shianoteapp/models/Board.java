package com.example.aleph5.shianoteapp.models;

import com.example.aleph5.shianoteapp.extraclasses.AwakeApplication;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Board extends RealmObject {

    @PrimaryKey
    private  int id;
    @Required
    private String shortInfo;
    @Required
    private Date createdAt;

    private RealmList<Note> notes;

    public  Board(){

    }

    public Board(String shortInfo){
        this.id = 0;
        this.shortInfo = shortInfo;
        this.createdAt = new Date();
        this.notes = new RealmList<Note>();
    }

    public int getId() {
        return id;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public RealmList<Note> getNotes() {
        return notes;
    }
}
