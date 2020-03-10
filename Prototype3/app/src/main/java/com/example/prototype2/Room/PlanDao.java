package com.example.prototype2.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM plan_table")
    List<Plan> getAll();

    @Query("SELECT * FROM plan_table WHERE id IN (:userIds)")
    List<Plan> loadAllByIds(int[] userIds);

    @Query("SELECT * from plan_table ORDER BY plan_name ASC")
    LiveData<List<Plan>> getAlphabetizedWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
//to insert one plan
    void insert(Plan plan);

    @Insert
    void insertAll(Plan plan);

    @Query("DELETE FROM plan_table")
    void deleteAll();

    @Delete
    void deletePlan(Plan plan);

    @Update
    void updatePlan(Plan plan);
}
