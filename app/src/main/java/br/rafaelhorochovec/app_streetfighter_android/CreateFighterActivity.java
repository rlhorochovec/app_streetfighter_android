package br.rafaelhorochovec.app_streetfighter_android;

import android.app.ProgressDialog;
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

public class CreateFighterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_create_fighter);

        final EditText name = (EditText) findViewById(R.id.edtName);
        final EditText country = (EditText) findViewById(R.id.edtCountry);
        
        Button saveBtn = (Button) findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fighter fighter = new Fighter();
                fighter.setName(name.getText().toString());
                fighter.setCountry(country.getText().toString());
                fighter.setThumbnailUrl("https://rlhorochovec.github.io/streetfighter/" + name.getText().toString().toLowerCase() + ".jpg");

                ApiInterface apiService = ApiClient.getRetrofitInstance().create(ApiInterface.class);
                Call<Fighter> call = apiService.saveFighter(fighter);
                call.enqueue(new Callback<Fighter>() {
                    @Override
                    public void onResponse(Call<Fighter> call, Response<Fighter> response) {
                        Toast.makeText(getBaseContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Fighter> call, Throwable t) {
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