package com.example.mapapp.domain

import com.example.mapapp.domain.entity.Item

class GetItemsListUseCase(
    private val repository: Repository
) {
    operator fun invoke(): List<Item> {
        return repository.getItemsList()
    }
}