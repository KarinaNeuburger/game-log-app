package com.tep.gamelog.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
    private String[] scriptSQLCreate;
    private String scriptSQLDelete;

    /**
     * Cria uma instância de SQLiteHelper
     * @param context
     * @param nomeBanco
     * @param versaoBanco
     * @param scriptSQLCreate
     * @param scriptSQLDelete
     */

    public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate,
                        String scriptSQLDelete){
        super(context, nomeBanco, null, versaoBanco);
        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptSQLDelete = scriptSQLDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        int qtdeScripts = scriptSQLCreate.length;

        for (int i = 0; i < qtdeScripts; i++){

            String sql = scriptSQLCreate[i];

            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int vAntiga, int vNova) {

        db.execSQL(scriptSQLDelete);

        onCreate(db);
    }
}