package com.tep.gamelog.service;

import retrofit2.Call;
import retrofit2.http.*;

import com.tep.gamelog.model.Game;

import java.util.List;

public interface GameService {
    @GET("/KarinaNeuburger/game-log-app/games?title=")
    Call<List<Game>> listGames(@Path("title") String title);
}
