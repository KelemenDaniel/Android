package com.example.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme
import org.w3c.dom.Text

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Splash", "onCreate() called!")
    }
}

@Composable
fun TextAreaFunc() {
    Text(
        text = "Hello"
    )
}



@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    AppTheme {
        TextAreaFunc()
    }
}