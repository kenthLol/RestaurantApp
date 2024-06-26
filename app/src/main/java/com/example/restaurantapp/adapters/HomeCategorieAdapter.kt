package com.example.restaurantapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.CategorieModel

class HomeCategorieAdapter(
    private val categorieList: List<CategorieModel>,
    public var selectedCategoryIndex: Int,
    private val clickListener: (CategorieModel, Int) -> Unit
) : RecyclerView.Adapter<HomeCategorieAdapter.HomeCategorieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategorieViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_horizontal_item, parent, false)
        return HomeCategorieViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCategorieViewHolder, position: Int)
    {
        val categorie = categorieList[position]
        holder.bind(categorie, position, selectedCategoryIndex, clickListener)
    }

    override fun getItemCount(): Int
    {
        return categorieList.size
    }

    inner class HomeCategorieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val categorieImage: ImageView = itemView.findViewById(R.id.imageView)
        private val categorieTitle: TextView = itemView.findViewById(R.id.title)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(categorie: CategorieModel, position: Int, selectedIndex: Int, clickListener: (CategorieModel, Int) -> Unit) {
            categorieImage.setImageResource(categorie.image)
            categorieTitle.text = categorie.title

            if (position == selectedIndex) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green_color))
                categorieTitle.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                categorieTitle.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.black))
            }

            itemView.setOnClickListener {
                clickListener(categorie, position)
            }
        }
    }
}