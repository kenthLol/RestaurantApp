package com.example.restaurantapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
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
        initListeners()
    }

    private fun initListeners()
    {
        createButton.setOnClickListener {
            val name = itemName.text.toString()
            val price = itemPrice.text.toString().toInt()
            val description = itemDescription.text.toString()
            val selectedCategoryId = categoryRadioGroup.checkedRadioButtonId
            val category = findViewById<RadioButton>(selectedCategoryId).text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("ITEM_NAME", name)
            resultIntent.putExtra("ITEM_PRICE", price)
            resultIntent.putExtra("ITEM_DESCRIPTION", description)
            resultIntent.putExtra("ITEM_CATEGORY", category)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
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