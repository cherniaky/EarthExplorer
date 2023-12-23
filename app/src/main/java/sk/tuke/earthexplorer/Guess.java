package sk.tuke.earthexplorer;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class Guess {
    public Guess() {
        setStatId(0);
        setCorrectLat(0);
        setCorrectLng(0);
        setGuessedLat(0);
        setGuessedLng(0);
        setScore(0);
        setDistance(0);
    }

    public Guess(long statId, float correctLat, float correctLng, float guessedLat, float guessedLng, int score, int distance) {
        setStatId(statId);
        setCorrectLat(correctLat);
        setCorrectLng(correctLng);
        setGuessedLat(guessedLat);
        setGuessedLng(guessedLng);
        setScore(score);
        setDistance(distance);
    }

    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo(name = "stat_id")
    private long statId;
    @ColumnInfo(name = "correctLat")
    private float correctLat;
    @ColumnInfo(name = "correctLng")
    private float correctLng;
    @ColumnInfo(name = "guessedLat")
    private float guessedLat;
    @ColumnInfo(name = "guessedLng")
    private float guessedLng;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name = "distance")
    private int distance;

    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public void setStatId(long statId) {
        this.statId = statId;
    }

    public long getStatId() {
        return statId;
    }


    public void setGuessedLat(float guessedLat) {
        this.guessedLat = guessedLat;
    }

    public float getGuessedLat() {
        return guessedLat;
    }

    public void setCorrectLat(float correctLat) {
        this.correctLat = correctLat;
    }

    public float getCorrectLat() {
        return correctLat;
    }

    public void setCorrectLng(float correctLng) {
        this.correctLng = correctLng;
    }

    public float getCorrectLng() {
        return correctLng;
    }

    public void setGuessedLng(float guessedLng) {
        this.guessedLng = guessedLng;
    }

    public float getGuessedLng() {
        return guessedLng;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
