package com.example.mapapp.domain

import androidx.lifecycle.LiveData
import com.example.mapapp.domain.entity.Item

class GetItemsListLiveDataUseCase(
    private val repository: Repository
) {
    operator fun invoke(): LiveData<List<Item>> {
        return repository.getItemsListLiveData()
    }
}