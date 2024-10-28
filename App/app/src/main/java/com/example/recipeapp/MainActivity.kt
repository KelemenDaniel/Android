package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("Main", "onCreate() called!")
    }
    override fun onStart() {
        super.onStart()
        Log.d("Main", "onStart() called!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Main", "onDestroy() called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Main", "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Main", "onStop() called!")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Main", "onResume() called!")
    }
}

