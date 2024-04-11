package br.rafaelhorochovec.app_streetfighter_android.retrofit;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import retrofit2.Call;
import retrofit2.http.GET;
public interface APIInterface {

    @GET("/lutadores")
    Call<Fighter> doGetListFighters();
}
