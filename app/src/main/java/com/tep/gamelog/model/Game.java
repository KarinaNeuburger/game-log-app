package com.tep.gamelog.model;

public class Game {

    private String id;
    private String title;
    private int release;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public int getRelease(){
        return release;
    }
    public void setRelease(int release){
        this.release = release;
    }

    @Override
    public String toString(){
        return "\nTítulo: " + title + "\nLançamento: " + release;
    }
}
