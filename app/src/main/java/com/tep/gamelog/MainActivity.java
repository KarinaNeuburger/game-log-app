package com.tep.gamelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tep.gamelog.model.Game;
import com.tep.gamelog.model.GameDAO;
import com.tep.gamelog.model.GameSQLite;
import com.tep.gamelog.util.DBUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private TextView resultTextView;
    private Button searchButton;

    private ListView list;
    private ArrayAdapter<GameSQLite> adapter = null;
    private int pos = -1;

    private GameSQLite gameatual = null;
    private GameDAO dao;
    private List<GameSQLite> games;
    private boolean contemGame = false;
    private List<Game> gamelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        final DBUtil dbutil = DBUtil.getInstance(this.getApplicationContext());
        dao = new GameDAO(dbutil.getDb());
        games = dao.lista();

        if (games != null){

            adapter = new ArrayAdapter<GameSQLite>(this, android.R.layout.simple_list_item_1, games);
            list.setAdapter(adapter);

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode chamar a intent
                pos = position;

                Log.d("TESTE", "ITEM CLICADO na posicao [ " + position + " ]" + games.get(position));

                gameatual = games.get(position);

                dao.excluir(gameatual.getId());
                games = dao.lista();

                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Teste", "Clicou no botão do envelope");


                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);

            }
        });
        }
    }
