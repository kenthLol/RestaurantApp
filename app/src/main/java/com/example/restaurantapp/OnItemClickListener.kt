package com.example.restaurantapp

import com.example.restaurantapp.models.ItemModel

interface OnItemClickListener
{
    fun onItemClick(item: ItemModel)
}