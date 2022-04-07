package com.example.mapapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mapapp.data.repository.RepositoryImpl
import com.example.mapapp.domain.AddItemUseCase
import com.example.mapapp.domain.ChangeItemOpacityUseCase
import com.example.mapapp.domain.GetItemsListUseCase
import com.example.mapapp.domain.SetItemOpacityUseCase
import com.example.mapapp.domain.entity.Item

class MapAppViewModel : ViewModel() {
    private val repository = RepositoryImpl
    private val addItemUseCase = AddItemUseCase(repository)
    private val changeItemOpacityUseCase = ChangeItemOpacityUseCase(repository)
    private val setItemOpacityUseCase = SetItemOpacityUseCase(repository)
    private val getItemsListUseCase = GetItemsListUseCase(repository)
    private val _itemsList = MutableLiveData<List<Item>>()

    val itemsList: LiveData<List<Item>>
        get() = _itemsList

    //livedata with list sorted by group
    val sortedItemsList: LiveData<List<Item>> =
        Transformations.map(_itemsList) { list ->
            list.sortedByDescending { item -> item.group }
        }

    init {
        _itemsList.value = getItemsListUseCase()
    }

    fun visibilityChange(item: Item, value: Boolean) {
        setItemOpacityUseCase(item, value)
    }

    fun opacityChange(item: Item, value: Float) {
        changeItemOpacityUseCase(item, value)
    }

    fun addDefaultItem() {
        addItemUseCase(repository.DEFAULT_ITEM)
        _itemsList.value = getItemsListUseCase()
    }
}