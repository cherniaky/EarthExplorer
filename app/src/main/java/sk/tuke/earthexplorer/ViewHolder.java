package sk.tuke.earthexplorer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
