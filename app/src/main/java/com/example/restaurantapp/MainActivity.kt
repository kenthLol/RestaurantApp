package com.example.restaurantapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.compose.material3.TextButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity()
{
    private lateinit var registerAccount: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val registerAccount = findViewById<Button>(R.id.registerAccountTextButton)

        registerAccount.setOnClickListener {
            val intent = Intent(this, activity_register_account::class.java)
            startActivity(intent)
        }
    }
}