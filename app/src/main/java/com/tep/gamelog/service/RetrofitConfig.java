package com.tep.gamelog.service;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
// Confuguração do Retrofit
public class RetrofitConfig {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
            retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com") // Url base para utilização na pesquisa
                .addConverterFactory(JacksonConverterFactory.create()) // Conversor do retorno para JSON
                .build();
            return retrofit;
    }
}
