package com.tep.gamelog.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tep.gamelog.helper.SQLiteHelper;
// Configurações da instância do banco de dados
public class DBUtil {

    private static DBUtil instance = null;
    // query para excluir banco, usado na alteração de versão
    private static String deleteSQL = "DROP TABLE IF EXISTS game";
    // query para criação do banco de dados
    private static String[] createSQL = new String[]{
            "CREATE TABLE IF NOT EXISTS game (id text not null," +
                    "title text not null," +
                    "release text not null);"};
    private static String nomeBanco = "gamelogv1";
    private static int versaoBanco = 1;
    private static SQLiteHelper dbHelper;
    private static SQLiteDatabase db;

    public DBUtil(){
    }
    // Cria a instância de acordo com os parâmetros enviados
    public static DBUtil getInstance(Context ctx){
        if (instance == null){
            dbHelper = new SQLiteHelper(ctx, nomeBanco, versaoBanco, createSQL, deleteSQL);
            db = dbHelper.getWritableDatabase();
            instance = new DBUtil();
        }
        return instance;
    }

    public static SQLiteDatabase getDb(){
        return db;
    }
}
