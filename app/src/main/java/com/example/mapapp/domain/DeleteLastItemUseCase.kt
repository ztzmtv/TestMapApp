package com.example.mapapp.domain

class DeleteLastItemUseCase(
    private val repository: Repository
) {
    operator fun invoke() {
        repository.deleteLastItem()
    }
}