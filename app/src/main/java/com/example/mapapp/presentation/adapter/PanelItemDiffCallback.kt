package com.example.mapapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mapapp.domain.entity.Item

class PanelItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}