package com.example.mapapp.domain

import androidx.lifecycle.LiveData
import com.example.mapapp.domain.entity.Item

interface Repository {

    fun getItemsList(): LiveData<List<Item>>

    fun changeItemOpacity(item: Item, value: Float)

    fun setItemOpacity(item: Item, value: Boolean)

    fun addItem(item: Item)

}