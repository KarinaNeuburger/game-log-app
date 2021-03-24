package com.tep.gamelog.model;

/**
 * Created by Karina on 31/10/2018.
 */

public class GameSQLite {

    private String id;
    private String title;
    private String release;


    /*public FilmSQLite(String id, String title, String description, String director, String producer,
                      String release_date, String rt_score, byte[] image){

        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.producer = producer;
        this.release_date = release_date;
        this.rt_score = rt_score;
        this.image = image;

    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public String toString(){
        String str = this.id + "\n" + this.title + "\n" + this.release;
        return str;
    }
}
