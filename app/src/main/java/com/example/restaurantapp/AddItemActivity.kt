package com.example.restaurantapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.Manifest
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class AddItemActivity : AppCompatActivity()
{
    companion object {
        private const val IMAGE_PERMISSION_REQUEST_CODE = 101
    }

    private lateinit var itemName: TextInputEditText
    private lateinit var itemPrice: TextInputEditText
    private lateinit var itemDescription: TextInputEditText
    private lateinit var categoryRadioGroup: RadioGroup
    private lateinit var createButton: Button
    private lateinit var selectImageButton: Button
    private lateinit var itemImageView: ImageView

    private var itemImageUri: Uri? = null


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

            val resultIntent = Intent().apply {
                putExtra("ITEM_NAME", name)
                putExtra("ITEM_PRICE", price)
                putExtra("ITEM_DESCRIPTION", description)
                putExtra("ITEM_CATEGORY", category)
                itemImageUri?.let {
                    putExtra("ITEM_IMAGE_URI", it.toString())
                }
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        selectImageButton.setOnClickListener {
            if (hasImagePermission())
            {
                openImagePicker()
            } else
            {
                requestImagePermission()
            }
        }
    }

    private fun initComponents()
    {
        itemName = findViewById(R.id.et_item_name)
        itemPrice = findViewById(R.id.et_item_price)
        itemDescription = findViewById(R.id.et_item_description)
        categoryRadioGroup = findViewById(R.id.category_radio_group)
        createButton = findViewById(R.id.button_create)
        selectImageButton = findViewById(R.id.button_select_image)
        itemImageView = findViewById(R.id.imageView_selected_image)
    }

    private fun hasImagePermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestImagePermission()
    {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            IMAGE_PERMISSION_REQUEST_CODE
        )
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null)
            {
                itemImageUri = result.data!!.data
                itemImageView.setImageURI(itemImageUri)
            }
        }

    private fun openImagePicker()
    {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == IMAGE_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            openImagePicker()
        }
    }
}