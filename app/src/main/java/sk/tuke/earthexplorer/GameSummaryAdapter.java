package sk.tuke.earthexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameSummaryAdapter extends RecyclerView.Adapter<SummaryViewHolder> {
    private ArrayList<PlaceModel> dataList;

    GameSummaryAdapter(ArrayList<PlaceModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_summary_list, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        int distance = dataList.get(position).distance;
        int score = dataList.get(position).score;
        holder.tvRound.setText(new Integer(position + 1).toString());
        holder.tvDistance.setText(distance + " km");
        holder.tvScore.setText(score + " points");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
