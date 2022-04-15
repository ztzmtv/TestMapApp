package com.example.mapapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding
import com.example.mapapp.domain.entity.Item

class PanelItemAdapter() : ListAdapter<Item, PanelItemViewHolder>(PanelItemDiffCallback()) {
    var onDetailsClickListener: ((item: Item) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelItemViewHolder {
        val view = PanelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PanelItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PanelItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvPanelItem.text = item.text
            swPanelItem.isChecked = item.isChecked
            panelSlider.value = item.opacity
            ivPanelItem.setImageResource(item.imageResId ?: DEFAULT_IMAGE_RES)
            clInvisiblePart.visibility = setVisibility(item)
            ivArrowPopup.setOnClickListener {
                onDetailsClickListener?.invoke(item)
                notifyItemChanged(position)
            }
        }
    }

    private fun setVisibility(item: Item) = if (item.isExpanded) {
        View.VISIBLE
    } else {
        View.GONE
    }

    companion object {
        private const val DEFAULT_IMAGE_RES = R.drawable.polygon
    }
}