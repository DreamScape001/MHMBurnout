package com.burnout.mhmburnout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.burnout.mhmburnout.R
import com.burnout.mhmburnout.database.Tasks

class RecyclerListAdapter(val context: Context, val task: List<Tasks>, private val onItemClicked: (Tasks) -> Unit) : RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>(){
    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById<TextView>(R.id.task)
        val button: Button = view.findViewById<Button>(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_layout, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = task[position].name
        holder.button.setOnClickListener {
            onItemClicked(task[position])
        }
    }

    override fun getItemCount(): Int {
        return task.size
    }

}