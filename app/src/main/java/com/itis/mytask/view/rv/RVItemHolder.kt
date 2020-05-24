package com.itis.mytask.view.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.mytask.R
import com.itis.mytask.model.TaskDto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rec_item.*

class RVItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun bind(task: TaskDto) {
        el_title.text = task.title
        el_text.text = task.text
    }

    companion object {
        fun create(parent: ViewGroup) = RVItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rec_item,
                parent,
                false
            )
        )
    }
}