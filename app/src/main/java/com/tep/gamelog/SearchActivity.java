package com.tep.gamelog;

//import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.tep.gamelog.model.Game;
import com.tep.gamelog.retrofit.RetrofitConfig;
import com.tep.gamelog.service.GameService;

import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private TextView resultText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.search_text);
        resultText = findViewById(R.id.search_result_text);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(searchText.getWindowToken(), 0);

                //final ProgressDialog progressDialog;
                //progressDialog = new ProgressDialog(SearchActivity.this);
                //progressDialog.setMax(100);
                //progressDialog.setMessage("Searching...");
                //progressDialog.setTitle("Game Log API v 1.0");
                //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                //progressDialog.show();
                
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false); // if you want user to wait for some process to finish,
                builder.setView(R.layout.layout_loading_dialog);
                AlertDialog dialog = builder.create();
                
                dialog.show(); // to show this dialog
                
                Log.d("Teste", "Teste");

                GameService service = RetrofitConfig.getRetrofitInstance().create(GameService.class);
                Call<List<Game>> call = service.listGames();
                call.enqueue(new Callback<List<Game>>() {
                //Call<List<Game>> call = new RetrofitConfig().getGameService().listGames();
                //call.enqueue(new Callback<List<Game>>() {
                    @Override
                    public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                        Log.d("onResponse", "Entrou no onResponse");
                        //progressDialog.dismiss();
                        dialog.dismiss(); // to hide this dialog
                        List<Game> gamelist = response.body();
                        Log.d("Teste", "GameList" + gamelist.toString());
                        if (!gamelist.isEmpty()) {

                            //Intent intent = new Intent(SearchActivity.this, FilmInfo.class);
                            Bundle parametros = new Bundle();

                            parametros.putString("id", gamelist.get(0).getId());
                            parametros.putString("titulo", gamelist.get(0).getTitle());
                            parametros.putInt("lançamento", gamelist.get(0).getRelease());

                            resultText.setText("Teste " + parametros +
                                    "\n" + gamelist.get(3).getId() +
                                    "\n" + gamelist.get(3).getTitle() +
                                    "\n" + gamelist.get(3).getRelease());

                            //intent.putExtras(parametros);
                            //startActivity(intent);

                        }else{
                            resultText.setText("Nenhum jogo encontrado");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Game>> call, Throwable t) {
                        //progressDialog.dismiss();
                        dialog.dismiss(); // to hide this dialog
                        resultText.setText("Erro ao buscar dados na API");
                        Log.e("GameService   ", "Erro ao buscar dados na API:" + t.getMessage());
                    }
                });
            }
        });
    }
}
