package com.example.mapapp.domain

class FilterItemsUseCase(
    private val repository: Repository
) {
    operator fun invoke(string: String) {
        repository.filterItems(string)
    }
}