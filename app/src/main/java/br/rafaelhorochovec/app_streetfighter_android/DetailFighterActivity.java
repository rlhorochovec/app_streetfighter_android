package br.rafaelhorochovec.app_streetfighter_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiClient;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFighterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_fighter);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("ID", 0);

        final TextView name = (TextView) findViewById(R.id.txtName);
        final TextView country = (TextView) findViewById(R.id.txtCountry);
        final ImageView thumbnailUrl = (ImageView) findViewById(R.id.imgThumbnailUrl);
        
        ApiInterface apiService = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Fighter> call = apiService.getFighterById(id);
        call.enqueue(new Callback<Fighter>() {
            @Override
            public void onResponse(Call<Fighter> call, Response<Fighter> response) {
                Fighter fighter = response.body();
                assert fighter != null;
                name.setText(fighter.getName());
                country.setText(fighter.getCountry());
                Glide.with(getApplicationContext()).load(fighter.getThumbnailUrl()).apply(RequestOptions.centerCropTransform()).into(thumbnailUrl);
            }

            @Override
            public void onFailure(Call<Fighter> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Falha na conexão.", Toast.LENGTH_SHORT).show();
            }
        });

        Button editBtn = (Button) findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailFighterActivity.this, EditFighterActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}