package sk.tuke.earthexplorer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private long start;

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
                googleMapClass.setCorrectPlace(correctPlace);
                onMapClick();

                zoomOnSlovensko();
            }
        });

        start = new Date().getTime();

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
                    googleMapClass.addCorrectMarker(correctPlace);
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

                    zoomOnSlovensko();
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

    private void zoomOnSlovensko() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(new LatLng(48.404669, 16.664393));
        builder.include(new LatLng(49.085405, 22.670359));
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        mGoogleMap.animateCamera(cameraUpdate);
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
        DbAddData addDataTask = new DbAddData();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        long now = new Date().getTime();
        addDataTask.execute(new ScoreStat(FirebaseAuth.getInstance().getCurrentUser().getEmail(), formattedDate, totalScore, (int) ((now - start) / 1000)));

        Intent intent = new Intent(GuessPlaceActivity.this, SummaryActivity.class);
        intent.putExtra("totalScore", totalScore);
        intent.putParcelableArrayListExtra("dataList", placeModelList);
        startActivity(intent);
    }

    class DbAddData extends AsyncTask<ScoreStat, Integer, Long> {

        @Override
        protected Long doInBackground(ScoreStat... scoreStat) {
            List<ScoreStat> list = new ArrayList<>();
            for (ScoreStat stat : scoreStat) {
                list.add(stat);
            }
            ScoreStatDatabase db = DbTools.getDbContext(new WeakReference<>(GuessPlaceActivity.this));
            return db.scoreStatDao().insertScoreStat(list.get(0));
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("scoreStats")
                    .document(id.toString())
                    .set(new ScoreStat(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            new Date().toString(), totalScore,
                            (int) ((new Date().getTime() - start) / 1000)));

            DbAddGuesses dbAddGuesses = new DbAddGuesses();
            dbAddGuesses.execute(id);
        }

    }

    class DbAddGuesses extends AsyncTask<Long, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Long... ids) {
            Long id = ids[0];

            ScoreStatDatabase db = DbTools.getDbContext(new WeakReference<>(GuessPlaceActivity.this));
            List<Guess> list = new ArrayList<>();
            for (PlaceModel placeModel : placeModelList) {
                list.add(new Guess(id,
                        (float) placeModel.correctPlace.latitude,
                        (float) placeModel.correctPlace.longitude,
                        (float) placeModel.guessedPlace.latitude,
                        (float) placeModel.guessedPlace.longitude,
                        placeModel.score, placeModel.distance));
            }
            Guess[] guessArray = list.toArray(new Guess[list.size()]);

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            for (Guess guess : guessArray) {
                firestore.collection("scoreStats")
                        .document(id.toString())
                        .collection("guesses")
                        .add(guess);
            }

            db.guessDao().insertGuesses(guessArray);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean id) {
            super.onPostExecute(id);

        }

    }
}