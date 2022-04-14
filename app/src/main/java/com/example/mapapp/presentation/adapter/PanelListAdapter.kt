package com.example.mapapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding
import com.example.mapapp.domain.entity.Item

class PanelListAdapter :
    ListAdapter<Item, PanelItemViewHolder>(PanelItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelItemViewHolder {
//        val layout = when (viewType) {
//            SHOP_ITEM_ENABLED -> R.layout.item_shop_enabled
//            SHOP_ITEM_DISABLED -> R.layout.item_shop_disabled
//            else -> throw RuntimeException("Unknown view type: $viewType")
//        }
        val layout = R.layout.panel_item
        val binding = PanelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PanelItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PanelItemViewHolder, position: Int) {
        val panelItem = getItem(position)
        val binding = holder.binding
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}