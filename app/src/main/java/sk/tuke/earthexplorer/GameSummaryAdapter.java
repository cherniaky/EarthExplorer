package sk.tuke.earthexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameSummaryAdapter extends RecyclerView.Adapter<GameSummaryAdapter.ViewHolder> {
    private ArrayList<PlaceModel> dataList;

    GameSummaryAdapter(ArrayList<PlaceModel> dataList) {
        this.dataList = dataList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvRound;
        TextView tvDistance;
        TextView tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            tvRound = view.findViewById(R.id.tv_summary_round);
            tvDistance = view.findViewById(R.id.tv_summary_distance);
            tvScore = view.findViewById(R.id.tv_summary_score);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_summary_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int distance = dataList.get(position).distance;
        int score = dataList.get(position).score;
        holder.tvRound.setText(new Integer(position + 1).toString());
        holder.tvDistance.setText(distance + " miles");
        holder.tvScore.setText(score + " points");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
