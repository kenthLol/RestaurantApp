package com.example.restaurantapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditItemActivity : AppCompatActivity()
{
    private lateinit var itemName: TextInputEditText
    private lateinit var itemPrice: TextInputEditText
    private lateinit var itemDescription: TextInputEditText
    private lateinit var itemCategory: RadioGroup
    private lateinit var itemImage: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var itemImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_item)
        initComponents()
        initListeners()

        val imageUriString = intent.getStringExtra("ITEM_IMAGE_URI")
        val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null

        itemImage.setImageURI(imageUri)
        itemName.setText(intent.getStringExtra("ITEM_NAME"))
        itemPrice.setText(intent.getIntExtra("ITEM_PRICE", 0).toString())
        itemDescription.setText(intent.getStringExtra("ITEM_DESCRIPTION"))

        val category = intent.getStringExtra("ITEM_CATEGORY")
        when (category) {
            "Hamburguesa" -> itemCategory.check(R.id.category_hamburguesa)
            "Pizza" -> itemCategory.check(R.id.category_pizza)
            "Ensalada" -> itemCategory.check(R.id.category_ensalada)
            "Postre" -> itemCategory.check(R.id.category_postre)
            "Bebida" -> itemCategory.check(R.id.category_bebida)
        }

    }

    private fun initComponents()
    {
        itemName = findViewById(R.id.et_edit_item_name)
        itemPrice = findViewById(R.id.et_edit_item_price)
        itemDescription = findViewById(R.id.et_edit_item_description)
        itemCategory = findViewById(R.id.category_edit_radio_group)
        itemImage = findViewById(R.id.imageView_edit_selected_image)
        btnSave = findViewById(R.id.button_save)
        btnCancel = findViewById(R.id.button_cancel)
    }

    private fun initListeners()
    {
        btnSave.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("ORIGINAL_ITEM_NAME", intent.getStringExtra("ITEM_NAME"))
            resultIntent.putExtra("ITEM_NAME", itemName.text.toString())
            resultIntent.putExtra("ITEM_PRICE", itemPrice.text.toString().toInt())
            resultIntent.putExtra("ITEM_DESCRIPTION", itemDescription.text.toString())

            val selectedCategoryId = itemCategory.checkedRadioButtonId
            val selectedCategory = when (selectedCategoryId) {
                R.id.category_hamburguesa -> "Hamburguesa"
                R.id.category_pizza -> "Pizza"
                R.id.category_ensalada -> "Ensalada"
                R.id.category_postre -> "Postre"
                R.id.category_bebida -> "Bebida"
                else -> ""
            }

            resultIntent.putExtra("ITEM_CATEGORY", selectedCategory)
            resultIntent.putExtra("ITEM_IMAGE_URI", itemImageUri.toString())

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

}