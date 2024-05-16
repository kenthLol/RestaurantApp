package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.adapters.HomeCategorieAdapter
import com.example.restaurantapp.adapters.HomeItemAdapter
import com.example.restaurantapp.models.CategorieModel
import com.example.restaurantapp.models.ItemModel

class HomeActivity : AppCompatActivity()
{

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

    private val items = listOf(
        ItemModel(
            R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"
        ),
        ItemModel(R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"
        ),
        ItemModel(R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"
        ),
        ItemModel(R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"
        ),
        ItemModel(R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"),
        ItemModel(R.drawable.burguer_cheese,
            "Hamburguesa de queso",
            10,
            "Hamburguesa de queso con carne de res",
            "Hamburguesa",
            "4.5"),
        ItemModel(R.drawable.ic_pizza, "Pizza Margherita", 8, "Pizza clásica italiana", "Pizza", "4.7"),
        ItemModel(R.drawable.ic_salad, "Salmon Salad", 12, "Salad with fresh salmon", "Ensalada", "5"),
        ItemModel(R.drawable.ic_postre, "Chocolate Cake", 6, "Delicious chocolate cake", "Postre", "4.9"),
        ItemModel(R.drawable.ic_drink, "Coca Cola", 2, "Refreshing cola drink", "Bebida", "4.0")
    )


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        initComponents()
        initUI()
    }

    private fun initUI()
    {
        homeCategoriesAdapter = HomeCategorieAdapter(categories) { category ->
            filterItemsByCategory(category.title)
        }
        homeHorizontalRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        homeHorizontalRecyclerView.adapter = homeCategoriesAdapter

        filteredItems = items // Initially show all items
        homeItemAdapter = HomeItemAdapter(filteredItems)
        homeVerticalRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        homeVerticalRecyclerView.adapter = homeItemAdapter
    }

    private fun initComponents()
    {
        homeHorizontalRecyclerView = findViewById(R.id.home_categories)
        homeVerticalRecyclerView = findViewById(R.id.homeItems)
    }

    private fun filterItemsByCategory(category: String) {
        filteredItems = items.filter { it.category == category }
        homeItemAdapter = HomeItemAdapter(filteredItems)
        homeVerticalRecyclerView.adapter = homeItemAdapter
    }
}