package com.example.mapapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding
import com.example.mapapp.domain.entity.Item
import com.example.mapapp.helper.PanelSliderTouchListener
import java.text.SimpleDateFormat
import java.util.*

class PanelItemAdapter : ListAdapter<Item, PanelItemViewHolder>(PanelItemDiffCallback()) {
    var onDetailsClickListener: ((position: Int) -> Unit)? = null
    var onSwitchChangeListener: ((item: Item) -> Unit)? = null
    var onSliderTouchListener: ((item: Item) -> Unit)? = null

    override fun getItemId(position: Int): Long {
        log("position $position id ${currentList[position].id.toLong()}")
        return  currentList[position].id.toLong()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelItemViewHolder {
        val view = PanelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PanelItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PanelItemViewHolder, position: Int) {
        val item = currentList[holder.adapterPosition]
        with(holder.binding) {
            val color = if (item.isExpanded) {
                ContextCompat.getColor(tvPanelItem.context, R.color.primaryLightColor)
            } else {
                ContextCompat.getColor(tvPanelItem.context, R.color.primaryTextColor)
            }
            val textOpacity = tvOpacity.context.getString(R.string.string_opacity)
            val textSynchronized =
                tvSynchronizedAt.context.getString(R.string.string_synchronized_at)
            val textCountOfElements =
                tvCountOfElements.context.getString(R.string.string_count_of_elements)
            val opacity = setHalfOpacity(item.isChecked)
            tvPanelItem.text = item.text
            tvPanelItem.setTextColor(color)
            panelSlider.value = item.opacity
            ivPanelItem.setColorFilter(color)
            ivPanelItem.setImageResource(item.imageResId ?: DEFAULT_IMAGE_RES)
            clInvisiblePart.visibility = setVisibility(item.isExpanded)
            llGroupDivider.visibility = View.GONE
            swPanelItem.isChecked = item.isChecked
            ivEye.visibility = setVisibility(!item.isChecked)
            tvOpacity.text = String.format(
                textOpacity,
                ((item.opacity) * PERCENTS_MULTIPLIER).toInt().toString()
            )
            tvSynchronizedAt.text = String.format(textSynchronized, getCurrentDate())
            tvCountOfElements.text = String.format(textCountOfElements, "34")
            for (i in 0 until root.childCount) root.getChildAt(i).alpha = opacity
            panelSlider.addOnSliderTouchListener(
                PanelSliderTouchListener {
                    item.opacity = it.value
                    onSliderTouchListener?.invoke(item)
                    for (i in 0 until root.childCount) root.getChildAt(i).alpha = it.value
                    tvOpacity.text = String.format(
                        textOpacity,
                        ((it.value) * PERCENTS_MULTIPLIER).toInt().toString()
                    )
                }
            )

            swPanelItem.setOnCheckedChangeListener { _, isSwitchChecked ->
                item.isChecked = isSwitchChecked
                onSwitchChangeListener?.invoke(item)
                swPanelItem.isChecked = item.isChecked
                ivEye.visibility = setVisibility(!item.isChecked)
                val opacity = setHalfOpacity(item.isChecked)
                for (i in 0 until root.childCount) root.getChildAt(i).alpha = opacity
            }
            tvPanelItem.setOnClickListener {
                onDetailsClickListener?.invoke(holder.adapterPosition)
                notifyItemChanged(position)
            }
            ivArrowPopup.setOnClickListener {
                onDetailsClickListener?.invoke(holder.adapterPosition)
                notifyItemChanged(position)
            }

        }
    }

    private fun getCurrentDate(): String? {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
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
        private const val PERCENTS_MULTIPLIER = 100
        private const val DEFAULT_IMAGE_RES = R.drawable.polygon
        private const val OPACITY_FULL = 1.0F
        private const val OPACITY_HALF = 0.65F
        private const val TAG = "PanelItemAdapter_TAG"
        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}