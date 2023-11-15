package sk.tuke.earthexplorer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import sk.tuke.earthexplorer.databinding.ActivityGuessPlaceBinding;

public class GuessPlaceActivity extends AppCompatActivity {
    ActivityGuessPlaceBinding binding = null;
    View mapView;
    View scoreBoard;
    private StreetViewPanorama streetView;
    private GoogleMap mGoogleMap;
    private GoogleMapClass googleMapClass;
    private int round = 1;

    private LatLng correctPlace;
    private LatLng selectedPlace = null;
    private List<LatLng> correctPlaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuessPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = findViewById(R.id.cl_mapView);
        scoreBoard = findViewById(R.id.ll_ScoreBoard);
        correctPlaceList = FamousPlacesData.getFamousPlacesList();
        correctPlace = correctPlaceList.get(0);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_StreetView);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {
                streetViewPanorama.setPosition(correctPlace);
                streetView = streetViewPanorama;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mGoogleMap = googleMap;
                googleMapClass = new GoogleMapClass(googleMap, GuessPlaceActivity.this);
                onMapClick();
            }
        });

        binding.fbOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessPlaceActivity.this.slideUp(mapView);
            }
        });
        binding.closeMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessPlaceActivity.this.slideDown(mapView);
            }
        });

        binding.markPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPlace != null) {
                    googleMapClass.addBlueMarker(correctPlace);
                    googleMapClass.addPolyline(correctPlace, selectedPlace);
                    googleMapClass.zoomOnMap();
                    mGoogleMap.setOnMapClickListener(null);
                    setTotalScore();
                    showScoreBoard();
                    setPlaceModel();
                    selectedPlace = null;
                }
                if (round == 5) {
                    binding.btnNextRound.setText("View Summary");
                }
            }
        });

        binding.btnNextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (round < 5) {
                    round++;
                    binding.tvRound.setText(round + "/5");
                    correctPlace = correctPlaceList.get(round - 1);
                    googleMapClass.setCorrectPlace(correctPlace);
                    streetView.setPosition(correctPlace);
                    GuessPlaceActivity.this.slideUp(scoreBoard);
                    GuessPlaceActivity.this.slideDown(mapView);
                    mGoogleMap.clear();
                    binding.markPlaceButton.setVisibility(View.GONE);
                    onMapClick();
                    googleMapClass.zoomOnMap(new LatLng(0.0, 0.0), 1f);
                } else {
                    endGame();
                }
            }
        });

        binding.fbHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMapClass.addCircle();
                binding.fbHintButton.setVisibility(View.GONE);
            }
        });

        binding.fbUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuessPlaceActivity.this, MiniGameActivity.class);
                startActivityForResult(intent, 69);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 69) {
            if (resultCode == Activity.RESULT_OK) {
                boolean result = data.getBooleanExtra("result", false);
                if (result) {
                    binding.fbHintButton.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    private void slideUp(View view) {
        view.animate().translationY(-view.getHeight()).setDuration(500);
    }

    private void slideDown(View view) {
        view.animate().translationY(view.getHeight()).setDuration(500);
    }

    private void onMapClick() {
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                selectedPlace = latLng;
                googleMapClass.setSelectedPlace(latLng);
                googleMapClass.setCorrectPlace(correctPlace);
                googleMapClass.addSelectedPlaceMarker(latLng);

                binding.markPlaceButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showScoreBoard() {
        binding.tvScore.setText("You got " + getScore() + " points");
        binding.tvDistance.setText("You are " + googleMapClass.getDistance() + " kilometers away");
        binding.pbScore.setProgress(getScore());
        this.slideDown(scoreBoard);
    }

    private int getScore() {
        int distance = googleMapClass.getDistance();
        return Math.max(0, 5000 - 2 * distance);
    }

    private int totalScore = 0;

    private void setTotalScore() {
        totalScore += getScore();
        binding.tvFinalScore.setText(new Integer(totalScore).toString());
    }

    private ArrayList<PlaceModel> placeModelList = new ArrayList<>(5);


    private void setPlaceModel() {
        PlaceModel place = new PlaceModel(
                correctPlace,
                selectedPlace,
                getScore(),
                googleMapClass.getDistance()
        );

        placeModelList.add(place);
    }

    private void endGame() {
        Intent intent = new Intent(GuessPlaceActivity.this, SummaryActivity.class);
        intent.putExtra("totalScore", totalScore);
        intent.putParcelableArrayListExtra("dataList", placeModelList);
        startActivity(intent);
//        finish();
    }
}