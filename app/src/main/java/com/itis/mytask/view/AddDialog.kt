package com.itis.mytask.view

import android.app.Dialog
import android.app.TaskStackBuilder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.itis.mytask.R
import com.itis.mytask.model.TaskDto
import com.itis.mytask.repository.RepositoryEmulator
import kotlinx.android.synthetic.main.dialog_add_task.*

class AddDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle("Add")
        val v = inflater.inflate(R.layout.dialog_add_task, null)
        return v
    }

    override fun onStart() {
        super.onStart()
        btn_add_task.setOnClickListener {
            val dto = TaskDto(et_title.text.toString(), et_text.text.toString())
            RepositoryEmulator.add(dto)
            dismiss()

        }
    }


}