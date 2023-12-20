package sk.tuke.earthexplorer;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GuessDao {
    @Query("SELECT * FROM Guess")
    List<Guess> getAll();

    @Query("SELECT * FROM Guess WHERE Id LIKE :Id")
    Guess getById(int Id);

    @Insert
    void insertGuesses(Guess... guess);
}
