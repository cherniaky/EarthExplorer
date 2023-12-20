package sk.tuke.earthexplorer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreStatDao {
    @Query("SELECT * FROM ScoreStat")
    List<ScoreStat> getAll();

//    @Query("DELETE FROM ScoreStat")
//    void removeAll();

    @Query("SELECT * FROM ScoreStat WHERE Id LIKE :Id")
    ScoreStat getById(int Id);

    @Insert
    long insertScoreStat(ScoreStat scoreStats);
}
