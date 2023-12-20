package sk.tuke.earthexplorer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreStatsListAdapter extends RecyclerView.Adapter<ScoreStatViewHolder> {
    private ArrayList<ScoreStat> dataList;
    private Context context;

    ScoreStatsListAdapter(ArrayList<ScoreStat> dataList, Context context) {
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
        String user = dataList.get(position).getUser();
        String date = dataList.get(position).getDate();
        Integer score = dataList.get(position).getScore();

        holder.tvUser.setText(user);
        holder.tvDate.setText(date);
        holder.tvScore.setText(score.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SummaryActivity.class);
                intent.putExtra("totalScore", score);
                intent.putParcelableArrayListExtra("dataList", new ArrayList<>());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
