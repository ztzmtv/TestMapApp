package com.example.mapapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.R
import com.example.mapapp.databinding.PanelItemBinding

import com.example.mapapp.domain.entity.Item

class PanelItemAdapter(
    private val list: List<Item>
) : RecyclerView.Adapter<PanelItemViewHolder>() {
    var onDetailsClickListener: ((position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelItemViewHolder {
        val view = PanelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PanelItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PanelItemViewHolder, position: Int) {
        val item = list[position]
        with(holder.binding) {
            tvPanelItem.text = item.text
            swPanelItem.isChecked = item.isChecked
            panelSlider.value = item.opacity
            ivPanelItem.setImageResource(item.imageResId ?: DEFAULT_IMAGE_RES)
            clInvisiblePart.visibility = if (item.isExpanded) {
                View.VISIBLE
            } else {
                View.GONE
            }

            ivArrowPopup.setOnClickListener {
                item.isExpanded = !(item.isExpanded)
                notifyItemChanged(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object {
        private const val DEFAULT_IMAGE_RES = R.drawable.polygon
    }
}