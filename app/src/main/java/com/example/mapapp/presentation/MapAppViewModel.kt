package com.example.mapapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapapp.domain.entity.Item
import com.example.mapapp.data.repository.RepositoryImpl
import com.example.mapapp.domain.AddItemUseCase
import com.example.mapapp.domain.ChangeItemOpacityUseCase
import com.example.mapapp.domain.GetItemsListUseCase
import com.example.mapapp.domain.SetItemOpacityUseCase


class MapAppViewModel : ViewModel() {
    private val repository = RepositoryImpl
    private val addItemUseCase = AddItemUseCase(repository)
    private val changeItemOpacityUseCase = ChangeItemOpacityUseCase(repository)
    private val setItemOpacityUseCase = SetItemOpacityUseCase(repository)
    private val getItemsListUseCase = GetItemsListUseCase(repository)

    private val _itemsList = MutableLiveData<List<Item>>()
    val itemsList: LiveData<List<Item>>
        get() = _itemsList

    init {
        _itemsList.value = getItemsListUseCase()
    }

//    fun getList(): LiveData<List<Item>> {
//        _itemsList.value = repository.getTemplateList()
//        return itemsList
//    }

    fun addItem(item: Item) {
        //
    }

    fun visibilityChange(item: Item, value: Boolean) {
        setItemOpacityUseCase(item, value)
        Log.d("ViewModel_TAG", "$item, $value")
    }

    fun opacityChange(item: Item, value: Int) {
        changeItemOpacityUseCase(item, value)
        Log.d("ViewModel_TAG", "$item, $value")
    }


}