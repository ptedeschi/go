package br.com.tedeschi.diapersgo.rest;

import br.com.tedeschi.diapersgo.model.Deals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("venues/byCoordinate")
    Call<Deals> getTopRatedMovies(@Query("lat") double lat, @Query("lng") double lng);
}