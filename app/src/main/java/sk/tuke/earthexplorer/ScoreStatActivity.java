package sk.tuke.earthexplorer;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import sk.tuke.earthexplorer.databinding.ActivityScoreStatBinding;


public class ScoreStatActivity extends AppCompatActivity {

    private ActivityScoreStatBinding binding;
    private ArrayList<ScoreStat> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScoreStatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DbGetData dbGetDataTask = new DbGetData();
        dbGetDataTask.execute();
    }

    private void setAdapter(List<StatWithGuesses> dataList) {
        RecyclerView recyclerView = findViewById(R.id.rv_stats_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter adapter = new ScoreStatsListAdapter(new ArrayList<>(dataList), ScoreStatActivity.this);
        recyclerView.setAdapter(adapter);
    }

    class DbGetData extends AsyncTask<Void, Integer, List<StatWithGuesses>> {

        @Override
        protected List<StatWithGuesses> doInBackground(Void... voids) {
            ScoreStatDatabase db = DbTools.getDbContext(new WeakReference<>(ScoreStatActivity.this));
            List<StatWithGuesses> data = db.statWithGuessesDao().getStatsWithGuesses();

            return data;
        }

        @Override
        protected void onPostExecute(List<StatWithGuesses> scoreStatList) {
            super.onPostExecute(scoreStatList);

            setAdapter(scoreStatList);
        }

    }
}