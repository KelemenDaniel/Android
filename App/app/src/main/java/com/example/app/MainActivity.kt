package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
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



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}

