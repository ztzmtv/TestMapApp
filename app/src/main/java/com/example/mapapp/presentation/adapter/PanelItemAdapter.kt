package com.example.mapapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding
import com.example.mapapp.domain.entity.Item

class PanelItemAdapter() : ListAdapter<Item, PanelItemViewHolder>(PanelItemDiffCallback()) {
    var onDetailsClickListener: ((item: Item) -> Unit)? = null
    var onSwitchChangeListener: ((item: Item) -> Unit)? = null


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
            val color = if (item.isExpanded) {
                ContextCompat.getColor(tvPanelItem.context, R.color.primaryLightColor)
            } else {
                ContextCompat.getColor(tvPanelItem.context, R.color.primaryTextColor)
            }
            tvPanelItem.text = item.text
            tvPanelItem.setTextColor(color)
            panelSlider.value = item.opacity
            ivPanelItem.setColorFilter(color)
            ivPanelItem.setImageResource(item.imageResId ?: DEFAULT_IMAGE_RES)
            clInvisiblePart.visibility = setVisibility(item.isExpanded)
            llGroupDivider.visibility = View.GONE
            swPanelItem.isChecked = item.isChecked
            ivEye.visibility = setVisibility(!item.isChecked)
            swPanelItem.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                onSwitchChangeListener?.invoke(item)
                val opacity = setHalfOpacity(item.isChecked)
                for (i in 0 until root.childCount) root.getChildAt(i).alpha = opacity
                ivEye.visibility = setVisibility(!item.isChecked)
            }
            tvPanelItem.setOnClickListener {
                onDetailsClickListener?.invoke(item)
                notifyItemChanged(position)
            }
            ivArrowPopup.setOnClickListener {
                onDetailsClickListener?.invoke(item)
                notifyItemChanged(position)
            }
        }
    }

    private fun setVisibility(isTrue: Boolean) = if (isTrue) {
        View.VISIBLE
    } else {
        View.GONE
    }

    private fun setHalfOpacity(isTrue: Boolean) = if (isTrue) {
        OPACITY_FULL
    } else {
        OPACITY_HALF
    }

    companion object {
        private const val DEFAULT_IMAGE_RES = R.drawable.polygon
        private const val OPACITY_FULL = 1.0F
        private const val OPACITY_HALF = 0.65F
    }
}