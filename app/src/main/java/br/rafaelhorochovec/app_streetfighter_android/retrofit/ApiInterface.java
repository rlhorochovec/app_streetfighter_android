package br.rafaelhorochovec.app_streetfighter_android.retrofit;

import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/api/fighters")
    Call<List<Fighter>> getFighters();

    @POST("/api/fighters")
    Call<Fighter> saveFighter(@Body Fighter fighter);
}
