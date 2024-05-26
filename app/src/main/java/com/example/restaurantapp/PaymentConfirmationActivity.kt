package com.example.restaurantapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PaymentConfirmationActivity : AppCompatActivity()
{
    private lateinit var goHomeButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment_confirmation)

        initComponents()
        initListeners()

    }

    private fun initListeners()
    {
        goHomeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initComponents()
    {
        goHomeButton = findViewById(R.id.btn_go_home)
    }
}