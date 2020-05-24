package com.itis.mytask.view.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.itis.mytask.model.TaskDto

class RVAdapter(private val dataSrc: List<TaskDto>) : RecyclerView.Adapter<RVItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVItemHolder =
        RVItemHolder.create(parent)


    override fun getItemCount(): Int = dataSrc.size

    override fun onBindViewHolder(holder: RVItemHolder, position: Int) {
        holder.bind(dataSrc[position])
    }
}