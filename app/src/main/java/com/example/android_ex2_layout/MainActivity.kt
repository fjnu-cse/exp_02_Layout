package com.example.android_ex2_layout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.open_task1).setOnClickListener {
            startActivity(Intent(this, Task1Activity::class.java))
        }
        findViewById<Button>(R.id.open_task2).setOnClickListener {
            startActivity(Intent(this, Task2Activity::class.java))
        }
        findViewById<Button>(R.id.open_task3).setOnClickListener {
            startActivity(Intent(this, Task3Activity::class.java))
        }
        findViewById<Button>(R.id.open_task4).setOnClickListener {
            startActivity(Intent(this, Task4Activity::class.java))
        }
        findViewById<Button>(R.id.open_task5).setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }
    }
}