package com.nandom.udemyworkmanger1

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DownloadingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = "UploadWorkerTAG"

    @SuppressLint("SimpleDateFormat")
    override fun doWork(): Result {
        try{
//            Receiving the value from MainActivity
            val count = inputData.getInt(MainActivity.KEY_VALUE,0)
            for (i in 0 until 3000) {
                Log.i(TAG, "doWork: downloading $i")
            }


            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())

            //Preparing the value to be sent back to the MainActivity
            val outputData = Data.Builder()
                .putString(UploadWorker.KEY_WORKER, currentDate)
                .build()

            Log.i(TAG, "doWork: Completed $currentDate")
            //Add the bundle outputData to the Result
            return Result.success()
        }catch (e: Exception) {
            return Result.failure()
        }
    }
}