package com.example.mapapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.binding.tvPanelItem.text = list[position].text
        holder.binding.clInvisiblePart.visibility = if (item.isExpanded) {
            View.VISIBLE
        } else {
            View.GONE
        }

        holder.binding.ivArrowPopup.setOnClickListener {
            item.isExpanded = !(item.isExpanded)
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}