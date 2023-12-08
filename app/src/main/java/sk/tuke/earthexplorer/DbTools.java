package sk.tuke.earthexplorer;

import android.content.Context;

import androidx.room.Room;

import java.lang.ref.WeakReference;

public class DbTools {
    private static ScoreStatDatabase _db;

    public DbTools(WeakReference<Context> refContext) {
        getDbContext(refContext);
    }

    public static ScoreStatDatabase getDbContext(WeakReference<Context> refContext) {
        if (_db != null)
            return _db;
        return Room.databaseBuilder(refContext.get(), ScoreStatDatabase.class, "scoreStat-db").build();
    }
}
