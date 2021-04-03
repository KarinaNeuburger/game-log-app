package com.tep.gamelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.tep.gamelog.model.GameDAO;
import com.tep.gamelog.model.GameSQLite;
import com.tep.gamelog.util.DBUtil;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<GameSQLite> adapter = null;
    private GameSQLite gameatual = null;
    private GameDAO dao;
    private List<GameSQLite> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        final DBUtil dbutil = DBUtil.getInstance(this.getApplicationContext()); // Cria instância do banco de dados
        dao = new GameDAO(dbutil.getDb()); // Atribui os métodos disponíveis na classe GameDAO para manipulação do banco de dados
        games = dao.lista(); // Alimenta a lista com os dados já existentes no banco de dados
        // Atribui ao adapter a lista de jogos e o layout do elemento da lista "list_row_layout"
        if (games != null){
            adapter = new ArrayAdapter<GameSQLite>(this, R.layout.list_row_layout, games);
            list.setAdapter(adapter); // Seta o adapter para manipular a classe ListView
        }
        // Seta a lista alimentada com os dados extraídos do banco de dados SQLite como clicável
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                gameatual = games.get(position);
                // Parâmetros de criação da caixa de diálogo para exclusão de registro com as
                // opções "SIM" para excluir e "NÃO" para cancelar a operação
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder .setTitle("Deletar gamelog")
                        .setMessage("Tem certeza que deseja deletar " + gameatual.getTitle() + "?");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dao.excluir(gameatual.getId()); // Exclui registro do banco de dados
                        games.remove(position); // Exclui registro do lista mostrada na tela
                        adapter.notifyDataSetChanged(); // Permite atualizar a tela ao excluir registro
                    }
                });
                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Ao clicar na opção "NÃO" não tem nenhuma ação para ser feita, apenas fecha a caixa de diálogo
                    }
                });
                builder.create();
                builder.show();
            }
        });
        // Ao clicar no botão adicionar chama a atividade de pesquisa SearchActivity
        FloatingActionButton addMoreButton = findViewById(R.id.add_more_button);
        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
        }
    }
