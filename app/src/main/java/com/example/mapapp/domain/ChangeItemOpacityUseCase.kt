package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

class ChangeItemOpacityUseCase(
    private val repository: Repository
) {
    operator fun invoke(item: Item, value: Float) {
        repository.changeItemOpacity(item, value)
    }
}