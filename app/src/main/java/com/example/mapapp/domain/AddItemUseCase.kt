package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

class AddItemUseCase(
    private val repository: Repository
) {
    operator fun invoke(item: Item) {
        repository.addItem(item)
    }
}