package com.example.restaurantapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ItemDetailActivity : AppCompatActivity()
{
    private lateinit var imgItemSelected: ImageView
    private lateinit var tvNameSelected: TextView
    private lateinit var tvRatingSelected: TextView
    private lateinit var tvDescriptionSelected: TextView
    private lateinit var reservateNowButton: Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_detail)
        initComponents()
        initListeners()

        val imageUriString = intent.getStringExtra("ITEM_IMAGE_URI")
        val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null
        val name = intent.getStringExtra("ITEM_NAME")
        val description = intent.getStringExtra("ITEM_DESCRIPTION")
        val rating = intent.getStringExtra("ITEM_RATING")

        imgItemSelected.setImageURI(imageUri)
        tvNameSelected.text = name
        tvRatingSelected.text = rating
        tvDescriptionSelected.text = description
    }

    private fun initListeners()
    {
        reservateNowButton.setOnClickListener {
            val intent = Intent(this, PaymentConfirmationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents()
    {
        imgItemSelected = findViewById(R.id.img_item_selected)
        tvNameSelected = findViewById(R.id.tv_name_selected)
        tvRatingSelected = findViewById(R.id.tv_rating_selected)
        tvDescriptionSelected = findViewById(R.id.tv_description_selected)
        reservateNowButton = findViewById(R.id.button_reservate_now)
    }
}