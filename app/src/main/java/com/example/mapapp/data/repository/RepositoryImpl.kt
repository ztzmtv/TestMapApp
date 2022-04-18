package com.example.mapapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapapp.R
import com.example.mapapp.domain.Repository
import com.example.mapapp.domain.entity.Item

object RepositoryImpl : Repository {
    private var autoIncrementId = 0
    private val panelItemsListLiveData = MutableLiveData<List<Item>>()
    private var panelItemsList = mutableListOf<Item>()

    init {
        panelItemsList = getTemplateList()
        updateList()
    }



    override fun getItemsList(): LiveData<List<Item>> {
        return panelItemsListLiveData
    }

    override fun changeItem(item: Item) {
        val index = getItemIndex(item)
        index?.let {
            panelItemsList.set(it, item)
        }
        updateList()
        Log.d("RepositoryImpl_TAG", "$panelItemsList")
    }

    override fun setItemOpacity(item: Item, value: Boolean) {
        item.isChecked = value
        editItem(item)
        updateList()
    }

    override fun addItem(item: Item) {
        if (item.id == Item.DEFAULT_NO_ID) {
            item.id = autoIncrementId++
        }
        panelItemsList.add(item)
        updateList()
        Log.d("RepositoryImpl_TAG", "$this")
    }

    override fun filterItems(string: String) {
        if (string.isNotEmpty()) {
            panelItemsListLiveData.value = panelItemsList.filter {
                it.text?.contains(string) ?: false
            }
        } else {
            updateList()
        }
    }

    override fun deleteLastItem() {
        if (panelItemsList.isNotEmpty()) {
            panelItemsList.removeLast()
        }
        updateList()
    }

    private fun editItem(item: Item) {
        // TODO("Редактирование")
    }

    private fun updateList() {
//        panelItemsList.sortedBy { it.id }
        panelItemsListLiveData.value = panelItemsList.toList()
        Log.d("RepositoryImpl_TAG", "updateList")
    }

    private fun getTemplateList(): MutableList<Item> {
        val templateList = mutableListOf<Item>()
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.geometry_collection,
                text = "Слой делян",
                opacity = 0.60f,
                group = "Общая группа",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.waypoint,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.polygon,
                text = "Преграды для прохождения огня",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                group = "Общая группа",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.geometry_collection,
                text = "Слой делян",
                opacity = 0.60f
            )
        )

        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.waypoint,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.line,
                text = "Преграды для прохождения огня",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.polygon_hatched,
                text = "Маска облачности от 02.07.2021",
            )
        )
        templateList.add(
            Item(
                id = autoIncrementId++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        return templateList
    }

    private fun getItemIndex(item: Item): Int? {
        for ((index, listItem) in panelItemsList.withIndex()) {
            if (item.id == listItem.id) {
                return index
            }
        }
        return null
    }

    fun getDefaultItem(): Item {
        return Item(
            imageResId = R.drawable.polygon,
            text = "Маска облачности от 02.07.2021",
            isChecked = false
        )
    }

}