package sk.tuke.earthexplorer;

import android.view.View;

public class SlideAnimation {
    static void slideUp(View view) {
        view.animate().translationY(-view.getHeight()).setDuration(500).setListener(null);
    }

    static void slideDown(View view) {
        view.animate().translationY(view.getHeight()).setDuration(500).setListener(null);
    }
}
