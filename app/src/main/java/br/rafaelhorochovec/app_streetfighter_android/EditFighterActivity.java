package br.rafaelhorochovec.app_streetfighter_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.rafaelhorochovec.app_streetfighter_android.pojo.Fighter;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiClient;
import br.rafaelhorochovec.app_streetfighter_android.retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFighterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_edit_fighter);

        final EditText name = (EditText) findViewById(R.id.edtName);
        final EditText country = (EditText) findViewById(R.id.edtCountry);

        Intent intent = getIntent();
        final Integer id = intent.getIntExtra("ID", 0);

        ApiInterface apiService = ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Fighter> call = apiService.getFighterById(id);
        call.enqueue(new Callback<Fighter>() {
            @Override
            public void onResponse(Call<Fighter> call, Response<Fighter> response) {
                Fighter fighter = response.body();
                assert fighter != null;
                name.setText(fighter.getName());
                country.setText(fighter.getCountry());
            }

            @Override
            public void onFailure(Call<Fighter> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Falha na conexão.", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateBtn = (Button) findViewById(R.id.btnUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fighter fighter = new Fighter();
                fighter.setId(id);
                fighter.setName(name.getText().toString());
                fighter.setCountry(country.getText().toString());
                fighter.setThumbnailUrl("https://rlhorochovec.github.io/streetfighter/" + name.getText().toString().toLowerCase() + ".jpg");

                Call<Void> call = apiService.updateFighter(id, fighter);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getBaseContext(), "Alterado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Falha na conexão.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Button deleteBtn = (Button) findViewById(R.id.btnEdit);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = apiService.deleteFighter(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getBaseContext(), "Removido com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditFighterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Falha na conexão.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}