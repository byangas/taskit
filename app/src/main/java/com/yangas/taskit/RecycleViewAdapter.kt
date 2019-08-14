package com.yangas.taskit

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yangas.taskit.models.Task

class RecycleViewAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<RecycleViewAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
       return TaskViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class TaskViewHolder(taskView: View): RecyclerView.ViewHolder(taskView) {
        private val textView:TextView = taskView.findViewById(R.id.title)
        fun bind(task:Task) {
            textView.text = task.title
        }
    }
}