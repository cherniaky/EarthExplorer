package sk.tuke.earthexplorer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ScoreStat {
    public ScoreStat() {
        setUser("");
        setDate("");
        setScore(0);
        setTime(0);
    }
    public ScoreStat(String user, String date, Integer score, Integer time) {
        setUser(user);
        setDate(date);
        setScore(score);
        setTime(time);
    }

    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo(name = "user")
    private String user;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "score")
    private Integer score;
    @ColumnInfo(name = "time")
    private Integer time;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time ) {
        this.time = time;
    }
}
