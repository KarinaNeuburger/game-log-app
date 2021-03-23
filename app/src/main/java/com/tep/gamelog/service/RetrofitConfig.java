package com.tep.gamelog.service;


import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
            retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

            return retrofit;
    }
}
