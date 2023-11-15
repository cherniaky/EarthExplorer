package sk.tuke.earthexplorer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class DrawView extends View {

    private int pointX;
    private int pointY;
    private int radius = 90;
    private int color;
    public boolean finished = false;

    public interface UpdateCountCallback {
        public void update();
    }

    public UpdateCountCallback callback = new UpdateCountCallback() {
        @Override
        public void update() {
        }
    };

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        reinitialize();
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawCircle(pointX, pointY, radius, paint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int event = motionEvent.getAction();
        if ((event == MotionEvent.ACTION_DOWN || event == MotionEvent.ACTION_MOVE) && !finished) {
            float touchX = motionEvent.getX();
            float touchY = motionEvent.getY();

            if (isIntersecting(touchX, touchY)) {
                reinitialize();
            }

            postInvalidate();
            return true;
        }
        return false;
    }

    public void reinitialize() {
        Random random = new Random();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        pointX = random.nextInt(width - radius) + radius / 2;
        pointY = random.nextInt(height - radius) + radius / 2;

        radius = Math.round(radius * 0.9F);
        color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

        callback.update();
    }

    public boolean isIntersecting(Float x, Float y) {
        return (Math.abs(x - pointX) < radius) && (Math.abs(y - pointY) < radius);
    }


}
