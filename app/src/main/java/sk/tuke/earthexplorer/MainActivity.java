package sk.tuke.earthexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiTools {
    private static final String mBaseApiUrl = "https://type.fit/api/";
    private static Retrofit mRetrofitInstance;

    private static Retrofit createRetrofitApiInstance() {
        if (mRetrofitInstance == null)
            mRetrofitInstance = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(mBaseApiUrl).build();
        return mRetrofitInstance;
    }

    public static IApiDefinition getApi() {
        return createRetrofitApiInstance().create(IApiDefinition.class);
    }
}

class JsonTools {
    public static String parseToQuote(JsonArray quoteJsonArray) {
        Random rand = new Random();
        int n = rand.nextInt(quoteJsonArray.size());
        JsonObject data = quoteJsonArray.get(n).getAsJsonObject();
        String author = data.get("author").getAsString();
        return "\"" + data.get("text").getAsString() + "\" By: " + author.substring(0, author.length() - 10);
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessButton = (Button) findViewById(R.id.guess_the_place_btn);
        Button statsButton = (Button) findViewById(R.id.my_stats_btn);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GuessPlaceActivity.class));
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScoreStatActivity.class));
            }
        });

        Call<JsonArray> data = ApiTools.getApi().getQuotes();
        data.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.code() == 200) {
                    String quote = JsonTools.parseToQuote(response.body());

                    ((TextView) findViewById(R.id.random_motivation_quote)).setText(quote);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}