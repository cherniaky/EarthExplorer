package sk.tuke.earthexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import sk.tuke.earthexplorer.databinding.ActivityMiniGameBinding;

public class MiniGameActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private TextView timeTextView;
    private DrawView drawView;

    private int score;
    private long finish;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            updateTime();
            timerHandler.postDelayed(this, 1000);
        }
    };
    ActivityMiniGameBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMiniGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        scoreTextView = findViewById(R.id.score_text_view);
        timeTextView = findViewById(R.id.time_text_view);
        drawView = findViewById(R.id.draw_view);
        drawView.callback = new DrawView.UpdateCountCallback() {
            @Override
            public void update() {
                updateScore();
            }
        };

        score = 0;
        finish = System.currentTimeMillis() + 1000 * 10;

        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void updateScore() {

        score++;
        if (score >= 10) {
            finishMinigame(true);
        }
        scoreTextView.setText("Score: " + String.valueOf(score) + "/10");
    }

    void finishMinigame(Boolean result) {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void updateTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = finish - currentTime;
        if (elapsedTime >= 0) {
            timeTextView.setText("Time left: 00:" + String.format("%02d", elapsedTime / 1000));
            return;
        }
        finishMinigame(false);
        drawView.finished = true;
    }
}