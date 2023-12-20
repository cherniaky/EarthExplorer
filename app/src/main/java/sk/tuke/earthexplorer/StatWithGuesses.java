package sk.tuke.earthexplorer;

import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StatWithGuesses {
    @Embedded
    public ScoreStat scoreStat;
    @Relation(parentColumn = "Id", entityColumn = "stat_id")
    public List<Guess> guessList;
}
