package com.example.prototype.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Plan.class}, version = 1, exportSchema = false)
public abstract class PlanRoomDatabase extends RoomDatabase {

    public abstract PlanDao wordDao();

    private static volatile PlanRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlanRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlanRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PlanRoomDatabase.class, "plan_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                PlanDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Plan plan = new Plan("トイレ");
                dao.insert(plan);
            });
        }
    };
}
