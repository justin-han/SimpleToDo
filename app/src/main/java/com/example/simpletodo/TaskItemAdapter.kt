package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

/*
    A bridge that tells recyclerview how to display the data (list of strings we give it)
 */
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener : OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){
    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // stores references to elements in our layout view
        val textView : TextView = itemView.findViewById(android.R.id.text1)
        init {
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(contactView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get data model based on position
        val item = listOfItems[position]

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}