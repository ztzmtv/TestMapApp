package com.example.mapapp.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding
import com.example.mapapp.domain.entity.Item
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import java.text.SimpleDateFormat
import java.util.*

open class BindingPanelItem(
    val item: Item
) : AbstractBindingItem<PanelItemBinding>() {

    override val type: Int
        get() = R.id.ll_panel_item

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PanelItemBinding {
        return PanelItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: PanelItemBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)

        with(binding) {
            val color = if (item.isExpanded) {
                androidx.core.content.ContextCompat.getColor(
                    tvPanelItem.context,
                    R.color.primaryLightColor
                )
            } else {
                androidx.core.content.ContextCompat.getColor(
                    tvPanelItem.context,
                    R.color.primaryTextColor
                )
            }
            val textOpacity =
                tvOpacity.context.getString(R.string.string_opacity)
            val textSynchronized =
                tvSynchronizedAt.context.getString(R.string.string_synchronized_at)
            val textCountOfElements =
                tvCountOfElements.context.getString(R.string.string_count_of_elements)
            val opacity = setHalfOpacity(item.isChecked)
            tvPanelItem.text = item.text
            tvPanelItem.setTextColor(color)
            panelSlider.value = item.opacity
            ivPanelItem.setColorFilter(color)
            ivPanelItem.setImageResource(
                item.imageResId
                    ?: DEFAULT_IMAGE_RES
            )
            clInvisiblePart.visibility = setVisibility(item.isExpanded)
            llGroupDivider.visibility = View.GONE
            swPanelItem.isChecked = item.isChecked
            ivEye.visibility = setVisibility(!item.isChecked)
            tvOpacity.text = String.format(
                textOpacity,
                ((item.opacity) * PERCENTS_MULTIPLIER).toInt()
                    .toString()
            )
            tvSynchronizedAt.text = String.format(textSynchronized, getCurrentDate())
            tvCountOfElements.text = String.format(textCountOfElements, "34")
            for (i in 0 until root.childCount) root.getChildAt(i).alpha = opacity
//
//            panelSlider.addOnSliderTouchListener(
//                PanelSliderTouchListener {
//                    item.opacity = it.value
//                    onSliderTouchListener?.invoke(item)
//                    for (i in 0 until root.childCount) root.getChildAt(i).alpha = it.value
//                    tvOpacity.text = format(
//                        textOpacity,
//                        ((it.value) * PERCENTS_MULTIPLIER).toInt()
//                            .toString()
//                    )
//                }
//            )
//
//            swPanelItem.setOnCheckedChangeListener { _, isSwitchChecked ->
//                item.isChecked = isSwitchChecked
//                onSwitchChangeListener?.invoke(item)
//                ivEye.visibility = setVisibility(!item.isChecked)
//                val opacityAlpha = setHalfOpacity(item.isChecked)
//                for (i in 0 until root.childCount) root.getChildAt(i).alpha = opacityAlpha
//            }
//            tvPanelItem.setOnClickListener {
//                onDetailsClickListener?.invoke(holder.adapterPosition)
//                notifyItemChanged(position)
//            }
//            ivArrowPopup.setOnClickListener {
//                onDetailsClickListener?.invoke(holder.adapterPosition)
//                val invisibleView = holder.binding.clInvisiblePart
//                invisibleView.visibility = setVisibility(!getVisibility(invisibleView))
//                //notifyItemChanged(position)
//            }

        }
    }

    private fun getCurrentDate(): String? {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getVisibility(view: View): Boolean {
        return view.visibility == View.VISIBLE
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