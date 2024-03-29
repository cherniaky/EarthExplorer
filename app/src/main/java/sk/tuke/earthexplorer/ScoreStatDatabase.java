package sk.tuke.earthexplorer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ScoreStat.class, Guess.class}, version = 1)
public abstract class ScoreStatDatabase extends RoomDatabase {
    public abstract ScoreStatDao scoreStatDao();
    public abstract GuessDao guessDao();
    public abstract StatWithGuessesDao statWithGuessesDao();
}
