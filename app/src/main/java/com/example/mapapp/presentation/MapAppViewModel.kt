package com.example.mapapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mapapp.data.repository.RepositoryImpl
import com.example.mapapp.domain.*
import com.example.mapapp.domain.entity.Item

class MapAppViewModel : ViewModel() {
    private val repository = RepositoryImpl
    private val addItemUseCase = AddItemUseCase(repository)
    private val changeItemUseCase = ChangeItemUseCase(repository)
    private val setItemOpacityUseCase = SetItemOpacityUseCase(repository)
    private val getItemsListLiveDataUseCase = GetItemsListLiveDataUseCase(repository)
    private val filterItemsUseCase = FilterItemsUseCase(repository)
    private val deleteLastItemUseCase = DeleteLastItemUseCase(repository)
    private val getItemsListUseCase = GetItemsListUseCase(repository)

    var itemsListLiveData = getItemsListLiveDataUseCase.invoke()
    val itemsList = getItemsListUseCase.invoke()

    fun changeItem(item: Item) {
        changeItemUseCase(item)
    }

    fun findItems(string: String) {
        filterItemsUseCase(string)
    }

    fun deleteLastItem() {
        deleteLastItemUseCase()
    }

    fun deleteItem(item: Item) {
        repository.deleteItem(item)
    }

    fun addDefaultItem() {
        addItemUseCase(repository.getDefaultItem())
        log("addDefaultItem()")
    }

    //TODO("create usecass")
    fun moveItem(from: Int, to: Int) {
        repository.moveItem(from, to)
    }

    fun getSortedList(isSorted: Boolean) {
        itemsListLiveData = if (isSorted) {
            repository.getSortedItemsListLiveData()
        } else {
            repository.getItemsListLiveData()
        }
    }


    companion object {
        private const val TAG = "MapAppViewModel_TAG"
        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}