package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var adapter : TaskItemAdapter
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove the item from the list
                listOfTasks.removeAt(position)

                // notify the adapter of the change
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set up button to be able to enter a task
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener {
            // grab the text the user has input @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // then add the string to listOfTasks
            listOfTasks.add(userInputtedTask)

            // notify the data adapter
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // clear the text field
            inputTextField.setText("")

            saveItems()
        }
    }

    // save the data the user has inputted
    // save data by writing and reading from a file


    // get the file we need
    fun getDataFile() : File {

        return File(filesDir, "data.txt")
    }

    // load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}

