package com.example.restaurantapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.adapters.HomeCategorieAdapter
import com.example.restaurantapp.adapters.HomeItemAdapter
import com.example.restaurantapp.models.CategorieModel
import com.example.restaurantapp.models.ItemModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity()
{
    companion object
    {
        const val ADD_ITEM_REQUEST_CODE = 1
        const val EDIT_ITEM_REQUEST_CODE = 2
    }

    private val categories = listOf(
        CategorieModel(R.drawable.ic_hamburguer, "Hamburguesa"),
        CategorieModel(R.drawable.ic_pizza, "Pizza"),
        CategorieModel(R.drawable.ic_salad, "Ensalada"),
        CategorieModel(R.drawable.ic_postre, "Postre"),
        CategorieModel(R.drawable.ic_drink, "Bebida"),
    )

    private lateinit var homeHorizontalRecyclerView: RecyclerView
    private lateinit var homeCategoriesAdapter: HomeCategorieAdapter

    /*
    * Vertical
    * */

    private lateinit var homeVerticalRecyclerView: RecyclerView
    private lateinit var homeItemAdapter: HomeItemAdapter
    private lateinit var filteredItems: List<ItemModel>

    private var items = mutableListOf(
        ItemModel(
            Uri.parse("android.resource://com.example.restaurantapp/drawable/burguer_cheese"),
            "Hamburguesa de res",
            150,
            "Hamburguesa de res con lechuga, jitomate, cebolla y queso",
            "Hamburguesa",
            "5.0"
        ),
        ItemModel(
            Uri.parse("android.resource://com.example.restaurantapp/drawable/ic_pizza"),
            "Pizza de peperoni",
            200,
            "Pizza de peperoni con queso mozzarella",
            "Pizza",
            "5.0"
        ),
        ItemModel(
            Uri.parse("android.resource://com.example.restaurantapp/drawable/ic_salad"),
            "Ensalada César",
            100,
            "Ensalada César con pollo, lechuga, jitomate, crutones y aderezo",
            "Ensalada",
            "5.0"
        ),
        ItemModel(
            Uri.parse("android.resource://com.example.restaurantapp/drawable/ic_postre"),
            "Pastel de chocolate",
            80,
            "Pastel de chocolate con fresas y crema batida",
            "Postre",
            "5.0"
        ),
        ItemModel(
            Uri.parse("android.resource://com.example.restaurantapp/drawable/ic_drink"),
            "Agua de horchata",
            20,
            "Agua de horchata con hielo",
            "Bebida",
            "5.0"
        ),
    )

    private var selectedCategoryIndex = -1
    private lateinit var fabAddItem: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initListeners()
    {
        fabAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE)
        }
    }

    private fun initUI()
    {
        homeCategoriesAdapter =
            HomeCategorieAdapter(categories, selectedCategoryIndex) { category, position ->
                selectedCategoryIndex = position
                filterItemsByCategory(category.title)
                homeCategoriesAdapter.selectedCategoryIndex = position
                homeCategoriesAdapter.notifyDataSetChanged() // Notify adapter to refresh the UI
            }
        homeHorizontalRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        homeHorizontalRecyclerView.adapter = homeCategoriesAdapter

        filteredItems = items // Initially show all items
        homeItemAdapter = HomeItemAdapter(filteredItems, object: OnItemClickListener {
            override fun onItemClick(item: ItemModel)
            {
                val intent = Intent(this@HomeActivity, ItemDetailActivity::class.java)
                intent.putExtra("ITEM_IMAGE_URI", item.image.toString())
                intent.putExtra("ITEM_NAME", item.title)
                intent.putExtra("ITEM_RATING", item.rating)
                intent.putExtra("ITEM_DESCRIPTION", item.description)
                startActivity(intent)
            }

            override fun onItemLongClick(item: ItemModel)
            {
                showContextMenu(item)
            }

        })
        homeVerticalRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        homeVerticalRecyclerView.adapter = homeItemAdapter
    }

    private fun initComponents()
    {
        homeHorizontalRecyclerView = findViewById(R.id.home_categories)
        homeVerticalRecyclerView = findViewById(R.id.homeItems)
        fabAddItem = findViewById(R.id.fabAddItem)
    }

    private fun filterItemsByCategory(category: String)
    {
        filteredItems = items.filter { it.category == category }
        homeItemAdapter = HomeItemAdapter(filteredItems, object: OnItemClickListener {
            override fun onItemClick(item: ItemModel)
            {
                val intent = Intent(this@HomeActivity, ItemDetailActivity::class.java)
                intent.putExtra("ITEM_IMAGE_URI", item.image.toString())
                intent.putExtra("ITEM_NAME", item.title)
                intent.putExtra("ITEM_RATING", item.rating)
                intent.putExtra("ITEM_DESCRIPTION", item.description)
                startActivity(intent)
            }

            override fun onItemLongClick(item: ItemModel) {
                showContextMenu(item)
            }
        })
        homeVerticalRecyclerView.adapter = homeItemAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        {
            when (requestCode)
            {
                ADD_ITEM_REQUEST_CODE ->
                {
                    val name = data?.getStringExtra("ITEM_NAME") ?: return
                    val price = data.getIntExtra("ITEM_PRICE", 0)
                    val description = data.getStringExtra("ITEM_DESCRIPTION") ?: return
                    val category = data.getStringExtra("ITEM_CATEGORY") ?: return
                    val imageUriString = data.getStringExtra("ITEM_IMAGE_URI")
                    val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null

                    val newItem = ItemModel(
                        imageUri,
                        name,
                        price,
                        description,
                        category,
                        "5.0"
                    )

                    items.add(newItem)
                    filterItemsByCategory(category)
                }
                EDIT_ITEM_REQUEST_CODE ->
                {
                    val originalName = data?.getStringExtra("ORIGINAL_ITEM_NAME") ?: return
                    val name = data.getStringExtra("ITEM_NAME") ?: return
                    val price = data.getIntExtra("ITEM_PRICE", 0)
                    val description = data.getStringExtra("ITEM_DESCRIPTION") ?: return
                    val category = data.getStringExtra("ITEM_CATEGORY") ?: return
                    val imageUriString = data.getStringExtra("ITEM_IMAGE_URI")
                    val imageUri = if (imageUriString != null) Uri.parse(imageUriString) else null

                    updateItemInList(originalName, name, price, description, category, imageUri)
                }
            }
        }
    }

    private fun showContextMenu(item: ItemModel)
    {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setItems(options) { dialog, which ->
                when (which)
                {
                    0 -> {
                        val intent = Intent(this, EditItemActivity::class.java)
                        intent.putExtra("ITEM_IMAGE_URI", item.image.toString())
                        intent.putExtra("ITEM_NAME", item.title)
                        intent.putExtra("ITEM_PRICE", item.price)
                        intent.putExtra("ITEM_DESCRIPTION", item.description)
                        intent.putExtra("ITEM_CATEGORY", item.category)
                        startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE)
                    }
                    1 -> {
                        // Delete option selected
                        showDeleteConfirmationDialog(item)
                    }
                }
            }
            .show()
    }

    private fun showDeleteConfirmationDialog(item: ItemModel) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar este ítem?")
            .setPositiveButton("Sí") { dialog, which ->
                items.remove(item)
                filterItemsByCategory(if (selectedCategoryIndex != -1) categories[selectedCategoryIndex].title else "")
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun updateItemInList(originalName: String, name: String, price: Int, description: String, category: String, imageUri: Uri?) {
        val itemIndex = items.indexOfFirst { it.title == originalName }
        if (itemIndex != -1) {
            val item = items[itemIndex]
            item.title = name
            item.price = price
            item.description = description
            item.category = category
            item.image = imageUri
            filterItemsByCategory(category)
        }
    }
}