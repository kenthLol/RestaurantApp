package com.example.restaurantapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditItemActivity : AppCompatActivity()
{
    companion object
    {
        private const val IMAGE_PERMISSION_REQUEST_CODE = 101
    }

    private lateinit var itemName: TextInputEditText
    private lateinit var itemPrice: TextInputEditText
    private lateinit var itemDescription: TextInputEditText
    private lateinit var itemCategory: RadioGroup
    private lateinit var itemImage: ImageView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var btnSelectImage: Button

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
        when (category)
        {
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
        btnSelectImage = findViewById(R.id.button_edit_select_image)
    }

    private fun initListeners()
    {
        btnSave.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("ORIGINAL_ITEM_NAME", intent.getStringExtra("ITEM_NAME"))
                putExtra("ITEM_NAME", itemName.text.toString())
                putExtra("ITEM_PRICE", itemPrice.text.toString().toInt())
                putExtra("ITEM_DESCRIPTION", itemDescription.text.toString())

                val selectedCategoryId = itemCategory.checkedRadioButtonId
                val selectedCategory = when (selectedCategoryId)
                {
                    R.id.category_hamburguesa -> "Hamburguesa"
                    R.id.category_pizza -> "Pizza"
                    R.id.category_ensalada -> "Ensalada"
                    R.id.category_postre -> "Postre"
                    R.id.category_bebida -> "Bebida"
                    else -> ""
                }
                putExtra("ITEM_CATEGORY", selectedCategory)

                // Only include the image URI if a new image has been selected
                if (itemImageUri != null) {
                    putExtra("ITEM_IMAGE_URI", itemImageUri.toString())
                } else {
                    putExtra("ITEM_IMAGE_URI", intent.getStringExtra("ITEM_IMAGE_URI"))
                }
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        btnSelectImage.setOnClickListener {
            if (hasImagePermission())
            {
                openImagePicker()
            } else
            {
                requestImagePermission()
            }
        }
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
                itemImage.setImageURI(itemImageUri)
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