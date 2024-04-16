package br.rafaelhorochovec.app_streetfighter_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiClient;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {

    private EditText nameEdt, countryEdt;
    private Button saveBtn;
    private String thumbnailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create);

        nameEdt = findViewById(R.id.edt_name);
        countryEdt = findViewById(R.id.edt_country);
        saveBtn = findViewById(R.id.btn_submit);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbnailUrl = "https://rlhorochovec.github.io/streetfighter/"+ nameEdt.getText().toString().toLowerCase() +".jpg";
                postFighter(nameEdt.getText().toString(), countryEdt.getText().toString(), thumbnailUrl);
            }
        });
    }

    private void postFighter(String name, String country, String thumbnailUrl) {
        ApiInterface apiService = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Fighter fighter = new Fighter(name, country, thumbnailUrl);
        Call<Fighter> call = apiService.saveFighter(fighter);

        call.enqueue(new Callback<Fighter>() {
            @Override
            public void onResponse(Call<Fighter> call, Response<Fighter> response) {
                Toast.makeText(CreateActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Fighter> call, Throwable t) {
                Log.d("TAG","Response = "+ t);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}