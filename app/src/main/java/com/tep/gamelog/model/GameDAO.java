package com.tep.gamelog.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.tep.gamelog.util.GenericDAO;
// Classe que oferece os métodos para manipulação do banco de dados
public class GameDAO implements GenericDAO<GameSQLite> {
    private SQLiteDatabase db;

    public GameDAO(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public long inserir(GameSQLite objeto) {

        ContentValues values = new ContentValues();

        values.put("id", objeto.getId());
        values.put("title", objeto.getTitle());
        values.put("release", objeto.getRelease());

        Log.d("",objeto.getId());
        Log.d("",objeto.getTitle());
        Log.d("",objeto.getRelease());

        long id = db.insert("game", "", values);

        return id;
    }

    @Override
    public int excluir(String id) {

        int count = db.delete("game", "id=?", new String[]{String.valueOf(id)});

        return count;
    }

    @Override
    public List<GameSQLite> lista() {

        List<GameSQLite> games = null;

        try{
            Cursor c = db.query("game", new String[]{"id", "title", "release"}, null, null,null,
                    null, null);
            if (c.moveToFirst()){
                games = new ArrayList<GameSQLite>();
                do{
                    GameSQLite game = new GameSQLite();
                    game.setId(c.getString(0));
                    game.setTitle(c.getString(1));
                    game.setRelease(c.getString(2));
                    games.add(game);
                }while(c.moveToNext());
            }
        }catch(Exception e){
            return null;
        }
        return games;
    }
}
