package com.example.prodiggy_ad_02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prodiggy_ad_02.ui.home.TodoListApp
import com.example.prodiggy_ad_02.ui.theme.PRODIGGY_AD_02Theme

class MainActivity : ComponentActivity() {
    companion object{
        const val TAG = "Rohit"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRODIGGY_AD_02Theme {
                TodoListApp()
            }
        }
    }
}

