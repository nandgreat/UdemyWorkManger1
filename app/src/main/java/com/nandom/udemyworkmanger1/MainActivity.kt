package com.nandom.udemyworkmanger1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_VALUE = "key_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            setOneTimeWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest(){

        val workManager = WorkManager.getInstance(applicationContext)

        val data: Data = Data.Builder()
            .putInt(KEY_VALUE, 225)
            .build()

        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            //Send value data to the worker class
            .setInputData(data)
            .build()

        val downloadRequest = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
            .build()

        val filteringWorker = OneTimeWorkRequest.Builder(FilteringWorker::class.java)
            .setConstraints(constraints)
            //Send value data to the worker class
            .build()

        val compressingWorker = OneTimeWorkRequest.Builder(CompressingWorker::class.java)
            .setConstraints(constraints)
            //Send value data to the worker class
            .build()

        val parallelWorker = mutableListOf<OneTimeWorkRequest>()
        parallelWorker.add(downloadRequest)
        parallelWorker.add(filteringWorker)


        workManager
            .beginWith(parallelWorker)
            .then(compressingWorker)
            .then(uploadRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                textView.text = it.state.name

                //cHECK
                if(it.state.isFinished){
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORKER)
                    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                }
            })
    }
}