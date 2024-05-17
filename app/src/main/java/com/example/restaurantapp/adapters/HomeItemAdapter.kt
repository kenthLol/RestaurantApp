package com.example.restaurantapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.R
import com.example.restaurantapp.models.ItemModel

class HomeItemAdapter(private var itemList: List<ItemModel>) : RecyclerView.Adapter<HomeItemAdapter.HomeItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_vertical_item, parent, false)
        return HomeItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int)
    {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int
    {
        return itemList.size
    }

    inner class HomeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val itemImage: ImageView = itemView.findViewById(R.id.imageViewDish)
        private val itemTitle: TextView = itemView.findViewById(R.id.tvTitleDish)
        private val itemPrice: TextView = itemView.findViewById(R.id.tvPriceDish)
        private val itemDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val itemRating: TextView = itemView.findViewById(R.id.tvRating)

        fun bind(item: ItemModel)
        {
            itemImage.setImageResource(item.image)
            itemTitle.text = item.title
            itemPrice.text = "$${item.price}"
            itemDescription.text = item.description
            itemRating.text = item.rating
        }
    }

}