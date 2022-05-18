package com.example.mapapp.helper

import com.example.mapapp.domain.entity.Item
import com.example.mapapp.presentation.adapter.BindingPanelItem

class Mapper {

    fun mapItemToBindingPanelItem(list: List<Item>): List<BindingPanelItem> {
        val resultList = mutableListOf<BindingPanelItem>()
        for (item in list) {
            resultList.add(BindingPanelItem(item))
        }
        return resultList
    }
}