package br.com.tedeschi.diapersgo.rest;

import br.com.tedeschi.diapersgo.model.Deals;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("venues/byCoordinate")
    Call<Deals> getTopRatedMovies(@Query("lat") double lat, @Query("lng") double lng);

    @GET("venues/insert")
    Call<Deals> insert(@Query("VenueFoursquareId") String VenueFoursquareId, @Query("VenueName") String VenueName, @Query("VenueAddress") String VenueAddress, @Query("VenueCity") String VenueCity, @Query("VenueState") String VenueState, @Query("VenueCountry") String VenueCountry, @Query("VenueLatitude") String VenueLatitude, @Query("VenueLongitude") String VenueLongitude, @Query("CustomerFirstName") String CustomerFirstName, @Query("CustomerLastName") String CustomerLastName, @Query("CustomerEmail") String CustomerEmail, @Query("CustomerCountry") String CustomerCountry, @Query("ProductName") String ProductName, @Query("ProductBrand") String ProductBrand, @Query("ProductModel") String ProductModel, @Query("ProductSize") String ProductSize, @Query("ProductQuantity") String ProductQuantity, @Query("DealPrice") String DealPrice, @Query("DealType") String DealType, @Query("DealComment") String DealComment);
}