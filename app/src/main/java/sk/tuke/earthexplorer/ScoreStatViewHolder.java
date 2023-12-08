package sk.tuke.earthexplorer;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ScoreStatViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView tvUser;
    TextView tvDate;
    TextView tvScore;
    public ScoreStatViewHolder(View view) {
        super(view);

        this.view = itemView;
        tvUser = view.findViewById(R.id.tv_user);
        tvDate = view.findViewById(R.id.tv_date);
        tvScore = view.findViewById(R.id.tv_score);
    }
}
