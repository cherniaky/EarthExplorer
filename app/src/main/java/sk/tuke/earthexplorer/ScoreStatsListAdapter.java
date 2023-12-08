package sk.tuke.earthexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreStatsListAdapter extends RecyclerView.Adapter<ScoreStatViewHolder> {
    private ArrayList<ScoreStat> dataList;
//    private ScoreStatViewHolder holder;
//    private int position;

    ScoreStatsListAdapter(ArrayList<ScoreStat> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ScoreStatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_stats_list, parent, false);
        return new ScoreStatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreStatViewHolder holder, int position) {
        String user = dataList.get(position).getUser();
        String date = dataList.get(position).getDate();
        Integer score = dataList.get(position).getScore();

        holder.tvUser.setText(user);
        holder.tvDate.setText(date);
        holder.tvScore.setText(score.toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
