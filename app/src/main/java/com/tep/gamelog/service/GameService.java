package com.tep.gamelog.service;

import retrofit2.Call;
import retrofit2.http.*;

import com.tep.gamelog.model.Game;

import java.util.List;
// Interface usada pelo retrofit para realizar a busca na API
public interface GameService {
    @GET("/API-TEP/gamelog-app/games") // Complemento da url da busca
    Call<List<Game>> findGameTitle(@Query("title") String game); // Configuração da query
}
