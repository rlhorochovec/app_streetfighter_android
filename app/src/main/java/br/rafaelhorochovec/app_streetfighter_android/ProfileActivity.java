package br.rafaelhorochovec.app_streetfighter_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("thumbnailUrl") && getIntent().hasExtra("name") && getIntent().hasExtra("country")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");
            String name = getIntent().getStringExtra("name");
            String country = getIntent().getStringExtra("country");

            setImage(thumbnailUrl, name, country);
        }
    }

    private void setImage(String thumbnailUrl, String name, String country){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView fighter = findViewById(R.id.name);
        TextView origin = findViewById(R.id.country);
        fighter.setText(name);
        origin.setText(country);

        ImageView image = findViewById(R.id.thumbnailUrl);
        Glide.with(this)
                .asBitmap()
                .load(thumbnailUrl)
                .into(image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}