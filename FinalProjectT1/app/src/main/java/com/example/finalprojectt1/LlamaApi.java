package com.example.finalprojectt1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlamaApi {
    final String ApiURL = "https://nbjiloroafjerluzdbtw.supabase.co/rest/v1/";
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiURL).addConverterFactory(GsonConverterFactory.create()).build();
    JugadorApi jugadorApi= retrofit.create(JugadorApi.class);
}
