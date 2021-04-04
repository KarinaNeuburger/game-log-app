package com.tep.gamelog.model;
// Cria a classe que vai manipular o objeto dentro do banco de dados
public class GameSQLite {

    private String id;
    private String title;
    private String release;

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
        String str = this.title + "\n" + this.release;
        return str;
    }
}
