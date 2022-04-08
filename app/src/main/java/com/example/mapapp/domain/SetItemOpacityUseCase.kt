package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

class SetItemOpacityUseCase(
    private val repository: Repository
) {
    operator fun invoke(item: Item, value: Boolean) {
        repository.setItemOpacity(item, value)
    }
}