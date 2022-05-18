package com.example.mapapp.domain

import androidx.lifecycle.LiveData
import com.example.mapapp.domain.entity.Item

interface Repository {

    fun getCurrentList(): List<Item>

    fun getItemsListLiveData(): LiveData<List<Item>>

    fun getSortedItemsListLiveData(): LiveData<List<Item>>

    fun changeItem(item: Item)

    fun setItemOpacity(item: Item, value: Boolean)

    fun addItem(item: Item)

    fun filterItems(string: String)

    fun deleteLastItem()

    fun moveItem(from: Int, to: Int)

    fun deleteItem(item: Item)

}