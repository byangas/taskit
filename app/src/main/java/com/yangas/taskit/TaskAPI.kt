package com.yangas.taskit

import com.yangas.taskit.models.Task
import io.reactivex.Observable
import retrofit2.http.GET


interface TaskAPI {
    @GET("api/v1/task")
    fun getTasks() : Observable<List<Task>>
}