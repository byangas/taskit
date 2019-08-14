package com.yangas.taskit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var subscriber: Disposable? = null
    private var taskListView: RecyclerView? = null
    private val BASE_URL = "https://5d5233bd3432e70014e6b6b3.mockapi.io/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskListView = findViewById(R.id.tasklist)
        //arg! Not sure why I have to set this, or why it can't be set in XML??
        taskListView?.layoutManager = LinearLayoutManager(this)
    }

    fun btnClickRxImplementation(view: View) {

        val mockServer = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(TaskAPI::class.java)

        subscriber = mockServer.getTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, "Received ${it.size.toString()} item(s)", Toast.LENGTH_LONG).show()
                taskListView?.adapter = RecycleViewAdapter(it)
            },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriber?.dispose()
    }
}
