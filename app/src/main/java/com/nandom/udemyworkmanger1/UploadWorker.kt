package com.nandom.udemyworkmanger1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = "UploadWorkerTAG"

    companion object {
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
        try{
//            Receiving the value from MainActivity
            val count = inputData.getInt(MainActivity.KEY_VALUE,0)
            for (i in 0 until count) {
                Log.i(TAG, "doWork: uploading $i")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())

            //Preparing the value to be sent back to the MainActivity
            val outputData = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()

            //Add the bundle outputData to the Result
            return Result.success(outputData)
        }catch (e: Exception) {
            return Result.failure()
        }
    }
}