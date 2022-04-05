package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

interface Repository {

    fun getItemsList(): List<Item>

    fun changeItemOpacity(item: Item, value: Int)

    fun setItemOpacity(item: Item, value: Boolean)

    fun addItem(item: Item)

}