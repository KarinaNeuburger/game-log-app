package com.tep.gamelog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.tep.gamelog.model.GameDAO;
import com.tep.gamelog.model.GameSQLite;
import com.tep.gamelog.util.DBUtil;

import org.w3c.dom.Text;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<GameSQLite> adapter = null;
    private GameSQLite gameatual = null;
    private GameDAO dao;
    private List<GameSQLite> games;
    public Toast toast;

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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                gameatual = games.get(position);
                // Parâmetros de criação da caixa de diálogo para exclusão de registro com as
                // opções "SIM" para excluir e "NÃO" para cancelar a operação
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_delete, null);
                builder.setView(dialogView);
                TextView dialogText = (TextView) dialogView.findViewById(R.id.dialogText);
                dialogText.setText("Tem certeza que deseja deletar " + gameatual.getTitle() + "?");
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = (Button) dialogView.findViewById(R.id.positive_button);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dao.excluir(gameatual.getId()); // Exclui registro do banco de dados
                        games.remove(position); // Exclui registro do lista mostrada na tela
                        adapter.notifyDataSetChanged(); // Permite atualizar a tela ao excluir registro
                        dialog.dismiss();
                    }
                });
                Button negativeButton = (Button) dialogView.findViewById(R.id.negative_button);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Ao clicar na opção "NÃO" não tem nenhuma ação para ser feita, apenas fecha a caixa de diálogo
                        dialog.dismiss();
                    }
                });
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
    // Reescreve a ação do botão back nativo do android
    @Override
    public void onBackPressed(){
        toast.makeText(MainActivity.this, "não é possível retroceder", Toast.LENGTH_SHORT).show();
    }
}
