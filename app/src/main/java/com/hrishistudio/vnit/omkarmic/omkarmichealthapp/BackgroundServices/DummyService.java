package com.hrishistudio.vnit.omkarmic.omkarmichealthapp.BackgroundServices;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class DummyService extends JobService {

    private static final String TAG = "OmkarmicDataSync";
    private boolean job_cancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {



        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
