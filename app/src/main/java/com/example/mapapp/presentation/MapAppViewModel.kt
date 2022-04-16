package com.example.mapapp.presentation

import android.util.Log
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

    val itemsList = getItemsListUseCase.invoke()

    fun visibilityChange(item: Item, value: Boolean) {
        setItemOpacityUseCase(item, value)
    }

    fun opacityChange(item: Item, value: Float) {
        changeItemOpacityUseCase(item, value)
    }

    fun addDefaultItem() {
        addItemUseCase(repository.getDefaultItem())
        log("addDefaultItem()")
    }

    companion object {
        private const val TAG = "MapAppViewModel_TAG"
        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}