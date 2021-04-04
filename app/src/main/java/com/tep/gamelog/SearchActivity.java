package com.tep.gamelog;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.tep.gamelog.model.Game;
import com.tep.gamelog.service.RetrofitConfig;
import com.tep.gamelog.service.GameService;
import com.tep.gamelog.model.GameDAO;
import com.tep.gamelog.model.GameSQLite;
import com.tep.gamelog.util.DBUtil;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private TextView resultText;
    private SearchView searchView;
    public String queryText;
    public Toast toast;
    private GameSQLite gameatual = null;
    private GameDAO dao;
    private List<GameSQLite> games;
    private boolean contemGame = false;
    private List<Game> gamelist;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FloatingActionButton addButton = findViewById(R.id.add_button);
        FloatingActionButton backButton = findViewById(R.id.back_button);
        resultText = findViewById(R.id.search_result_text);
        searchView = findViewById(R.id.search_view);
        searchView.setSubmitButtonEnabled(true); // Mostra um botão para enviar uma query depois de terminar de escrever
        searchView.setIconifiedByDefault(false); // Mostra a barra de pesquisa aberta como padrão
        addButton.setEnabled(false); // Desabilita o botão para salvar enquanto o app não tiver um retorno da API

        final DBUtil dbutil = DBUtil.getInstance(this.getApplicationContext()); // Cria instância do banco de dados
        dao = new GameDAO(dbutil.getDb()); // Atribui os métodos disponíveis na classe GameDAO para manipulação do banco de dados
        games = dao.lista(); // Alimenta a lista com os dados já existentes no banco de dados
        // Cria um listener para receber os inputs do usuário
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // Envia a query ao clicar no botão enviar " > "
                queryText = query.toUpperCase(); // Converte o texto da query para uppercase devido aos dados da API estarem em uppercase
                // Cria uma janela de diálogo de pesquisa enquanto o app busca dos dados da API
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setCancelable(false); // Obriga o usuário aguardar o final da execução da busca
                builder.setView(R.layout.dialog_loading); // Seta o layout customizado da janela
                AlertDialog dialog = builder.create();
                dialog.show();
                // Inicio do processo de busca de dados na API com Retrofit
                GameService service = RetrofitConfig.getRetrofitInstance().create(GameService.class); // Cria o serviço de busca de
                // acordo com as configurações da classe RetrofitConfig e da interface GameService
                Call<List<Game>> call = service.findGameTitle(queryText); // Envia a query de pesquisa
                call.enqueue(new Callback<List<Game>>() {
                    @Override // Realizou a busca e obteve retorno possitivo
                    public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                        dialog.dismiss(); // Fecha a caixa de diálogo
                        gamelist = response.body(); // Recebe a resposta da API em formato JSON
                        if (!gamelist.isEmpty()) {
                            addButton.setEnabled(true); // Em caso de retorno positivo da API habilita o botão de adicionar registro
                            resultText.setText(gamelist.get(0).getTitle() + "\n" + gamelist.get(0).getRelease()); // Imprime na tela os dados do retorno
                        }else{
                            resultText.setText("Nenhum game encontrado! :( "); // Imprime na tela mensagem de retorno vazio
                            // não encontrou nenhum dada correspondente com a query enviada
                        }
                    }
                    @Override // Realizou a busca e não obteve retorno
                    public void onFailure(Call<List<Game>> call, Throwable t) {
                        dialog.dismiss();// Fecha a caixa de diálogo
                        resultText.setText("Erro ao buscar dados na API, tente novamente mais tarde! ;D "); // Mensagem de erro no retorno da API
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                games = dao.lista(); // A lista recebe os dados do banco
                contemGame = false; // Flag para fazer a comparação se o registro já existe no banco de dados
                if(gameatual == null){ gameatual = new GameSQLite(); } // Cria um objeto da classe GameSQLite
                // Usa os métodos set para alimentar o objeto com os dados retornados pela busca na API
                gameatual.setId(gamelist.get(0).getId());
                gameatual.setTitle(gamelist.get(0).getTitle());
                gameatual.setRelease("" + gamelist.get(0).getRelease());
                if(games != null){ // Varifica se a lista de games não está vazia
                    for (GameSQLite game:games){ // Corre a lista atual
                        if(game.getId().equals(gameatual.getId())){ // Verifica se o ID recebido já existe no banco de dados
                            contemGame = true; // Seta a flag para true em caso do registro já existir
                            toast.makeText(SearchActivity.this, "esse game já está logado XD ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if(gameatual.getId() != "" && contemGame == false) { // Verifica se o campo ID é difetente de nulo e se o flag contemGame é false
                    dao.inserir(gameatual); // Insere registro no banco de dados
                    toast.makeText(SearchActivity.this, "gamelog level up!!!! :D ", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(SearchActivity.this, MainActivity.class); // Retorna para a MainActivity após inserir
                startActivity(i);
            }
        });
        // Botão para retornar a MainActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    // Reescreve a ação do botão back nativo do android
    @Override
    public void onBackPressed(){
        Intent i = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(i);
    }
}

