package sk.tuke.earthexplorer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ScoreStatsListAdapter extends RecyclerView.Adapter<ScoreStatViewHolder> {
    private ArrayList<StatWithGuesses> dataList;
    private Context context;

    ScoreStatsListAdapter(ArrayList<StatWithGuesses> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoreStatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_stats_list, parent, false);
        return new ScoreStatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreStatViewHolder holder, int position) {
        String user = dataList.get(position).scoreStat.getUser();
        String date = dataList.get(position).scoreStat.getDate();
        Integer score = dataList.get(position).scoreStat.getScore();

        holder.tvUser.setText(user);
        holder.tvDate.setText(date);
        holder.tvScore.setText(score.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SummaryActivity.class);
                intent.putExtra("totalScore", score);

                List<Guess> guessList = dataList.get(position).guessList;
                ArrayList<PlaceModel> placeModels = new ArrayList<>();
                for (Guess guess : guessList) {
                    placeModels.add(new PlaceModel(
                            new LatLng(guess.getCorrectLat(), guess.getCorrectLng()),
                            new LatLng(guess.getGuessedLat(), guess.getGuessedLng()),
                            guess.getScore(),
                            guess.getDistance()));
                }
                intent.putParcelableArrayListExtra("dataList", placeModels);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
