package br.rafaelhorochovec.app_streetfighter_android.retrofit;

import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("/api/fighters")
    Call<Fighter> saveFighter(@Body Fighter fighter);

    @GET("/api/fighters")
    Call<List<Fighter>> getFighters();

    @GET("/api/fighters/{id}")
    Call<Fighter> getFighterById(@Path("id") Integer id);

    @PUT("/api/fighters/{id}")
    Call<Void> updateFighter(@Path("id") Integer id, @Body Fighter fighter);

    @DELETE("/api/fighters/{id}")
    Call<Void> removeFighter(@Path("id") Integer id);
}
