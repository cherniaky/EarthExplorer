package sk.tuke.earthexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import sk.tuke.earthexplorer.databinding.ActivityLeaderboardBinding;
import sk.tuke.earthexplorer.databinding.ActivityScoreStatBinding;

public class LeaderboardActivity extends AppCompatActivity {
    private ActivityLeaderboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference statsRef = db.collection("scoreStats");
        statsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> scoreStats = queryDocumentSnapshots.getDocuments();
            List<ScoreStat> scoreStatDataList = queryDocumentSnapshots.toObjects(ScoreStat.class);

            List<StatWithGuesses> statsWithGuesses = new ArrayList<>();

            for (int i = 0; i < scoreStats.size(); i++) {
                StatWithGuesses stat = new StatWithGuesses();
                DocumentSnapshot scoreStat = scoreStats.get(i);
                stat.scoreStat = scoreStatDataList.get(i);

                db.collection("scoreStats").document(scoreStat.getId())
                        .collection("guesses").get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                            List<Guess> guesses = queryDocumentSnapshots1.toObjects(Guess.class);
                            stat.guessList = guesses;
                            statsWithGuesses.add(stat);
                            setAdapter(statsWithGuesses);
                        });

            }

        });

    }

    private void setAdapter(List<StatWithGuesses> dataList) {
        RecyclerView recyclerView = findViewById(R.id.rv_stats_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new ScoreStatsListAdapter(new ArrayList<>(dataList), LeaderboardActivity.this);
        recyclerView.setAdapter(adapter);
    }
}