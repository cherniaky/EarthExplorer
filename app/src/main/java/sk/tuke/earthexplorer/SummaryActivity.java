package sk.tuke.earthexplorer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sk.tuke.earthexplorer.databinding.ActivitySummaryBinding;

public class SummaryActivity extends AppCompatActivity {

    private ActivitySummaryBinding binding;
    private ArrayList<PlaceModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int totalScore = getIntent().getIntExtra("totalScore", 0);
        dataList = getIntent().getParcelableArrayListExtra("dataList");
        setAdapter(dataList);
        binding.tvFinalScore.setText(totalScore + " points");
        binding.tvFinalDistance.setText(getFinalScore(dataList) + " kilometers");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.summary_map_fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                GoogleMapClass googleMapClass = new GoogleMapClass(googleMap, SummaryActivity.this);

                for (PlaceModel place : dataList) {
                    googleMapClass.addBlueMarker(place.correctPlace);
                    googleMapClass.addRedMarker(place.guessedPlace);
                    googleMapClass.addPolyline(place.correctPlace, place.guessedPlace);
                }
            }
        });

        binding.btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SummaryActivity.this, GuessPlaceActivity.class));
                finish();
            }
        });

        binding.btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAdapter(ArrayList<PlaceModel> dataList) {
        RecyclerView recyclerView = findViewById(R.id.rv_game_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new GameSummaryAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }


    private int getFinalScore(ArrayList<PlaceModel> dataList) {
        int finalDistance = 0;
        for (PlaceModel i : dataList) {
            finalDistance += i.distance;
        }

        return finalDistance;
    }
}