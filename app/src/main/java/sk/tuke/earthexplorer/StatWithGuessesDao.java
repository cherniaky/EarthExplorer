package sk.tuke.earthexplorer;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StatWithGuessesDao {
    @Transaction
    @Query("SELECT * FROM ScoreStat")
    public List<StatWithGuesses> getStatsWithGuesses();

}
