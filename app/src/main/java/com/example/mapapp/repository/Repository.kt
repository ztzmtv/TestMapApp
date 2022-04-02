package com.example.mapapp.repository

import com.example.mapapp.R
import com.example.mapapp.entity.Entity

object Repository {

    private var count_id = 0

    fun getTemplateList(): List<Entity> {

        val list = mutableListOf<Entity>()

        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Слой делян",
                opacity = 60
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Слой делян",
                opacity = 60
            )
        )

        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Сигналы о лесоизменениях, тестовая выборка с ув-ным шагом",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Преграды для прохождения огня",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
            )
        )
        list.add(
            Entity(
                id = count_id++,
                resId = R.drawable.ic_launcher_background,
                text = "Маска облачности от 02.07.2021",
                isChecked = false
            )
        )
        return list
    }

}