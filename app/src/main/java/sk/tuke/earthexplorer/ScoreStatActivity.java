package sk.tuke.earthexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import sk.tuke.earthexplorer.databinding.ActivityScoreStatBinding;


public class ScoreStatActivity extends AppCompatActivity {

    private ActivityScoreStatBinding binding;
    private ArrayList<PlaceModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScoreStatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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