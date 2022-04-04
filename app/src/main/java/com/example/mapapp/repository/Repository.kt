package com.example.mapapp.repository

import com.example.mapapp.R
import com.example.mapapp.entity.Item

object Repository {

    private var count_id = 0
    private val list = mutableListOf<Item>()

    fun addItem(item: Item) {
        list.add(item)
    }

    fun getList(): List<Item> {
        return list
    }


    fun getTemplateList(): List<Item> {
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Слой делян",
                opacity = 60
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Слой делян",
                opacity = 60
            )
        )

        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
            )
        )
        list.add(
            Item(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        return list
    }

}