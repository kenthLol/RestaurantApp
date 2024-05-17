package com.example.restaurantapp

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class AddItemActivity : AppCompatActivity()
{
    private lateinit var itemName: TextInputEditText
    private lateinit var itemPrice: TextInputEditText
    private lateinit var itemDescription: TextInputEditText
    private lateinit var categoryRadioGroup : RadioGroup
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_item)
        initComponents()
    }

    private fun initComponents()
    {
        itemName = findViewById(R.id.et_item_name)
        itemPrice = findViewById(R.id.et_item_price)
        itemDescription = findViewById(R.id.et_item_description)
        categoryRadioGroup = findViewById(R.id.category_radio_group)
        createButton = findViewById(R.id.button_create)
    }
}