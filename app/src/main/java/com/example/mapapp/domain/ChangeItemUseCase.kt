package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

class ChangeItemUseCase(
    private val repository: Repository
) {
    operator fun invoke(item: Item) {
        repository.changeItem(item)
    }
}