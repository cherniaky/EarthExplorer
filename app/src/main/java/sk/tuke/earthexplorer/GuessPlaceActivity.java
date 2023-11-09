package sk.tuke.earthexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Set;
import java.util.TreeSet;

import sk.tuke.earthexplorer.databinding.ActivityGuessPlaceBinding;

public class GuessPlaceActivity extends AppCompatActivity {
    ActivityGuessPlaceBinding binding = null;
    View mapView;
    View scoreBoard;
    private StreetViewPanorama streetView;

    private LatLng correctPlace;
    private LatLng selectedPlace = null;
    private Set<LatLng> correctPlaceList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuessPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = findViewById(R.id.cl_mapView);
        scoreBoard = findViewById(R.id.ll_ScoreBoard);
        correctPlaceList = Constants.getFamousPlaceList();
        correctPlace = correctPlaceList.iterator().next();

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_StreetView);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {
                streetViewPanorama.setPosition(correctPlace);
                streetView = streetViewPanorama;
            }
        });


    }
}