package br.rafaelhorochovec.app_streetfighter_android;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.rafaelhorochovec.app_streetfighter_android.adapter.FighterAdapter;
import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiClient;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private FighterAdapter fighterAdapter;
    private List<Fighter> fighterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fighterAdapter = new FighterAdapter();
        recyclerView.setAdapter(fighterAdapter);

        fighterList = new ArrayList<>();
        ApiInterface apiService = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Fighter>> call = apiService.getFighters();

        call.enqueue(new Callback<List<Fighter>>() {
            @Override
            public void onResponse(Call<List<Fighter>> call, Response<List<Fighter>> response) {
                fighterList = response.body();
                Log.d("TAG","Response = "+ fighterList);
                fighterAdapter.setFighterList(getApplicationContext(), fighterList);
            }

            @Override
            public void onFailure(Call<List<Fighter>> call, Throwable t) {
                Log.d("TAG","Response = "+ t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fighterAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                fighterAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}