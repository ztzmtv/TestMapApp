package com.example.mapapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapapp.entity.Item
import com.example.mapapp.repository.Repository


class MapAppViewModel : ViewModel() {

    private val _itemsList = MutableLiveData<List<Item>>()
    val itemsList: LiveData<List<Item>>
        get() = _itemsList

    init {
        _itemsList.value = Repository.getTemplateList()
    }

    fun getList(): LiveData<List<Item>> {
        _itemsList.value = Repository.getTemplateList()
        return itemsList
    }

    fun addItem(item: Item) {
        //
    }


}