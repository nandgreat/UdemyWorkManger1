package com.nandom.udemyworkmanger1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CompressingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val TAG = "UploadWorkerTAG"

    override fun doWork(): Result {
        try{
//            Receiving the value from MainActivity
            for (i in 0 until 300) {
                Log.i(TAG, "doWork: compressing $i")
            }

            //Add the bundle outputData to the Result
            return Result.success()
        }catch (e: Exception) {
            return Result.failure()
        }
    }
}