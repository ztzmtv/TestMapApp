package com.example.mapapp.data.repository

import android.util.Log
import com.example.mapapp.R
import com.example.mapapp.domain.Repository
import com.example.mapapp.domain.entity.Item

object RepositoryImpl : Repository {

    private var count_id = 0
    private val list = mutableListOf<Item>()

    override fun getItemsList(): List<Item> {
        return getTemplateList()
    }

    override fun changeItemOpacity(item: Item, value: Float) {
        item.opacity = value
        editItem(item)
    }

    override fun setItemOpacity(item: Item, value: Boolean) {
        item.isChecked = value
        editItem(item)
    }

    override fun addItem(item: Item) {
        list.add(item)
        Log.d("RepositoryImpl_TAG", "OK")
    }

    private fun editItem(item: Item) {
        val index = getItemIndex(item)
        index?.let { list.set(it, item) }
        Log.d("RepositoryImpl_TAG", "$list")
    }

    private fun getTemplateList(): List<Item> {
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.geometry_collection,
                text = "Слой делян",
                opacity = 0.60f,
                group = "Общая группа",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.waypoint,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.polygon,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                group = "Общая группа",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.geometry_collection,
                text = "Слой делян",
                opacity = 0.60f
            )
        )

        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.waypoint,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.line,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.polygon_hatched,
                text = "Маска облачности от 02.07.2021",
            )
        )
        list.add(
            Item(
                id = count_id++,
                imageResId = R.drawable.polygon,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        return list
    }

    private fun getItemIndex(item: Item): Int? {
        for ((index, listItem) in list.withIndex()) {
            if (item.id == listItem.id) {
                return index
            }
        }
        return null
    }

// value adding by default
    val DEFAULT_ITEM = Item(
        id = count_id++,
        imageResId = R.drawable.polygon,
        text = "Маска облачности от 02.07.2021",
    )
}