package com.example.mapapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapapp.entity.Item
import com.example.mapapp.repository.Repository


class MapAppViewModel : ViewModel() {

    private val repository = Repository

    private val _itemsList = MutableLiveData<List<Item>>()
    val itemsList: LiveData<List<Item>>
        get() = _itemsList

    init {
        _itemsList.value = repository.getTemplateList()
    }

//    fun getList(): LiveData<List<Item>> {
//        _itemsList.value = repository.getTemplateList()
//        return itemsList
//    }

    fun addItem(item: Item) {
        //
    }

    fun visibilityChange(item: Item, value: Boolean) {
        //TODO() описать изменение свитча
        Log.d("ViewModel_TAG", "$item, $value")
    }

    fun opacityChange(item: Item, value: Int){
        //todo() описать изменение ползунка прозрачности
        Log.d("ViewModel_TAG", "$item, $value")
    }


}