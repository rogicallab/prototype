package com.example.prototype2.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlanRepository {
    private PlanDao mPlanDao;
    private LiveData<List<Plan>> mAllPlans;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PlanRepository(Application application) {
        PlanRoomDatabase db = PlanRoomDatabase.getDatabase(application);
        mPlanDao = db.wordDao();
        mAllPlans = mPlanDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Plan>> getAllPlans() {
        return mAllPlans;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Plan plan) {
        PlanRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPlanDao.insert(plan);
        });
    }

    private static class deletePlanAsyncTask extends AsyncTask<Plan, Void, Void> {
        private PlanDao mAsyncTaskDao;

        deletePlanAsyncTask(PlanDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Plan... params) {
            mAsyncTaskDao.deletePlan(params[0]);
            return null;
        }
    }
    public void deletePlan (Plan plan) {
        PlanRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPlanDao.deletePlan(plan);
        });

    }
    public void updatePlan(Plan plan){
        PlanRoomDatabase.databaseWriteExecutor.execute(()->{
            mPlanDao.updatePlan(plan);
        });
    }

}